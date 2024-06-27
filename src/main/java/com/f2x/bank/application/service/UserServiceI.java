package com.f2x.bank.application.service;

import com.f2x.bank.domain.model.User;

public interface UserServiceI {

	User getUserByTypeAndNumberIdentification(String idType, String idNumber);
	User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
