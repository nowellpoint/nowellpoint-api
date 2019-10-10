package com.nowellpoint.api.model;

import javax.ws.rs.core.UriBuilder;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import com.nowellpoint.api.UserResource;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
	private @BsonId String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String countryCode;
	private String country;
	private String timeZone;
	
	public Attributes getAttributes() {
		return Attributes.builder()
				.href(UriBuilder.fromResource(UserResource.class).build(getId()).toString())
				.type(User.class.getSimpleName())
				.build();
	}
	
	@BsonCreator
	public User(
			@BsonId String id, 
			@BsonProperty("firstName") String firstName, 
			@BsonProperty("lastName") String lastName, 
			@BsonProperty("email") String email,
			@BsonProperty("phone") String phone,
			@BsonProperty("countryCode") String countryCode,
			@BsonProperty("country") String country,
			@BsonProperty("timeZone") String timeZone) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.countryCode = countryCode;
		this.country = country;
		this.timeZone = timeZone;
	}
}