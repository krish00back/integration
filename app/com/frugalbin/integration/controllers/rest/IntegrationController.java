package com.frugalbin.integration.controllers.rest;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS.Response;
import play.mvc.BodyParser;
import play.mvc.Result;
import antlr.debug.GuessingEvent;

import com.frugalbin.common.dto.request.authentication.UsersRequestDto;
import com.frugalbin.common.dto.request.communication.CommunicationRequestDto;
import com.frugalbin.common.dto.request.communication.SendCommunicationRequestDto;
import com.frugalbin.common.dto.request.integration.SaveUserRequestBean;
import com.frugalbin.common.dto.request.integration.UserDetailsBean;
import com.frugalbin.common.dto.response.authentication.UsersResponseDto;
import com.frugalbin.common.dto.response.integration.FlightSearchResponseBean;
import com.frugalbin.common.enums.TemplateNames;
import com.frugalbin.common.exceptions.BusinessException;
import com.frugalbin.common.rest.client.RestClient;
import com.frugalbin.common.utils.Constants;
import com.frugalbin.integration.controllers.base.BaseController;
import com.frugalbin.integration.controllers.base.IntegrationRestProtocol;
import com.frugalbin.integration.utils.Util;

@Named
@Singleton
public class IntegrationController extends BaseController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationController.class);

	@BodyParser.Of(BodyParser.Json.class)
	public Result login(String sessionId) throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.AUTH_LOGIN, request().body().asJson(),
				sessionId);
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result register() throws BusinessException
	{
		// 1. Register in Authentication module
		Promise<Response> res = RestClient
				.sendRequest(IntegrationRestProtocol.AUTH_REGISTER, request().body().asJson());

		UsersResponseDto usersRes = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(),
				UsersResponseDto.class);

		// TODO: get users info

		// 2. Send Confirmation Email
		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_GET_TEMPLATE,
				Json.toJson(TemplateNames.REGISTRATION_CONFIRMATION));

		Long regConfirmationTemplateId = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), Long.class);

		CommunicationRequestDto commReq = new CommunicationRequestDto();
		commReq.setTemplateId(regConfirmationTemplateId);
		commReq.setKeyValues("");

		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_CREATE, Json.toJson(commReq));
		Long commId = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), Long.class);

		SendCommunicationRequestDto sendReq = new SendCommunicationRequestDto();
		sendReq.setCommunicationConnectionIds(new Long[] { commId });

		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_SEND, Json.toJson(sendReq));

		// 3. Send Welcome message/email
		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_GET_TEMPLATE,
				Json.toJson(TemplateNames.FRUGALBIN_WELCOME));

		Long regWelcomeTemplateId = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), Long.class);

		commReq = new CommunicationRequestDto();
		commReq.setTemplateId(regWelcomeTemplateId);
		commReq.setKeyValues("");

		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_CREATE, Json.toJson(commReq));
		commId = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), Long.class);

		sendReq = new SendCommunicationRequestDto();
		sendReq.setCommunicationConnectionIds(new Long[] { commId });

		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_SEND, Json.toJson(sendReq));
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result myAccount() throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.AUTH_ACC_DETAILS, request().body()
				.asJson());
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result myOrders()
	{
		return convertObjectToJsonResponse("");
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result getCityList() throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_CITY);
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result getFlightSlotDetails() throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_FLIGHT_SLOTS, request()
				.body().asJson());
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result bookFlightTicket()
	{
		return convertObjectToJsonResponse("");
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result saveRequest() throws BusinessException
	{
		SaveUserRequestBean saveUserReq = convertJsonNodeToObject(request().body().asJson(), SaveUserRequestBean.class);
		UserDetailsBean userDetails = saveUserReq.getUserDetails();

		// Save user details and get userId(auth)
		UsersRequestDto usersRequestDto = new UsersRequestDto("GUEST", userDetails.getName(), null,
				userDetails.getEmail(), userDetails.getPhoneno());
		Promise<Response> response = RestClient.sendRequest(IntegrationRestProtocol.AUTH_LOGIN,
				Json.toJson(usersRequestDto));

		UsersResponseDto userResponse = convertJsonNodeToObject(response.get(Constants.REST_TIMEOUT).asJson(),
				UsersResponseDto.class);
		saveUserReq.getUserDetails().setUserId(userResponse.getUserId());

		// Save request details(Inventory Airline)
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_CREATE_USER_REQUEST, saveUserReq);
		return ok(Util.getJsonResponse(res));
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result getRequestedFlightDetails() throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_REQUESTED_FLIGHT_DETAILS, request().body().asJson());
		FlightSearchResponseBean flightSearchResponseBean = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), FlightSearchResponseBean.class);
		
		res = RestClient.sendRequest(IntegrationRestProtocol.AUTH_GET_USER, flightSearchResponseBean.getSearchRequest().getUserDetails());
		UserDetailsBean userDetail = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), UserDetailsBean.class);
		
		flightSearchResponseBean.getSearchRequest().getUserDetails().setEmail(userDetail.getEmail());
		flightSearchResponseBean.getSearchRequest().getUserDetails().setPhoneno(userDetail.getPhoneno());
		flightSearchResponseBean.getSearchRequest().getUserDetails().setUserId(null);
		
		return convertObjectToJsonResponse(flightSearchResponseBean);
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result checkFlightPrice() throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_CHECK_FLIGHT_PRICE, request().body().asJson());
		return ok(Util.getJsonResponse(res));
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public Result beginFlightTransaction() throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_SAVE_BOOKING, request().body().asJson());
		return ok(Util.getJsonResponse(res));
	}
}
