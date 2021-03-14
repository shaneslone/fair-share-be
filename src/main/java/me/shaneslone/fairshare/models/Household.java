package me.shaneslone.fairshare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "households")
public class Household extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long householdId;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "household", allowSetters = true)
    private Set<User> users;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "household", allowSetters = true)
    private Set<MonthlyBill> monthlyBills;
}
