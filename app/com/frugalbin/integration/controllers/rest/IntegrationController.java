package com.frugalbin.integration.controllers.rest;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS.Response;
import play.mvc.BodyParser;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.frugalbin.common.dto.request.authentication.UsersRequestDto;
import com.frugalbin.common.dto.request.communication.CommunicationRequestDto;
import com.frugalbin.common.dto.request.communication.SendCommunicationRequestDto;
import com.frugalbin.common.dto.request.communication.SendFrugalbinMessageRequest;
import com.frugalbin.common.dto.request.integration.SaveUserRequestBean;
import com.frugalbin.common.dto.request.integration.UserDetailsBean;
import com.frugalbin.common.dto.request.inventory.airline.FlightBookingRequest;
import com.frugalbin.common.dto.request.payment.PaymentRequestDto;
import com.frugalbin.common.dto.response.authentication.UsersResponseDto;
import com.frugalbin.common.dto.response.communication.SuccessStatusResponse;
import com.frugalbin.common.dto.response.integration.BeginTransactionRequest;
import com.frugalbin.common.dto.response.integration.FlightSearchResponseBean;
import com.frugalbin.common.dto.response.integration.SaveUserResponseBean;
import com.frugalbin.common.dto.response.inventory.airline.PriceCheckResponseBean;
import com.frugalbin.common.dto.response.inventory.airline.SaveBookingResponseBean;
import com.frugalbin.common.dto.response.payment.PaymentResponseDto;
import com.frugalbin.common.dto.response.payment.PaymentValidationResponse;
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

	public Result enablecors()
	{
		System.out.println("inside enable cors");
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
		response().setHeader("Access-Control-Allow-Methods", "HEAD,GET,PUT,DELETE,OPTIONS");
		response().setHeader("Access-Control-Max-Age", "10000");
		response().setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Referrer, User-Agent");
		return ok();
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result login(String sessionId) throws BusinessException
	{
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.AUTH_LOGIN, request().body().asJson(),
				sessionId);
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
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
		commReq.setTemplateKeyValues("");

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
		commReq.setTemplateKeyValues("");

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
		LOGGER.info("Get City List Request started");
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_CITY);
		LOGGER.info("Get City List Request ends");
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result getFlightSlotDetails() throws BusinessException
	{
		LOGGER.info("Get Flight Slot Details Request started");
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_FLIGHT_SLOTS, request()
				.body().asJson());
		LOGGER.info("Get Flight Slot Details Request ends");
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
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));

		LOGGER.info("Save Request started");
		
		SaveUserRequestBean saveUserReq = convertJsonNodeToObject(request().body().asJson(), SaveUserRequestBean.class);
		
		LOGGER.debug("Save User Request is: " + saveUserReq);

		UserDetailsBean userDetails = saveUserReq.getUserDetails();

		LOGGER.info("Login in Guest user");
		
		// Save user details and get userId(auth)
		UsersRequestDto usersRequestDto = new UsersRequestDto("GUEST", null, userDetails.getEmail(),
				userDetails.getPhoneno(), userDetails.getFirstName(), userDetails.getLastName());
		Promise<Response> response = RestClient.sendRequest(IntegrationRestProtocol.AUTH_LOGIN,
				Json.toJson(usersRequestDto));

		UsersResponseDto userResponse = convertJsonNodeToObject(response.get(Constants.REST_TIMEOUT).asJson(),
				UsersResponseDto.class);
		saveUserReq.getUserDetails().setUserId(userResponse.getUserId());
		
		LOGGER.info("Logged in as Guest user");

		LOGGER.info("Creating User Request");
		
		// Save request details(Inventory Airline)
		Promise<Response> saveUserJsonRes = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_CREATE_USER_REQUEST,
				saveUserReq);
		SaveUserResponseBean saveUserResponse = convertJsonNodeToObject(saveUserJsonRes.get(Constants.REST_TIMEOUT)
				.asJson(), SaveUserResponseBean.class);

		if (!saveUserResponse.getRequestStatus())
		{
			LOGGER.info("Couldn't create User request: " + saveUserResponse.getFailureReason());
			SuccessStatusResponse statusResponse = new SuccessStatusResponse();
			statusResponse.setIsSuccess(false);
			statusResponse.setFailureMsg(saveUserResponse.getFailureReason());
			return convertObjectToJsonResponse(statusResponse);
		}
		
		LOGGER.info("Sending link mail to cust care");
		response = sendCommunicationEmail(TemplateNames.SEND_USER_REQUEST_LINK, "%RequestId%="
					+ saveUserResponse.getRequestId(), "care@frugalbin.com", "care@frugalbin.com",
					"Subject=Send Link for Flight Search Request;VisibleName=Customer Care FrugalBin");

//		return ok(Util.getJsonResponse(response));
		LOGGER.info("Save Request is a success");
		return convertObjectToJsonResponse(saveUserResponse);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result getRequestedFlightDetails() throws BusinessException
	{
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));

		LOGGER.info("Get Requested Flight Details started");
		
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_REQUESTED_FLIGHT_DETAILS,
				request().body().asJson());
		JsonNode jsonNode = res.get(Constants.REST_TIMEOUT).asJson();

		if (jsonNode.has("errorCode"))
		{
			LOGGER.info("Got error from Inventory module: "+ jsonNode);
			return ok(jsonNode);
		}

		FlightSearchResponseBean flightSearchResponseBean = convertJsonNodeToObject(jsonNode,
				FlightSearchResponseBean.class);

		LOGGER.info("Getting user details");
		
		res = RestClient.sendRequest(IntegrationRestProtocol.AUTH_GET_USER, flightSearchResponseBean.getSearchRequest()
				.getUserDetails());
		UserDetailsBean userDetail = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(),
				UserDetailsBean.class);

		flightSearchResponseBean.getSearchRequest().getUserDetails().setFirstName(userDetail.getFirstName());
		flightSearchResponseBean.getSearchRequest().getUserDetails().setLastName(userDetail.getLastName());
		flightSearchResponseBean.getSearchRequest().getUserDetails().setEmail(userDetail.getEmail());
		flightSearchResponseBean.getSearchRequest().getUserDetails().setPhoneno(userDetail.getPhoneno());
		flightSearchResponseBean.getSearchRequest().getUserDetails().setUserId(null);

		LOGGER.info("Get Requested Flight Details successfully ended");
		
		return convertObjectToJsonResponse(flightSearchResponseBean);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result sendRequestedFlightLink(String requestId) throws BusinessException
	{
		LOGGER.info("send Requested Flight Link started");
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_GET_USER_DETAILS, requestId);
		Response response = res.get(Constants.REST_TIMEOUT);
		if (response.getStatus() != 200)
		{
			LOGGER.info("Invalid User Request: " + requestId);
			throw new BusinessException(1001, "Invalid User Request");
		}

		UserDetailsBean userDetailsBean = convertJsonNodeToObject(response.asJson(), UserDetailsBean.class);

		LOGGER.info("getting user details");
		
		res = RestClient.sendRequest(IntegrationRestProtocol.AUTH_GET_USER, userDetailsBean);
		UserDetailsBean userDetail = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(),
				UserDetailsBean.class);

		LOGGER.info("sending link communication to user");
		
		res = sendCommunicationEmail(TemplateNames.USER_REQUEST_LINK, "%RequestId%=" + requestId, "care@frugalbin.com",
				userDetail.getEmail(), "Subject=Flight Search Request Link;VisibleName=Customer Care FrugalBin");
		
		LOGGER.info("send Requested Flight Link successfully ended");
		
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result checkFlightPrice() throws BusinessException
	{
		LOGGER.info("check Flight Price started");
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));

		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_CHECK_FLIGHT_PRICE, request()
				.body().asJson());

		LOGGER.info("check Flight Price successfully ends");
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result beginFlightTransaction() throws BusinessException
	{
		LOGGER.info("begin Flight Transaction started");
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));

		BeginTransactionRequest request = convertJsonNodeToObject(request().body().asJson(),
				BeginTransactionRequest.class);

		LOGGER.info("checking price change");
		// Price Check
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_CHECK_FLIGHT_PRICE,
				request.getPriceCheckRequest());
		PriceCheckResponseBean priceCheckResponseBean = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT)
				.asJson(), PriceCheckResponseBean.class);

		if (!priceCheckResponseBean.getIsSuccess() || priceCheckResponseBean.getIsFareChanged())
		{
			LOGGER.info("Price has been changed");
			return convertObjectToJsonResponse(priceCheckResponseBean);
		}

		LOGGER.info("requesting to save the booking");
		// SaveBooking
		/*Promise<Response> */res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_SAVE_BOOKING, request.getPriceCheckRequest());

		SaveBookingResponseBean saveBookingResponseBean = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT)
				.asJson(), SaveBookingResponseBean.class);

		if (!saveBookingResponseBean.getSuccess())
		{
			LOGGER.info("couldn't save booking");
			throw new BusinessException(1001, "Couldn't save the booking, Frugal might have been missed :(");
		}

		LOGGER.info("Getting payment url");

		PaymentRequestDto paymentUrRequest = request.getPaymentUrlRequest();
		paymentUrRequest.udf1 = saveBookingResponseBean.getBookingId();
		res = RestClient.sendRequest(IntegrationRestProtocol.PAYMENT_GET_PGURL, paymentUrRequest);

		Response response = res.get(Constants.REST_TIMEOUT);
		
		LOGGER.info("begin Flight Transaction susccesfully ended");
		return ok(response.asByteArray()).as("text/html");
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result completeFlightTransaction() throws BusinessException
	{
		response().setHeader("Access-Control-Allow-Origin", request().getHeader("Origin"));

		LOGGER.info("Complete Flight Transaction begins");

		PaymentResponseDto paymentResponseDto = convertJsonNodeToObject(request().body().asJson(),
				PaymentResponseDto.class);
		
		LOGGER.info("Validating payment details");
		
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.PAYMENT_VALIDATE_PG_RESPONSE,
				paymentResponseDto);

		PaymentValidationResponse paymentValidationResponse = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT)
				.asJson(), PaymentValidationResponse.class);

		// check condition if valid
		if (!paymentValidationResponse.getIsValid())
		{
			LOGGER.info("Invalid payment details");
			throw new BusinessException(1001, "Payment Response is corrupted: "
					+ paymentValidationResponse.getMessage());
		}

		FlightBookingRequest bookingRequest = new FlightBookingRequest();
		bookingRequest.setBookingId(paymentResponseDto.udf1);
		
		LOGGER.info("Booking ticket");
		
		res = RestClient.sendRequest(IntegrationRestProtocol.INV_AIR_BOOK_TICKET, request().body().asJson());

		LOGGER.info("Complete Flight Transaction successfully ended");
		return ok(Util.getJsonResponse(res));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result sendFrugalbinMessage() throws BusinessException
	{
		LOGGER.info("Sending messge");
		SendFrugalbinMessageRequest msgRequest = convertJsonNodeToObject(request().body().asJson(),
				SendFrugalbinMessageRequest.class);

		Promise<Response> res = sendCommunicationEmail(TemplateNames.SEND_FRUGALBIN_MESSAGE, "%FBMessage%="
				+ msgRequest.getMessage(), msgRequest.getFromEmailAddr(), "care@frugalbin.com",
				"Subject=" + msgRequest.getSubject() + ";VisibleName=" + msgRequest.getFromName());
		
		LOGGER.info("Sent message");
		return ok(Util.getJsonResponse(res));
	}

	private Promise<Response> sendCommunicationEmail(TemplateNames template, String templateKeyValues, String from,
			String to, String commInfoKeyValues) throws BusinessException
	{
		// Get Template Id
		Promise<Response> res = RestClient.sendRequest(IntegrationRestProtocol.COMM_GET_TEMPLATE, template.name());

		Map<String, Integer> resMap = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), Map.class);
		Long templateId = resMap.get("templateId").longValue();

		// Create Communication
		CommunicationRequestDto commReq = new CommunicationRequestDto();
		commReq.setTemplateId(templateId);
		commReq.setTemplateKeyValues(templateKeyValues);
		commReq.setFrom(from);
		commReq.setTo(to);
		commReq.setCommInfoKeyValues(commInfoKeyValues);

		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_CREATE, Json.toJson(commReq));
		Map<String, Integer> commIdMap = convertJsonNodeToObject(res.get(Constants.REST_TIMEOUT).asJson(), Map.class);

		Long commId = commIdMap.get("communicationId").longValue();

		// Send Communication
		SendCommunicationRequestDto sendReq = new SendCommunicationRequestDto();
		sendReq.setCommunicationIds(new Long[] { commId });

		res = RestClient.sendRequest(IntegrationRestProtocol.COMM_SEND, Json.toJson(sendReq));

		return res;
	}
}
