package com.nowellpoint.services.rest;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nowellpoint.api.service.AccountService;
import com.nowellpoint.api.service.ConnectionService;
import com.nowellpoint.services.rest.model.Connection;
import com.nowellpoint.services.rest.model.sforce.Account;

@Path("{connectionId}/accounts")
@RequestScoped
public class AccountResource {
	
	@Inject
	AccountService accountService;
	
	@Inject
	ConnectionService connectionService;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("connectionId") String connectionId, @PathParam("id") String id) {
		Optional<Connection> connection = connectionService.findById("00DR0000001vySjMAI", connectionId);
		Optional<Account> account = accountService.findById(connection.get(), id);
		if (account.isPresent()) {
			return Response.ok(account).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}