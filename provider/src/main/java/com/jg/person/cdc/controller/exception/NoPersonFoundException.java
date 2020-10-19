package com.jg.person.cdc.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPersonFoundException extends RuntimeException {

	public NoPersonFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
