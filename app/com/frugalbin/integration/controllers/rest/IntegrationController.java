package com.frugalbin.integration.controllers.rest;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.F.Promise;
import play.libs.WS.Response;
import play.mvc.BodyParser;
import play.mvc.Result;

import com.frugalbin.common.exceptions.BusinessException;
import com.frugalbin.integration.controllers.base.BaseController;
import com.frugalbin.integration.controllers.base.RestClientCaller;

@Named
@Singleton
public class IntegrationController extends BaseController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationController.class);
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result login(String sessionId) throws BusinessException
	{
		Promise<Response> res = RestClientCaller.AUTH_LOGIN.sendGetRequest(sessionId);
		return convertObjectToJsonResponse("");
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result register()
	{
		return convertObjectToJsonResponse("");
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result myAccount()
	{
		return convertObjectToJsonResponse("");
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result myOrders()
	{
		return convertObjectToJsonResponse("");
	}
	@BodyParser.Of(BodyParser.Json.class)
	public Result getCityList()
	{
		return convertObjectToJsonResponse("");
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result getFlightSlotDetails()
	{
		return convertObjectToJsonResponse("");
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result bookFlightTicket()
	{
		return convertObjectToJsonResponse("");
	}
}
