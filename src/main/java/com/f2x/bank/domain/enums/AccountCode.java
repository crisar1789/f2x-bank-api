package com.f2x.bank.domain.enums;

public enum AccountCode {
	AH("AH", 53), 
	CO("CO", 33);
	
	private final String code;
	private final Integer number;
	
	private AccountCode(String code, Integer number) {
		this.number = number;
		this.code = code;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public static AccountCode valueOfCode(String code) {
	    for (AccountCode e : values()) {
	        if (e.code.equals(code)) {
	            return e;
	        }
	    }
	    return null;
	}
}
