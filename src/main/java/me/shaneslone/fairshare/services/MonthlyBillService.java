package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.models.MonthlyBill;

import java.util.List;

public interface MonthlyBillService {
    List<MonthlyBill> findAll();
    MonthlyBill findByMonthlyBillId(long id);
    void delete(long id);
    MonthlyBill save(MonthlyBill monthlyBill);
    MonthlyBill update(MonthlyBill monthlyBill);
    void deleteAll();
}
