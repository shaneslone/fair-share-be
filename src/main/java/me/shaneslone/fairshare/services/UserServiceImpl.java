package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.exceptions.ResourceNotFoundException;
import me.shaneslone.fairshare.models.Role;
import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.models.UserRoles;
import me.shaneslone.fairshare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<User> findAll() {
        List<User> rtnList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public User findByUserId(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByUsername(name.toLowerCase());
        if(user == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return user;
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();
        if(user.getUserid() != 0){
            userRepository.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }
        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());

        for(UserRoles ur : user.getRoles()) {
            Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
            newUser.getRoles().add(new UserRoles(newUser, addRole));
        }

        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findByUserId(id);
        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if(user.getUsername() != null){
                currentUser.setUsername(user.getUsername().toLowerCase());
            }
            if(user.getPassword() != null){
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }
            if(user.getEmail() != null){
                currentUser.setEmail(user.getEmail());
            }
            if(user.getFirstname() != null){
                currentUser.setFirstname(user.getFirstname());
            }
            if(user.getLastname() != null){
                currentUser.setLastname(user.getLastname());
            }
            if(user.getRoles().size() > 0){
                currentUser.getRoles().clear();
                for(UserRoles ur : user.getRoles()){
                    Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
                    currentUser.getRoles().add(new UserRoles(currentUser, addRole));
                }
            }
            return userRepository.save(currentUser);
        } else {
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
