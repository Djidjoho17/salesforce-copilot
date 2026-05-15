package com.salesforce.copilot.controller;

import com.salesforce.copilot.model.Activity;
import com.salesforce.copilot.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    // GET http://localhost:8080/api/activities
    // Returns all activities
    @GetMapping
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // GET http://localhost:8080/api/activities/opportunity/1
    // Returns all activities for a specific deal
    @GetMapping("/opportunity/{opportunityId}")
    public List<Activity> getByOpportunity(@PathVariable Long opportunityId) {
        return activityRepository
                .findByOpportunityIdOrderByActivityDateDesc(opportunityId);
    }

    // POST http://localhost:8080/api/activities
    // Creates a new activity
    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityRepository.save(activity);
    }
}