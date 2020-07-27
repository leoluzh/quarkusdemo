package com.lambdasys.quarkusdemo.exceptionsmappers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@SuppressWarnings("serial")
public class ErrorResponse implements Serializable {

	private String errorMessage;
	private String errorCode;
	private String documentationLink;
	
}
