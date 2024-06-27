package com.f2x.bank.application.exception;

@SuppressWarnings("serial")
public class F2XBankException extends RuntimeException{

	public F2XBankException(String message, String value) {
		super(message + value);
	}
	
	public F2XBankException(String message) {
		super(message);
	}
}
