package com.api.usersChallenge.security;

import com.api.usersChallenge.models.UserEntity;
import com.api.usersChallenge.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findOneByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found with given email: "+ email));
        return new UserDetailsImpl(user);
    }
}
