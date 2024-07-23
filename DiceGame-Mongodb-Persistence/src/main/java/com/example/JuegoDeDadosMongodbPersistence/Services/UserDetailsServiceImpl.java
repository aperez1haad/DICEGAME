package com.example.JuegoDeDadosMongodbPersistence.Services;

import com.example.JuegoDeDadosMongodbPersistence.Model.UserEntity;
import com.example.JuegoDeDadosMongodbPersistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(username);

        if (byEmail.isPresent()) {
            UserEntity userEntity = byEmail.get();

            Collection<? extends GrantedAuthority> authorities = userEntity
                    .getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))).collect(Collectors.toSet());

            return new User(userEntity.getEmail(), userEntity.getPassword(),true, true, true,true, authorities);

        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

    }
}
