package com.fatec.glab.service;

import com.fatec.glab.dto.user.UserCreateRequestDTO;
import com.fatec.glab.exception.UserAlreadyExist;
import com.fatec.glab.model.User;
import com.fatec.glab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCaseAndActiveTrue(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public User registerUser(UserCreateRequestDTO userRequestDTO) {

        boolean userExist = userRepository.findByEmailIgnoreCase(userRequestDTO.email()).isPresent();
        if (userExist) {
            throw new UserAlreadyExist("User already exists");
        }

        var encryptedPassword = passwordEncoder.encode(userRequestDTO.password());
        var user = new User(userRequestDTO, encryptedPassword);

        return userRepository.save(user);
    }
}
