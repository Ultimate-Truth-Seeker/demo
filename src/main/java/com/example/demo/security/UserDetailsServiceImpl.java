package com.example.demo.security;

import com.example.demo.model.DBUser;
import com.example.demo.repository.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private users userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<DBUser> users = userRepository.findAll();
        for (DBUser u : users) {
            if (u.getName().equals(username)) {
                return UserDetailsImpl.build(u);
            }
        }
        throw new UsernameNotFoundException("User Not Found with username: " + username);

    }

}