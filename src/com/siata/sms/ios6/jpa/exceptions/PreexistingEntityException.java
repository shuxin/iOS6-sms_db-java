package com.siata.sms.ios6.jpa.exceptions;

public class PreexistingEntityException extends Exception {
	public PreexistingEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreexistingEntityException(String message) {
		super(message);
	}
}
