package com.f2x.bank.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.f2x.bank.domain.enums.DocumentCode;
import com.f2x.bank.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u inner join u.identificationType i where i.code.code = :idType and u.identificationNumber = :idNumber")
	Optional<User> findByTypeAndNumberIdentification(DocumentCode idType, String idNumber);
}
