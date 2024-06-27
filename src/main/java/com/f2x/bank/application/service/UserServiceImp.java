package com.f2x.bank.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.domain.enums.DocumentCode;
import com.f2x.bank.domain.model.User;
import com.f2x.bank.domain.repository.UserRepository;

@Service
public class UserServiceImp implements UserServiceI {

	@Autowired
	private UserRepository userRepository;
	
	@Override
    public User getUserByTypeAndNumberIdentification(String idType, String idNumber) {
		return getUser(idType, idNumber).orElseThrow(() -> new F2XBankException("User not found"));
    }
	
	@Override
    public User createUser(User user) {
		checkUserExist(user);
		checkUserAge(user);
        user.setCreatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }
	
	private void checkUserExist(User user) {
		Optional<User> userExist = getUser(user.getIdentificationType().getCode().getCode(),
				user.getIdentificationNumber());
		
		if (userExist.isPresent()) {
			throw new F2XBankException("User already exist");
		}
	}
	
	private Optional<User> getUser(String idType, String idNumber) {
		DocumentCode code = DocumentCode.valueOf(idType);
        return userRepository.findByTypeAndNumberIdentification(code, idNumber);
	}

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setBirthDate(user.getBirthDate());
        checkUserAge(existingUser);
        existingUser.setUpdatedDate(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    private void checkUserAge(User user) {
    	
    	LocalDate now = LocalDate.now();

    	Period period = Period.between(user.getBirthDate(), now);
    	
    	if(period.getYears() < 18) {
    		throw new F2XBankException("User is not old enough to open an account. It should be 18");
    	}
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
