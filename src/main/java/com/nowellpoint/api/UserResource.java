package com.nowellpoint.api;

import java.io.InputStream;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.nowellpoint.api.model.CreateResponse;
import com.nowellpoint.api.model.CreateUserRequest;
import com.nowellpoint.api.model.User;
import com.nowellpoint.api.service.UserService;
import com.nowellpoint.api.util.JsonbUtil;
import com.nowellpoint.client.sforce.OauthException;
import com.nowellpoint.http.Status;

@Path("/users")
public class UserResource {
	
	@Inject
	UserService userService;
	
	@Inject
	Validator validator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(InputStream requestBody) {
    	
    	CreateUserRequest request = JsonbUtil.fromJson(requestBody, CreateUserRequest.class);
    	
    	Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
    	if (violations.isEmpty()) {
    		
    		User user = null;
        	try {
        		user = userService.create(request);
        	} catch (OauthException e) {
        		throw new WebApplicationException(e.getError() + ": " + e.getErrorDescription(), Status.FORBIDDEN);
        	}
        	
            return Response.created(UriBuilder.fromResource(UserResource.class).build(user.getId()))
            		.entity(new CreateResponse(user))
            		.build();
    	} else {
    		
    		return Response.status(Status.BAD_REQUEST)
            		.entity(new CreateResponse(violations))
            		.build();
    	}
    }
}