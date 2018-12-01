package com.app.filemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/***
 * Custom Exception to handle exception while downloading file
 * 
 * @author Ravi Teja Pasarakonda
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserFileNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6681155708454443587L;

	public UserFileNotFoundException(String message){
		super(message);
	}
	
	public UserFileNotFoundException(String message, Throwable ex){
		super(message, ex);
	}

}
