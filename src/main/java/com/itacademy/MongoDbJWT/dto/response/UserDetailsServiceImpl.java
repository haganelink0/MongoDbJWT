package com.itacademy.MongoDbJWT.dto.response;

import com.itacademy.MongoDbJWT.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;

public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserPlayerResponse userPlayer = userRepository.findByName(name);
        if (userPlayer == null) {
            throw new UsernameNotFoundException(name);
        }
        return new User(userPlayer.getName(), userPlayer.getPassword(),emptyList());
    }
}
