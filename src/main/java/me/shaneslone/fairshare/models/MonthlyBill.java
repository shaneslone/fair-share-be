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
    private long monthlyBillId;

    @NotNull
    private String month;

    private int year;

    @OneToMany(mappedBy = "monthlybill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "monthlybill", allowSetters = true)
    private List<Bill> bills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "householdId")
    @JsonIgnoreProperties(value = "monthlyBills", allowSetters = true)
    private Household household;

    public MonthlyBill() {
    }

    public MonthlyBill(@NotNull String month, int year) {
        this.month = month;
        this.year = year;
    }

    public long getMonthlyBillId() {
        return monthlyBillId;
    }

    public void setMonthlyBillId(long monthlyBillId) {
        this.monthlyBillId = monthlyBillId;
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
}
