package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.exceptions.ResourceNotFoundException;
import me.shaneslone.fairshare.models.Bill;
import me.shaneslone.fairshare.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "billService")
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;

    @Override
    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        billRepository.findAll().iterator().forEachRemaining(bills::add);
        return bills;
    }

    @Override
    public Bill findByBillId(long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill id " + id + " not found!"));
    }

    @Override
    public void delete(long id) {
        billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill id " + id + " not found!"));
        billRepository.deleteById(id);
    }

    @Override
    public Bill save(Bill bill) {
        Bill newBill = new Bill();
        if(bill.getBillId() != 0){
            billRepository.findById(bill.getBillId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bill id " + bill.getBillId() + " not found!"));
            newBill.setBillId(bill.getBillId());
        }
        newBill.setType(bill.getType());
        newBill.setCompnayName(bill.getCompnayName());
        newBill.setAmmount(bill.getAmmount());
        newBill.setIsPaid(bill.getIsPaid());
        newBill.setDueDate(bill.getDueDate());
        newBill.setIsRecurring(bill.getIsRecurring());
        newBill.setWebsite(bill.getWebsite());

        return billRepository.save(newBill);
    }

    @Override
    public Bill update(Bill bill, long id) {
        return null;
    }

    @Override
    public void deleteAll() {
        billRepository.deleteAll();
    }
}
