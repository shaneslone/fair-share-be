package me.shaneslone.fairshare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monthlybills")
public class MonthlyBill extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long monthlybillid;

    private long date;

    @OneToMany(mappedBy = "monthlyBill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "monthlyBill", allowSetters = true)
    private List<Bill> bills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "householdid")
    @JsonIgnoreProperties(value = {"monthlyBills", "users"}, allowSetters = true)
    private Household household;

    public MonthlyBill() {
    }

    public MonthlyBill(long date) {
        this.date = date;
    }

    public long getMonthlybillid() {
        return monthlybillid;
    }

    public void setMonthlybillid(long monthlyBillId) {
        this.monthlybillid = monthlyBillId;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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
