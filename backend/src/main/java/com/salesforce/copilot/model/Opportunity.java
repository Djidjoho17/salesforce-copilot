package com.salesforce.copilot.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String stage;
    private Double amount;

    @Column(name = "close_date")
    private LocalDate closeDate;

    private Integer probability;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @JsonIgnore
    @OneToMany(mappedBy = "opportunity", cascade = CascadeType.ALL)
    private List<Activity> activities;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}