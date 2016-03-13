package com.frugalbin.integration.controllers.base;

import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;

import com.fasterxml.jackson.databind.JsonNode;
import com.frugalbin.common.exceptions.BusinessException;

public enum RestClientCaller
{
	// AUTHENTICATION MODULE
	/**
	 * GET <context_url>/authentication/getUserId?sessionId=<sessionId><br>
	 * params req : 1) <sessionId>
	 */
	AUTH_GET_USER_ID(RequestType.GET, RestClientCaller.AUTH_URL + "getUserId?sessionId=<sessionId>", "<sessionId>"),
	/**
	 * POST <context_url>/authentication/login<br>
	 * {<br>
	 * "username":"<username>",<br>
	 * "password":"<password>"<br>
	 * }<br>
	 */
	AUTH_LOGIN(RequestType.POST, RestClientCaller.AUTH_URL + "login"),
	AUTH_REGISTER(RequestType.POST, RestClientCaller.AUTH_URL + "signup"),
	AUTH_ACC_DETAILS(RequestType.POST, RestClientCaller.AUTH_URL + "account")

	// PAYMENT MODULE

	// COMMUNICATION MODULE

	// INVENTORY MODULE

	// DELIVERY MODULE
	;

	private static final String URL_PATH_SEPARATOR = "/";

	private static final String CURR_URL = "http://localhost:9000" + URL_PATH_SEPARATOR;

	private static final String AUTH_URL = CURR_URL + "authentication" + URL_PATH_SEPARATOR;
	private static final String PAYMENT_URL = CURR_URL + "payment" + URL_PATH_SEPARATOR;
	private static final String COMMUNICATION_URL = CURR_URL + "communication" + URL_PATH_SEPARATOR;
	private static final String INVENTORY_URL = CURR_URL + "inventory" + URL_PATH_SEPARATOR;
	private static final String DELIVERY_URL = CURR_URL + "delivery" + URL_PATH_SEPARATOR;

	private final String url;

	private RequestType reqType;

	private String[] params;

	private RestClientCaller(RequestType reqType, String url, String... params)
	{
		this.reqType = reqType;
		this.url = url;
		this.params = params;
	}

	public Promise<Response> sendGetRequest(String... params) throws BusinessException
	{
		String finalUrl = getFinalUrl(params);
		
		WSRequestHolder requestHolder = WS.url(finalUrl);

		Promise<Response> res = requestHolder.get();

		return res;
	}

	private String getFinalUrl(String[] params) throws BusinessException
	{
		if (this.params.length > params.length)
		{
			throw new BusinessException(1001, "Less num of params passed for " + this + " : req=" + this.params.length
					+ ", passed=" + params.length);
		}

		String finalUrl = this.url;

		if (this.params.length > 0)
		{
			for (int i = 0; i < this.params.length; i++)
			{
				finalUrl = finalUrl.replace(this.params[i], params[i]);
			}
		}
		
		return finalUrl;
	}

	public Promise<Response> sendOtherRequest(JsonNode reqNode, String... params) throws BusinessException
	{
		String finalUrl = getFinalUrl(params);
		WSRequestHolder requestHolder = WS.url(this.url);

		Promise<Response> res = null;
		switch (this.reqType)
		{
			case POST:
				res = requestHolder.post(reqNode);
				break;
			case PUT:
				res = requestHolder.put(reqNode);
				break;
			default:
				break;
		}

		return res;
	}

	private static enum RequestType
	{
		GET,
		POST,
		PUT
	}
}
