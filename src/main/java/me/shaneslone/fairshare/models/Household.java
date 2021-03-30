package me.shaneslone.fairshare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "households")
public class Household extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long householdid;

    @NotNull
    private String householdKey;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "household", allowSetters = true)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "household", allowSetters = true)
    private Set<MonthlyBill> monthlyBills = new HashSet<>();

    public Household() {
    }

    public long getHouseholdid() {
        return householdid;
    }

    public void setHouseholdid(long householdId) {
        this.householdid = householdId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<MonthlyBill> getMonthlyBills() {
        return monthlyBills;
    }

    public void setMonthlyBills(Set<MonthlyBill> monthlyBills) {
        this.monthlyBills = monthlyBills;
    }

    public String getHouseholdKey() {
        return householdKey;
    }

    public void setHouseholdKey(String householdKey) {
        this.householdKey = householdKey;
    }

    @Override
    public String toString() {
        return "Household{" +
                "householdid=" + householdid +
                ", householdKey='" + householdKey + '\'' +
                ", users=" + users +
                ", monthlyBills=" + monthlyBills +
                '}';
    }
}
