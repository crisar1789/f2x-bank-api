package com.f2x.bank.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.f2x.bank.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
