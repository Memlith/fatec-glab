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
        List<User> users = userRepository.findAll();

        return users.stream().map(this::convertToUserResponseDTO).toList();
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(String id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }

        throw new IdNotFoundException("User com ID " + id + " não encontrado.");

    }

    public void delete(String id) {
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
