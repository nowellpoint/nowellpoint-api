package com.nowellpoint.services.rest.model;

import javax.json.bind.annotation.JsonbProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;

@Getter
@RegisterForReflection
public class JsonWebKey {
	@JsonbProperty(value="alg") private String algorithm;
	@JsonbProperty(value="kid") private String keyId;
	@JsonbProperty(value="kty") private String keyType;
	@JsonbProperty(value="e") private String exponent;
	@JsonbProperty(value="n") private String modulus;
	@JsonbProperty(value="use") private String useage;
}