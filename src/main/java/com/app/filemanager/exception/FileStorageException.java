package com.app.filemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/***
 * Custom Exception to handle exceptions while storing file
 * 
 * @author Ravi Teja Pasarakonda
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileStorageException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6137941429221928366L;

	public FileStorageException(String message){
		super(message);
	}
	
	public FileStorageException(String message, Throwable ex){
		super(message, ex);
	}

}
