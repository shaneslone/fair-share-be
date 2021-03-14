package me.shaneslone.fairshare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bills")
public class Bill  extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billid;

    @NotNull
    private String type;

    @NotNull
    private String compnayName;

    private double ammount;

    private boolean isRecurring;

    private long dueDate;

    private boolean isPaid;

    private String website;

    @ManyToOne
    @JoinColumn(name = "monthlybillid")
    @JsonIgnoreProperties(value = "monthlyBill", allowSetters = true)
    private MonthlyBill monthlyBill;

    public Bill() {
    }

    public Bill(@NotNull String type,
                @NotNull String compnayName,
                double ammount,
                boolean isRecurring,
                long dueDate,
                boolean isPaid,
                String website) {
        this.type = type;
        this.compnayName = compnayName;
        this.ammount = ammount;
        this.isRecurring = isRecurring;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.website = website;
    }

    public long getBillid() {
        return billid;
    }

    public void setBillid(long billId) {
        this.billid = billId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompnayName() {
        return compnayName;
    }

    public void setCompnayName(String compnayName) {
        this.compnayName = compnayName;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public boolean getIsRecurring() {
        return this.isRecurring;
    }

    public void setIsRecurring(boolean recurring) {
        this.isRecurring = recurring;
    }

    public long getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsPaid() {
        return this.isPaid;
    }

    public void setIsPaid(boolean paid) {
        this.isPaid = paid;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public MonthlyBill getMonthlyBill() {
        return monthlyBill;
    }

    public void setMonthlyBill(MonthlyBill monthlyBill) {
        this.monthlyBill = monthlyBill;
    }
}
