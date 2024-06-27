package com.f2x.bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
    
    @Column(name = "account_number")
    private String accountNumber;
    
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    
    @Column(name = "balance")
    private BigDecimal balance;
    
    @Column(name = "gmf_exempt")
    private boolean gmfExempt;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    
    @NotNull(message = "User is mandatory")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
