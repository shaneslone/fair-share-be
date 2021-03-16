package me.shaneslone.fairshare;

import me.shaneslone.fairshare.models.*;
import me.shaneslone.fairshare.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    @Autowired
    private MonthlyBillService monthlyBillService;

    @Autowired
    private HouseholdService householdService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        userService.deleteAll();
        roleService.deleteAll();
        monthlyBillService.deleteAll();
        billService.deleteAll();
        householdService.deleteAll();

        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);

        User u1 = new User("shaneslone",
                "test",
                "slone.shane@gmail.com",
                "Shane",
                "Slone");
        u1.getRoles().add(new UserRoles(u1, r1));
        u1 = userService.save(u1);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2025);
        c.set(Calendar.MONTH, Calendar.MARCH);
        c.set(Calendar.DAY_OF_MONTH, 20);

        Bill b1 = new Bill("Power",
                "Kentucky Power",
                91.32,
                false,
                c.getTime().getTime(),
                false,
                "https://www.kentuckypower.com/");

        MonthlyBill mb1 = new MonthlyBill("March", 2021);
        mb1.getBills().add(b1);


        Household h1 = new Household();
        h1.getMonthlyBills().add(mb1);
        h1 = householdService.save(h1);
        u1.setHousehold(h1);
        userService.save(u1);

    }
}
