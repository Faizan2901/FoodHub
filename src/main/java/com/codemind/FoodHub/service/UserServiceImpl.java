package com.codemind.FoodHub.service;

import com.codemind.FoodHub.dao.RoleDAO;
import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.Role;
import com.codemind.FoodHub.entity.User;
import com.codemind.FoodHub.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }


    @Override
    public void save(WebUser webUser) {

        User user=new User();
        user.setUserName(webUser.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(webUser.getPassword()));
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEmail(webUser.getEmail());

        // give user default role of "customer"
        user.setRoles(Arrays.asList(roleDAO.findByName("ROLE_CUSTOMER")));

        userDAO.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userDAO.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }
        return authorities;

    }
}
