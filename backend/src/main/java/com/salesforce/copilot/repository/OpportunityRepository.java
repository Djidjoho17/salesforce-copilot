package com.salesforce.copilot.repository;

import com.salesforce.copilot.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    // Finds all deals in a specific stage
    // e.g. findByStage("Negotiation") finds all deals in Negotiation
    List<Opportunity> findByStage(String stage);

    // Custom SQL query — finds all deals for a specific account
    List<Opportunity> findByAccountId(Long accountId);

    // Returns all deals sorted by amount — biggest deals first
    @Query("SELECT o FROM Opportunity o ORDER BY o.amount DESC")
    List<Opportunity> findAllOrderByAmountDesc();
}