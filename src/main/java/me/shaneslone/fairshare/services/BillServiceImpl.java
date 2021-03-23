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
    
    @Autowired
    private HelperFunctions helperFunctions;

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
        if(bill.getBillid() != 0){
            billRepository.findById(bill.getBillid())
                    .orElseThrow(() -> new ResourceNotFoundException("Bill id " + bill.getBillid() + " not found!"));
            newBill.setBillid(bill.getBillid());
        }
        newBill.setType(bill.getType());
        newBill.setCompanyName(bill.getCompanyName());
        newBill.setAmount(bill.getAmount());
        newBill.setPaid(bill.isRecurring());
        newBill.setDueDate(bill.getDueDate());
        newBill.setRecurring(bill.isRecurring());
        newBill.setWebsite(bill.getWebsite());
        newBill.setMonthlyBill(bill.getMonthlyBill());

        return billRepository.save(newBill);
    }

    @Override
    public Bill update(Bill bill, long id) {
        Bill currentBill = findByBillId(id);
        if(helperFunctions.isHouseholdMember(currentBill.getMonthlyBill().getHousehold().getUsers())){
            if(bill.getType() != null){
                currentBill.setType(bill.getType());
            }
            if(bill.getCompanyName() != null){
                currentBill.setCompanyName(bill.getCompanyName());
            }
            if(bill.getAmount() > 0){
                currentBill.setAmount(bill.getAmount());
            }
            if(bill.getDueDate() > 0){
                currentBill.setDueDate(bill.getDueDate());
            }
            if(bill.getWebsite() != null){
                currentBill.setWebsite(bill.getWebsite());
            }
            if(bill.getMonthlyBill() != null){
                currentBill.setMonthlyBill(bill.getMonthlyBill());
            }
            return billRepository.save(currentBill);
        } else {
            throw new ResourceNotFoundException("User is not authorized to make change!");
        }
    }

    @Override
    public void setPaid(boolean paid, long id) {
        Bill currentBill = findByBillId(id);
        if(helperFunctions.isHouseholdMember(currentBill.getMonthlyBill().getHousehold().getUsers())){
            currentBill.setPaid(paid);
            billRepository.save(currentBill);
        } else {
            throw new ResourceNotFoundException("User is not authorized to make change!");
        }
    }

    @Override
    public void deleteAll() {
        billRepository.deleteAll();
    }
}
