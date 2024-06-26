package com.f2x.bank.application.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.domain.model.User;
import com.f2x.bank.domain.repository.UserRepository;

@Service
public class UserServiceImp implements UserServiceI {

	@Autowired
	private UserRepository userRepository;
	
	@Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
	
	@Override
    public User createUser(User user) {
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setUpdatedDate(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
