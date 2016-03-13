package com.frugalbin.integration.controllers.base;

import org.apache.http.HttpStatus;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.frugalbin.integration.controllers.dto.response.ErrorResponse;
import com.frugalbin.integration.exceptions.BusinessException1;
import com.frugalbin.integration.exceptions.ErrorConstants;

public class BaseController extends Controller
{

	/**
	 * Converts RequestBody to ObjectDto
	 * 
	 * @param requestBody
	 * @param clazz
	 * @return
	 * @throws BusinessException1
	 */
	public <A> A convertRequestBodyToObject(RequestBody requestBody, Class<A> clazz) throws BusinessException1
	{
		try
		{
			JsonNode jsonNode = requestBody.asJson();
			return Json.fromJson(jsonNode, clazz);
		}
		catch (Exception ex)
		{
			ErrorConstants error = ErrorConstants.INVALID_REQUEST_DATA;
			throw new BusinessException1(error.errorCode, error.errorMessage, ex.getCause());
		}
	}

	/**
	 * Generate error which is unknown to system
	 * 
	 * @return
	 */
	public ErrorResponse unknownErrorResponse()
	{
		ErrorConstants error = ErrorConstants.CONTACT_SYSTEM_ADMINISTRATOR;
		return new ErrorResponse(error.errorCode, error.errorMessage);
	}

	/**
	 * Success Object in JSON.
	 * 
	 * @param object
	 * @return
	 */
	public Result convertObjectToJsonResponse(Object object)
	{
		JsonNode jsonNode = Json.toJson(object);
		return ok(jsonNode);
	}

	/**
	 * Validation error to JSON response.
	 * 
	 * @param object
	 * @return
	 */
	public Result validationErrorToJsonResponse(Object object)
	{
		JsonNode jsonNode = Json.toJson(object);
		return status(HttpStatus.SC_BAD_REQUEST, jsonNode);
	}

	/**
	 * Finds ErrorType based on errorResponse and send specific response. Rules
	 * : ErrorCode Starts with :: 4 : Validation Error 5 : Internal Server Error
	 * 
	 * @param errorResponse
	 * @return
	 */
	public Result errorObjectToJsonResponse(ErrorResponse errorResponse)
	{

		int errorCode = errorResponse.getErrorCode();
		// char errorType = getFirstDigit(errorCode.charAt(0);

		// the httpErrorCode
		int httpErrorCode;

		switch (errorCode / 100)
		{
			case 4:
				httpErrorCode = HttpStatus.SC_BAD_REQUEST;
				break;

			case 5:
				httpErrorCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
				break;

			default:
				httpErrorCode = HttpStatus.SC_BAD_REQUEST;
				break;
		}
		return errorObjectToJsonResponse(httpErrorCode, errorResponse);
	}

	/**
	 * Error Object to JSON response
	 * 
	 * @param httpErrorCode
	 * @param errorResponse
	 * @return
	 */
	public Result errorObjectToJsonResponse(int httpErrorCode, ErrorResponse errorResponse)
	{
		JsonNode jsonNode = Json.toJson(errorResponse);
		return status(httpErrorCode, jsonNode);
	}
}