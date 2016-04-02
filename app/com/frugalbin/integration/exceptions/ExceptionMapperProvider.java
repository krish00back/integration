package com.frugalbin.integration.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frugalbin.common.dto.response.communication.ErrorResponse;
import com.frugalbin.common.exceptions.BusinessException;


@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperProvider.class);

    /**
     * Maps an exception to a corresponding {@link javax.ws.rs.core.Response} object.
     */
    @Override
    public Response toResponse(Throwable exception) throws WebApplicationException {

        String logMsg;

        Status resStatus;
		ErrorResponse resErrMsg;
		if (exception instanceof BusinessException) {

            BusinessException businessException = (BusinessException) exception;
            logMsg = businessException.toString();

            resStatus = Status.INTERNAL_SERVER_ERROR;

            LOGGER.error(logMsg);
            resErrMsg = new ErrorResponse(businessException.getErrorCode(), businessException.getMessage());
        }
        else {
            logMsg = "Unhandled Exception occured { }";

            resStatus = Status.INTERNAL_SERVER_ERROR;

            LOGGER.error(logMsg, exception);
            resErrMsg = new ErrorResponse(1001, exception.getMessage());
        }

        return Response.status(resStatus).entity(resErrMsg).build();
    }
}
