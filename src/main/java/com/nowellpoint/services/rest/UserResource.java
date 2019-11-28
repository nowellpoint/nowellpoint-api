package com.nowellpoint.services.rest;

import java.net.URI;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import com.nowellpoint.api.service.UserService;
import com.nowellpoint.client.sforce.OauthException;
import com.nowellpoint.http.Status;
import com.nowellpoint.services.rest.model.CreateResponse;
import com.nowellpoint.services.rest.model.CreateUserRequest;
import com.nowellpoint.services.rest.model.User;

@Path("/users")
public class UserResource {
	
	@Inject
	UserService userService;
	
	@Inject
	Validator validator;
	
	@Inject
	@Claim(standard = Claims.groups)
	//@Claim("cognito:groups")
	String groups;
	
	@GET
	@Path("/{id}")
	@RolesAllowed("Administrator")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") String id) {
		User user = userService.findById(id);
		if (user != null) {
			return Response.ok(user).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserRequest request) {
    	
    	Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
    	
    	if (violations.isEmpty()) {
    		
    		User user = null;
        	try {
        		user = userService.create(request);
        	} catch (OauthException e) {
        		throw new WebApplicationException(e.getError() + ": " + e.getErrorDescription(), Status.FORBIDDEN);
        	}
        	
            return Response.created(URI.create(user.getAttributes().getHref()))
            		.entity(new CreateResponse(user))
            		.build();
    	} else {
    		
    		return Response.status(Status.BAD_REQUEST)
            		.entity(new CreateResponse(violations))
            		.build();
    	}
    }
}