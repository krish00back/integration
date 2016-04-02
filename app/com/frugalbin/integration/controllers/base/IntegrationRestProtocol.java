package com.frugalbin.integration.controllers.base;

import com.frugalbin.common.rest.client.RequestType;
import com.frugalbin.common.rest.client.RestClientProtocolInterface;

public enum IntegrationRestProtocol implements RestClientProtocolInterface
{
	// AUTHENTICATION MODULE
	/**
	 * GET <context_url>/authentication/getUserId?sessionId=<sessionId><br>
	 * params req : 1) <sessionId>
	 */
	AUTH_GET_USER_ID(
			RequestType.GET,
			IntegrationRestProtocol.AUTH_URL + "getUserId?sessionId=<sessionId>",
			"<sessionId>"),
	/**
	 * POST <context_url>/authentication/login<br>
	 * {<br>
	 * "username":"<username>",<br>
	 * "password":"<password>"<br>
	 * }<br>
	 */
	AUTH_LOGIN(RequestType.POST, IntegrationRestProtocol.AUTH_URL + "login"),
	AUTH_REGISTER(RequestType.POST, IntegrationRestProtocol.AUTH_URL + "signup"),
	AUTH_ACC_DETAILS(RequestType.POST, IntegrationRestProtocol.AUTH_URL + "account"),

	// PAYMENT MODULE

	// COMMUNICATION MODULE
	COMM_GET_DETAILS(RequestType.GET, IntegrationRestProtocol.COMMUNICATION_URL + "<id>", "<id>"),
	COMM_SEND(RequestType.POST, IntegrationRestProtocol.COMMUNICATION_URL + "send"),
	COMM_CREATE(RequestType.POST, IntegrationRestProtocol.COMMUNICATION_URL + "create"),
	COMM_GET_TEMPLATE(RequestType.GET, IntegrationRestProtocol.COMMUNICATION_URL + "template"
			+ IntegrationRestProtocol.URL_PATH_SEPARATOR + "<templName>", "<templateName>"),

	// INVENTORY MODULE
	INV_AIR_GET_CITY(RequestType.GET, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "getCityList"),
	INV_AIR_GET_FLIGHT_SLOTS(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "getFlightSlotDetails"),
	INV_AIR_BOOK_TICKET(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "bookFlightTicket"),

	// DELIVERY MODULE
	;

	private static final String URL_PATH_SEPARATOR = "/";

	private static final String CURR_URL = "http://localhost:9001" + URL_PATH_SEPARATOR;

	private static final String AUTH_URL = CURR_URL + "authentication" + URL_PATH_SEPARATOR;
	private static final String PAYMENT_URL = CURR_URL + "payment" + URL_PATH_SEPARATOR;
	private static final String COMMUNICATION_URL = CURR_URL + "communication" + URL_PATH_SEPARATOR;
	private static final String INVENTORY_AIRLINE_URL = CURR_URL + "inventoryAirline" + URL_PATH_SEPARATOR;
	private static final String DELIVERY_URL = CURR_URL + "delivery" + URL_PATH_SEPARATOR;

	private final String url;

	private RequestType reqType;

	private String[] params;

	private IntegrationRestProtocol(RequestType reqType, String url, String... params)
	{
		this.reqType = reqType;
		this.url = url;
		this.params = params;
	}

	public String getUrl()
	{
		return url;
	}

	public RequestType getReqType()
	{
		return reqType;
	}

	public String[] getParams()
	{
		return params;
	}

	@Override
	public String getName()
	{
		return this.name();
	}
}