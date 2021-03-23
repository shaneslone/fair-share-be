package me.shaneslone.fairshare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bills")
@JsonIgnoreProperties(value = "monthlyBill", allowSetters = true)
public class Bill  extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billid;

    @NotNull
    private String type;

    @NotNull
    private String companyName;

    private double amount;

    @JsonProperty
    private boolean isRecurring;

    private long dueDate;

    @JsonProperty
    private boolean isPaid;

    private String website;

    @ManyToOne
    @JoinColumn(name = "monthlybillid")
    @JsonIgnoreProperties(value = "bills", allowSetters = true)
    private MonthlyBill monthlyBill;

    public Bill() {
    }

    public Bill(@NotNull String type,
                @NotNull String companyName,
                double amount,
                boolean isRecurring,
                long dueDate,
                boolean isPaid,
                String website) {
        this.type = type;
        this.companyName = companyName;
        this.amount = amount;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String compnayName) {
        this.companyName = compnayName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double ammount) {
        this.amount = ammount;
    }

    public long getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
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
