package com.salesforce.copilot.config;

import com.salesforce.copilot.model.*;
import com.salesforce.copilot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private AccountRepository accountRepo;
    @Autowired private OpportunityRepository oppRepo;
    @Autowired private ActivityRepository activityRepo;

    @Override
    public void run(String... args) {

        // Only seed if database is empty
        // This prevents duplicate data every time you restart the app
        if (accountRepo.count() > 0) return;

        System.out.println("🌱 Seeding database with sample data...");

        // ── Create Accounts (Companies) ──
        Account acme = new Account(null, "Acme Corporation",
                "Technology", "acme.com", "555-0101",
                5_000_000.0, null, null);

        Account globex = new Account(null, "Globex Industries",
                "Manufacturing", "globex.com", "555-0202",
                12_000_000.0, null, null);

        Account initech = new Account(null, "Initech",
                "Finance", "initech.com", "555-0303",
                3_500_000.0, null, null);

        Account umbrella = new Account(null, "Umbrella Corp",
                "Healthcare", "umbrella.com", "555-0404",
                8_000_000.0, null, null);

        acme     = accountRepo.save(acme);
        globex   = accountRepo.save(globex);
        initech  = accountRepo.save(initech);
        umbrella = accountRepo.save(umbrella);

        // ── Create Opportunities (Deals) ──
        Opportunity deal1 = new Opportunity(null,
                "Acme - Enterprise License",
                "Negotiation", 85_000.0,
                LocalDate.now().plusDays(14),
                75, acme, null, null);

        Opportunity deal2 = new Opportunity(null,
                "Globex - Platform Upgrade",
                "Proposal", 220_000.0,
                LocalDate.now().plusDays(30),
                50, globex, null, null);

        Opportunity deal3 = new Opportunity(null,
                "Initech - Starter Package",
                "Qualification", 15_000.0,
                LocalDate.now().plusDays(60),
                30, initech, null, null);

        Opportunity deal4 = new Opportunity(null,
                "Umbrella - Premium Suite",
                "Prospecting", 45_000.0,
                LocalDate.now().plusDays(90),
                20, umbrella, null, null);

        Opportunity deal5 = new Opportunity(null,
                "Acme - Support Contract",
                "Closed Won", 24_000.0,
                LocalDate.now().minusDays(5),
                100, acme, null, null);

        deal1 = oppRepo.save(deal1);
        deal2 = oppRepo.save(deal2);
        deal3 = oppRepo.save(deal3);
        deal4 = oppRepo.save(deal4);
        deal5 = oppRepo.save(deal5);

        // ── Create Activities (Calls, Emails, Meetings) ──
        activityRepo.save(new Activity(null,
                "Call", "Pricing discussion",
                "Discussed enterprise tier pricing. Client happy with ROI.",
                LocalDateTime.now().minusDays(2),
                deal1, null));

        activityRepo.save(new Activity(null,
                "Email", "Proposal sent",
                "Sent detailed proposal PDF. Awaiting response from VP.",
                LocalDateTime.now().minusDays(1),
                deal2, null));

        activityRepo.save(new Activity(null,
                "Meeting", "Discovery call",
                "First meeting. Pain points: slow reporting, manual data entry.",
                LocalDateTime.now().minusDays(7),
                deal3, null));

        activityRepo.save(new Activity(null,
                "Call", "Introduction call",
                "Spoke with procurement team. They are evaluating 3 vendors.",
                LocalDateTime.now().minusDays(14),
                deal4, null));

        activityRepo.save(new Activity(null,
                "Meeting", "Contract signed",
                "Deal closed. Contract signed by both parties.",
                LocalDateTime.now().minusDays(5),
                deal5, null));

        System.out.println("✅ Seed data loaded successfully!");
        System.out.println("   - 4 accounts created");
        System.out.println("   - 5 opportunities created");
        System.out.println("   - 5 activities created");
    }
}