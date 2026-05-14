package com.salesforce.copilot.repository;

import com.salesforce.copilot.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Spring automatically writes the SQL for this method
    // just from reading the method name
    // It translates to: SELECT * FROM accounts WHERE industry = ?
    List<Account> findByIndustry(String industry);
}