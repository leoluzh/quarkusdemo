package com.lambdasys.quarkusdemo.exceptionsmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lambdasys.quarkusdemo.exceptions.MunicipioNotFoundException;

@Provider
public class MunicipioNotFoundExceptionMapper implements ExceptionMapper<MunicipioNotFoundException> {

	@Override
	public Response toResponse(MunicipioNotFoundException exception) {
		ErrorResponse response = ErrorResponse.builder().errorCode("404").errorMessage(exception.getMessage()).build();
		return Response.ok().entity(response).build();
	}

}
