package com.salesforce.copilot.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String industry;
    private String website;
    private String phone;

    @Column(name = "annual_revenue")
    private Double annualRevenue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Opportunity> opportunities;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}