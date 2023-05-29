package com.thaieats.serviceImp;

import com.thaieats.model.Privilege;
import com.thaieats.model.Role;
import com.thaieats.model.User;
import com.thaieats.repository.RoleRepository;
import com.thaieats.repository.UserRepository;
import com.thaieats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        Set<Role> roles = user.getRoles();
        Set<Privilege> privileges = new HashSet<>();

        for (Role role : roles) {
            Set<Privilege> rolePrivileges = role.getPrivileges();
            privileges.addAll(rolePrivileges);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getPassword(), user.getPassword(), authorities);
    }

    @Override
    public User createUser(User userInfo) {
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        Role tempRole = roleRepository.findByName("USER");
        userInfo.setRoles(new HashSet<Role>(Set.of(tempRole)));
        userInfo.setIsActive(true);
        return userRepository.save(userInfo);
    }

}
