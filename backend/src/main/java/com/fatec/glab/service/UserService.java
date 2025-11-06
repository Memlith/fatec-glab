package com.fatec.glab.service;

import java.util.List;
import java.util.Optional;

import com.fatec.glab.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.model.User;
import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> getAll() {
       List<User> user = userRepository.findAll();
        return user.stream().map(this::convertToUserResponseDTO).toList();
    }

    public UserResponseDTO getById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new IdNotFoundException("User com ID " + id + " não encontrado.");
        }

        return convertToUserResponseDTO(user.get());

    }

    public UserResponseDTO save(User user) {
        return convertToUserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO update(String id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IdNotFoundException("User com ID " + id + " não encontrado.");
        }

        User user = existingUser.get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return convertToUserResponseDTO(userRepository.save(user));


    }

    public void delete(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IdNotFoundException("User com ID " + id + " não encontrado.");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }

}
