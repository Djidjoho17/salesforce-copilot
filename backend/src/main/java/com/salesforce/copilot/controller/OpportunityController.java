package com.salesforce.copilot.controller;

import com.salesforce.copilot.model.Opportunity;
import com.salesforce.copilot.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@CrossOrigin(origins = "http://localhost:5173")
public class OpportunityController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    // GET http://localhost:8080/api/opportunities
    // Returns ALL deals as JSON
    @GetMapping
    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    // GET http://localhost:8080/api/opportunities/1
    // Returns ONE deal by its ID
    @GetMapping("/{id}")
    public Opportunity getById(@PathVariable Long id) {
        return opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deal not found: " + id));
    }

    // GET http://localhost:8080/api/opportunities/stage/Negotiation
    // Returns all deals in a specific stage
    @GetMapping("/stage/{stage}")
    public List<Opportunity> getByStage(@PathVariable String stage) {
        return opportunityRepository.findByStage(stage);
    }

    // POST http://localhost:8080/api/opportunities
    // Creates a new deal
    @PostMapping
    public Opportunity createOpportunity(@RequestBody Opportunity opportunity) {
        return opportunityRepository.save(opportunity);
    }

    // PUT http://localhost:8080/api/opportunities/1
    // Updates an existing deal
    @PutMapping("/{id}")
    public Opportunity updateOpportunity(@PathVariable Long id,
                                         @RequestBody Opportunity updated) {
        return opportunityRepository.findById(id).map(opp -> {
            opp.setName(updated.getName());
            opp.setStage(updated.getStage());
            opp.setAmount(updated.getAmount());
            opp.setProbability(updated.getProbability());
            opp.setCloseDate(updated.getCloseDate());
            return opportunityRepository.save(opp);
        }).orElseThrow(() -> new RuntimeException("Deal not found: " + id));
    }

    // DELETE http://localhost:8080/api/opportunities/1
    // Deletes a deal
    @DeleteMapping("/{id}")
    public String deleteOpportunity(@PathVariable Long id) {
        opportunityRepository.deleteById(id);
        return "Deal deleted successfully";
    }
}