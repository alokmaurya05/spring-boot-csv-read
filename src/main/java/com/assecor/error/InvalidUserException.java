package com.assecor.error;

/**
 * This class throw Exception 
 * When Requested user not present 
 */
public class InvalidUserException extends RuntimeException {
	
	private static final long serialVersionUID = 2769009288670086378L;

	public InvalidUserException(Long userId)
	{
		super("Invalid user id "+userId);
		
		
	}

}
