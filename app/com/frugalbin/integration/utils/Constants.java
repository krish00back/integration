package com.frugalbin.integration.utils;

public interface Constants
{
	// Schema
	static final String INVENTORY_AIRLINE_SCHEMA = "INVENTORY_AIRLINE";

	// Table Names
	static final String AIRPORT_DETAILS_TABLE = "AIRPORT_DETAILS";
	static final String CITIES_TABLE = "CITIES";
	static final String FLIGHT_DETAILS_TABLE = "FLIGHT_DETAILS";
	static final String FLIGHT_SEAT_DETAILS_TABLE = "FLIGHT_SEAT_DETAILS";
	static final String BOOKING_DETAILS_TABLE = "BOOKING_DETAILS";
	static final String PASSENGER_DETAILS_TABLE = "PASSENGER_DETAILS";
	static final String BOOKING_PASSENGER_MAPPER_TABLE = "BOOKING_PASSENGER_MAPPER";
	static final String JA_INT_AIRLINE_CONNECTION_DETAILS_TABLE = "JA_INT_AIRLINE_CONNECTION_DETAILS";
	static final String QPX_AIRLINE_CONNECTION_DETAILS_TABLE = "QPX_AIRLINE_CONNECTION_DETAILS";

	// Table Columns
	// Columns: Common
	static final String ID_COLUMN = "ID";
	static final String NAME_COLUMN = "NAME";

	// Columns: AIRPORT_DETAILS
	static final String AIRPORT_CITY_ID_COLUMN = "CITY_ID";
	static final String AIRPORT_ADDRESS_ID_COLUMN = "ADDRESS_ID";

	// Columns: CITIES
	static final String STATE_COLUMN = "STATE";

	// Columns: FLIGHT_DETAILS
	static final String FD_AIRLINE_ID_COLUMN = "AIRLINE_ID";
	static final String FD_FLIGHT_NUMBER_COLUMN = "FLIGHT_NUMBER";
	static final String FD_FROM_AIRPORT_COLUMN = "FROM_AIRPORT";
	static final String FD_TO_AIRPORT_COLUMN = "TO_AIRPORT";
	static final String FD_TOTAL_SEATS_COLUMN = "TOTAL_SEATS";

	// Columns: FLIGHT_SEAT_DETAILS
	static final String FSD_FLIGHT_ID_COLUMN = "FLIGHT_ID";
	static final String FSD_DEPARTURE_TIME_COLUMN = "DEPARTURE_TIME";
	static final String FSD_ARRIVAL_TIME_COLUMN = "ARRIVAL_TIME";
	static final String FSD_AVAILABLE_SEATS_COLUMN = "AVAILABLE_SEATS";
	static final String FSD_ALLOTTED_SEATS_COLUMN = "ALLOTED_SEATS";

	// Columns: BOOKING_DETAILS
	static final String BD_AIRLINE_TRANSACTION_ID_COLUMN = "AIRLINE_TRANSACTION_ID";
	static final String BD_FLIGHT_SEAT_ID_COLUMN = "FLIGHT_SEAT_ID";

	// Columns: PASSENGER_DETAILS
	static final String PD_FIRST_NAME_COLUMN = "FIRST_NAME";
	static final String PD_LAST_NAME_COLUMN = "LAST_NAME";
	static final String PD_AGE_COLUMN = "AGE";
	static final String PD_GENDER_COLUMN = "GENDER";

	// Columns: PASSENDER_BOOKING_MAPPER
	static final String PBM_BOOKING_ID_COLUMN = "BOOKING_ID";
	static final String PBM_PASSENGER_ID_COLUMN = "PASSENGER_ID";

	// Columns: AIRLINE_CONNECTION_DETAILS
	static final String ACD_CONNECTION_URL_COLUMN = "CONNECTION_URL";
	static final String ACD_USERNAME_COLUMN = "USERNAME";
	static final String ACD_PASSWORD_COLUMN = "PASSWORD";
	static final String CONNECTION_TYPE_COLUMN = "CONNECTION_TYPE";
	static final String CONNECTION_SERVICE_TYPE_COLUMN = "CONNECTION_SERVICE_TYPE";
	static final String SEARCH_JSON_REQUEST_COLUMN = "SEARCH_JSON_REQUEST";
	
	// ERROR Codes
	static final int TEMPLATE_NOT_FOUND_ERROR_CODE = 1001;

	// ERROR Messages
	static final String TEMPLATE_NOT_FOUND_ERROR_MESSAGE = "Template with Id {} not found";

	static final String TIMESTAMP_TYPE = "timestamp";

	static final long UPDATER_DELAY_PERIOD = 1 * 10 * 1000;

	static final long UPDATER_PERIOD = 5 * 60 * 1000;
}
