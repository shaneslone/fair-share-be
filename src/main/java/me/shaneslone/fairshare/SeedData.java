package me.shaneslone.fairshare;

import me.shaneslone.fairshare.models.Bill;
import me.shaneslone.fairshare.models.Role;
import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.models.UserRoles;
import me.shaneslone.fairshare.services.BillService;
import me.shaneslone.fairshare.services.RoleService;
import me.shaneslone.fairshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        userService.deleteAll();
        roleService.deleteAll();

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

        userService.save(u1);

        Bill b1 = new Bill("Power",
                "Kentucky Power",
                91.32,
                false,
                10,
                false,
                null);

        billService.save(b1);

    }
}
