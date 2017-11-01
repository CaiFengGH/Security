package com.caifeng.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -692319022270408749L;

	private String id;
	
	public UserNotFoundException(String id){
		super("User not found");
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
