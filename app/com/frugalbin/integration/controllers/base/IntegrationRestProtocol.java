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
	AUTH_GET_USER(RequestType.POST, IntegrationRestProtocol.AUTH_URL + "getUser"),

	// PAYMENT MODULE
	PAYMENT_GET_PGURL(RequestType.POST, IntegrationRestProtocol.PAYMENT_URL + "pgurl"),
	PAYMENT_VALIDATE_PG_RESPONSE(RequestType.POST, IntegrationRestProtocol.PAYMENT_URL + "validatePGResponse"),

	// COMMUNICATION MODULE
	COMM_GET_DETAILS(RequestType.GET, IntegrationRestProtocol.COMMUNICATION_URL + "<id>", "<id>"),
	COMM_SEND(RequestType.POST, IntegrationRestProtocol.COMMUNICATION_URL + "send"),
	COMM_CREATE(RequestType.POST, IntegrationRestProtocol.COMMUNICATION_URL + "create"),
	COMM_GET_TEMPLATE(RequestType.GET, IntegrationRestProtocol.COMMUNICATION_URL + "template", "templateName"),

	// INVENTORY MODULE
	INV_AIR_GET_CITY(RequestType.GET, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "getCityList"),
	INV_AIR_GET_FLIGHT_SLOTS(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "getFlightSlotDetails"),
	INV_AIR_BOOK_TICKET(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "bookFlight"),
	INV_AIR_CREATE_USER_REQUEST(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "createUserRequest"),
	INV_AIR_GET_USER_DETAILS(RequestType.GET, IntegrationRestProtocol.INVENTORY_AIRLINE_URL
			+ "getUserDetails", "requestId"),
	INV_AIR_GET_REQUESTED_FLIGHT_DETAILS(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL
			+ "getRequestedFlightDetails"),
	INV_AIR_CHECK_FLIGHT_PRICE(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "checkFlightPrice"),
	INV_AIR_SAVE_BOOKING(RequestType.POST, IntegrationRestProtocol.INVENTORY_AIRLINE_URL + "saveBooking"),

	// DELIVERY MODULE
	;

	private static final String URL_PATH_SEPARATOR = "/";

	private static final String CURR_URL = "http://localhost";
	private static final String ANURAG_URL = "http://ec2-52-40-162-133.us-west-2.compute.amazonaws.com";
	private static final String CARE_FB_URL = "http://localhost";

	private static final String AUTH_URL = CARE_FB_URL + ":9000" + URL_PATH_SEPARATOR;
	private static final String PAYMENT_URL = ANURAG_URL + ":9003" + URL_PATH_SEPARATOR;
	private static final String COMMUNICATION_URL = ANURAG_URL + ":9004" + URL_PATH_SEPARATOR + "communications"
			+ URL_PATH_SEPARATOR;
	private static final String INVENTORY_AIRLINE_URL = CARE_FB_URL + ":9001" + URL_PATH_SEPARATOR + "inventoryAirline"
			+ URL_PATH_SEPARATOR;
	private static final String DELIVERY_URL = CURR_URL + ":9001" + URL_PATH_SEPARATOR + "delivery"
			+ URL_PATH_SEPARATOR;

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
