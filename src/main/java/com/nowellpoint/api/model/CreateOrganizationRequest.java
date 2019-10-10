package com.nowellpoint.api.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class CreateOrganizationRequest {
	
	@NotEmpty
	private String clientId;
	
	@NotEmpty
	private String clientSecret;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
}