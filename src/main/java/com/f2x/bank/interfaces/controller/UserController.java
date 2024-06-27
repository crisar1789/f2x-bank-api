package com.f2x.bank.interfaces.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.application.service.UserServiceI;
import com.f2x.bank.domain.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceI userService;
	
	@GetMapping("/{idType}/{idNumber}")
    public ResponseEntity<Object> getUserById(@PathVariable String idType, @PathVariable String idNumber) {
		ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(userService
					.getUserByTypeAndNumberIdentification(idType, idNumber), HttpStatus.CREATED);
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return response;
    }
	
	@PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
		ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
		} catch(ConstraintViolationException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(),	HttpStatus.BAD_REQUEST);
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
