package com.f2x.bank.application.service;

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.domain.model.Transaction;

public interface TransactionServiceI {

	Transaction getTransactionById(Long id);
	Transaction createTransaction(Transaction transaction) throws F2XBankException;
}
