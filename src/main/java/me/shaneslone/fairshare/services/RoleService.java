package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findRoleById(long id);
    Role save(Role role);
    Role findByName(String name);
    void deleteAll();
    Role update(Role role, long id);

}
