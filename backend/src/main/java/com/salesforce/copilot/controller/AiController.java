package com.salesforce.copilot.controller;

import com.salesforce.copilot.model.Opportunity;
import com.salesforce.copilot.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    @Value("${anthropic.api.key}")
    private String anthropicApiKey;

    @Autowired
    private OpportunityRepository oppRepo;

    @PostMapping("/analyze")
    public Map<String, String> analyze(@RequestBody Map<String, String> request)
            throws Exception {

        String question = request.get("question");

        // Get all deals from database to give Claude context
        List<Opportunity> deals = oppRepo.findAll();

        // Build a text summary of the pipeline
        String pipeline = deals.stream()
                .map(o -> String.format(
                        "Deal: %s | Stage: %s | Amount: $%.0f | Close: %s | Probability: %d%%",
                        o.getName(), o.getStage(), o.getAmount(),
                        o.getCloseDate(), o.getProbability()))
                .collect(Collectors.joining("\\n"));

        // Build the prompt for Claude
        String prompt = "You are an expert Salesforce sales analyst AI copilot.\\n\\n" +
                "Current sales pipeline:\\n" + pipeline + "\\n\\n" +
                "Sales rep question: " + question + "\\n\\n" +
                "Give a concise, actionable analysis in under 150 words. " +
                "Use bullet points. Name specific deals.";

        // Build JSON request body for Claude API
        String body = "{" +
                "\"model\":\"claude-haiku-4-5-20251001\"," +
                "\"max_tokens\":1024," +
                "\"messages\":[{" +
                "\"role\":\"user\"," +
                "\"content\":\"" + prompt.replace("\"", "\\\"").replace("\n", "\\n") + "\"" +
                "}]" +
                "}";

        // Call Claude API using Java's built-in HTTP client
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.anthropic.com/v1/messages"))
                .header("content-type", "application/json")
                .header("x-api-key", anthropicApiKey)
                .header("anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());

        // Parse the response to extract Claude's text
        String responseBody = response.body();
        int start = responseBody.indexOf("\"text\":\"") + 8;
        int end = responseBody.indexOf("\",\"type\"", start);
        if (end == -1) end = responseBody.indexOf("\"}", start);

        String aiText = responseBody.substring(start, end)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");

        return Map.of("response", aiText);
    }
}