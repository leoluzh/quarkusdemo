package com.lambdasys.quarkusdemo.exceptionsmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ErrorResponse response = ErrorResponse.builder().errorCode("400").errorMessage(exception.getMessage()).build();
		return Response.ok().entity(response).build();
	}

}
