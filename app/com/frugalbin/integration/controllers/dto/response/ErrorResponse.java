package com.frugalbin.integration.controllers.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for sending ErrorMessages
 * 
 * @author pkonwar
 */
public class ErrorResponse implements Serializable
{

	private static final long serialVersionUID = 1L;

	public ErrorResponse()
	{
	}

	/**
	 * Generates ErrorResponse accepting errorCode and List of errorMessages
	 * 
	 * @param errorCode
	 * @param errorMessages
	 */
	public ErrorResponse(int errorCode, List<String> errorMessages)
	{
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
	}

	/**
	 * Generates ErrorResponse accepting a errorCode and a errorMessage
	 * 
	 * @param errorCode
	 * @param errorMessage
	 */
	public ErrorResponse(int errorCode, String errorMessage)
	{
		this.errorCode = errorCode;
		this.errorMessages = new ArrayList<>();
		this.errorMessages.add(errorMessage);
	}

	/**
	 * Generates ErrorResponse accepting errorCode and either an errorMessage or
	 * a list of errorMessages.
	 * 
	 * @param errorCode
	 * @param errorMessage
	 * @param errorMessages
	 */
	public ErrorResponse(int errorCode, String errorMessage, List<String> errorMessages)
	{
		this.errorCode = errorCode;
		if (errorMessage != null && errorMessage.trim().length() > 0)
		{
			this.errorMessages = new ArrayList<>();
			this.errorMessages.add(errorMessage);
		}
		else
		{
			this.errorMessages = errorMessages;
		}
	}

	public int errorCode;

	public List<String> errorMessages;

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

	public List<String> getErrorMessages()
	{
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages)
	{
		this.errorMessages = errorMessages;
	}

}
