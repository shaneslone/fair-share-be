package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.exceptions.ResourceNotFoundException;
import me.shaneslone.fairshare.models.Household;
import me.shaneslone.fairshare.models.MonthlyBill;
import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.repositories.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MonthlyBillService monthlyBillService;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<Household> findAll() {
        List<Household> households = new ArrayList<>();
        householdRepository.findAll().iterator().forEachRemaining(households::add);
        return households;
    }

    @Override
    public Household findByHouseholdId(long id) {
        return householdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Household id " + id + " not found!"));
    }

    @Override
    public Household findByHouseholdKey(String key) {
        Household h = householdRepository.findHouseholdByHouseholdKey(key);
        if(h == null){
            throw new ResourceNotFoundException("Household with key " + key + " not found!");
        }
        return h;
    }

    @Override
    public void delete(long id) {
        householdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Household id " + id + " not found!"));
        householdRepository.deleteById(id);
    }

    @Override
    public Household save(Household household) {
        System.out.println(household);
        Household newHousehold = new Household();

        if(household.getHouseholdid() != 0){
            householdRepository.findById(household.getHouseholdid())
                    .orElseThrow(() -> new ResourceNotFoundException("Household id " + household.getHouseholdid() + " not found!"));
            newHousehold.setHouseholdid(household.getHouseholdid());
        }
        newHousehold.setHouseholdKey(household.getHouseholdKey());

        if(newHousehold.getHouseholdid() == 0){
            newHousehold = householdRepository.save(newHousehold);
        }

        for (User u : household.getUsers()){
            u.setHousehold(newHousehold);
            u = userService.save(u);
            newHousehold.getUsers().add(u);
        }
        for(MonthlyBill mb : household.getMonthlyBills()){
            mb.setHousehold(newHousehold);
            mb = monthlyBillService.save(mb);
            newHousehold.getMonthlyBills().add(mb);
        }
        return householdRepository.save(newHousehold);
    }

    @Override
    public void deleteAll() {
        householdRepository.deleteAll();
    }
}
