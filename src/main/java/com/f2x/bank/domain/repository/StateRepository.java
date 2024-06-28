package com.f2x.bank.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f2x.bank.domain.enums.StateCode;
import com.f2x.bank.domain.model.State;

public interface StateRepository extends JpaRepository<State, Long> {

	Optional<State> findByCode(StateCode code);
}
