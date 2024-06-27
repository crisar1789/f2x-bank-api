package com.f2x.bank.domain.enums;

public enum DocumentCode {
	CC("CC"), NIT("NIT"), CE("CE"); 
	
	private final String code;
	
	private DocumentCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public static DocumentCode valueOfCode(String code) {
	    for (DocumentCode e : values()) {
	        if (e.code.equals(code)) {
	            return e;
	        }
	    }
	    return null;
	}
}
