package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.exceptions.ResourceNotFoundException;
import me.shaneslone.fairshare.models.Role;
import me.shaneslone.fairshare.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role findRoleById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name);
        if (role == null){
            throw new ResourceNotFoundException("Role name " + name + " not found!");
        }
        return role;
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }

    @Override
    public Role update(Role role, long id) {
        if (role.getName() == null){
            throw new ResourceNotFoundException("No role name found to update!");
        }
        findRoleById(id);
        roleRepository.updateRoleName(userAuditing.getCurrentAuditor().get(), id, role.getName());
        return findRoleById(id);
    }
}
