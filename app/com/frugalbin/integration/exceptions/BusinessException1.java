package com.frugalbin.integration.exceptions;

public class BusinessException1 extends Throwable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;

	public BusinessException1(int errorCode, String errorMessage)
	{
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BusinessException1(int errorCode, String errorMessage, Throwable cause)
	{
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BusinessException1()
	{
		super();
	}

	public BusinessException1(Throwable cause)
	{
		super(cause);
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}