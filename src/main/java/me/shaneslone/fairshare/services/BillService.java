package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.models.Bill;

import java.util.List;

public interface BillService {
    List<Bill> findAll();
    Bill findByBillId(long id);
    void delete(long id);
    Bill save(Bill bill);
    Bill update(Bill bill, long id);
    void setPaid(boolean paid, long id);
    void deleteAll();
}
