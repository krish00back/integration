package com.frugalbin.integration.exceptions;

import java.util.List;

import com.frugalbin.common.exceptions.BusinessException;

public class ValidationException extends BusinessException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> errorMessages;

	public List<String> getErrorMessages()
	{
		return errorMessages;
	}

	public ValidationException(int errorCode, String errorMessage, List<String> errorMessages)
	{
		super(errorCode, errorMessage);
		this.errorMessages = errorMessages;
	}

	public ValidationException(int errorCode, String errorMessage, List<String> errorMessages, Throwable cause)
	{
		super(errorCode, errorMessage, cause);
		this.errorMessages = errorMessages;
	}

	public ValidationException()
	{
		super();
	}

	public ValidationException(Throwable cause)
	{
		super(cause);
	}
}