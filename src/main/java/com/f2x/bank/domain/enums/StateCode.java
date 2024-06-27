package com.f2x.bank.domain.enums;

public enum StateCode {
	A("Active"), I("Inactive"), C("Cacel"); 
	
	private final String name;
	
	private StateCode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static StateCode valueOfCode(String name) {
	    for (StateCode e : values()) {
	        if (e.name.equals(name)) {
	            return e;
	        }
	    }
	    return null;
	}
}
