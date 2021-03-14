package me.shaneslone.fairshare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monthlybills")
public class MonthlyBill extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long monthlybillid;

    @NotNull
    private String month;

    private int year;

    @OneToMany(mappedBy = "monthlyBill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "monthlyBill", allowSetters = true)
    private List<Bill> bills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "householdid")
    @JsonIgnoreProperties(value = "monthlyBills", allowSetters = true)
    private Household household;

    public MonthlyBill() {
    }

    public MonthlyBill(@NotNull String month, int year) {
        this.month = month;
        this.year = year;
    }

    public long getMonthlybillid() {
        return monthlybillid;
    }

    public void setMonthlybillid(long monthlyBillId) {
        this.monthlybillid = monthlyBillId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
