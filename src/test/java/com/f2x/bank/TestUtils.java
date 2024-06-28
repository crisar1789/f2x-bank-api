package com.f2x.bank;

import java.math.BigDecimal;

import com.f2x.bank.domain.enums.TransactionCode;
import com.f2x.bank.domain.model.Product;
import com.f2x.bank.domain.model.Transaction;
import com.f2x.bank.domain.model.TransactionType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

	public static Transaction createValidTransactionTransfer() {
	
		return Transaction.builder().accountDestination(createValidProduct("888888888"))
				.accountOrigin(createValidProduct("9999999999"))
				.transactionType(createValidTransactionPay(TransactionCode.T))
				.value(BigDecimal.valueOf(10000))
				.build();
	}
	
	public static TransactionType createValidTransactionPay(TransactionCode code) {
		
		return TransactionType.builder().id(Long.valueOf(2))
				.code(code).build();
	}
	
	public static Product createValidProduct(String accountNumber) {
		
		return Product.builder().accountNumber(accountNumber).build();
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
