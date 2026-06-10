package com.fatec.glab.service;

import com.fatec.glab.dto.user.UserCreateRequestDTO;
import com.fatec.glab.exception.UserAlreadyExist;
import com.fatec.glab.model.User;
import com.fatec.glab.repository.UserRepository;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
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
    public User createUserWithEmailAndPassword(UserCreateRequestDTO userRequestDTO) {

        boolean userExist = userRepository.findByEmailIgnoreCase(userRequestDTO.email()).isPresent();
        if (userExist) {
            throw new UserAlreadyExist("User already exists");
        }

        UserRecord firebaseUser = null;
        try {
            var createRequest = new UserRecord.CreateRequest()
                    .setEmail(userRequestDTO.email())
                    .setPassword(userRequestDTO.password())
                    .setDisplayName(userRequestDTO.name());
                    
            firebaseUser = FirebaseAuth.getInstance().createUser(createRequest);
        } catch (FirebaseAuthException e) {
            if (e.getAuthErrorCode() == AuthErrorCode.EMAIL_ALREADY_EXISTS) {
                firebaseUser = null;
            } else {
                throw new RuntimeException("Failed to create Firebase user", e);
            }
        }

        try {
            var encryptedPassword = passwordEncoder.encode(userRequestDTO.password());
            var user = new User(userRequestDTO, encryptedPassword);
            return userRepository.save(user);
        } catch (Exception e) {
            if (firebaseUser != null) {
                try {
                    FirebaseAuth.getInstance().deleteUser(firebaseUser.getUid());
                } catch (FirebaseAuthException deleteException) {
                    // Ignore delete failure; registration still failed.
                }
            }
            throw e;
        }
    }
}
