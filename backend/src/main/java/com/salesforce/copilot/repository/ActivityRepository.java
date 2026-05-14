package com.salesforce.copilot.repository;

import com.salesforce.copilot.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Finds all activities for a specific deal
    // ordered by date — most recent first
    List<Activity> findByOpportunityIdOrderByActivityDateDesc(Long opportunityId);
}