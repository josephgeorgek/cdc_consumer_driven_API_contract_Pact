package com.jg.cdc.consumer;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import com.jg.cdc.consumer.data.PersonDTO;
import com.jg.cdc.consumer.service.PersonServiceClient;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = "person-service.base-url:http://localhost:8080", classes = PersonServiceClient.class)
@Disabled
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "person-service", port = "8080")
public class PersonServiceContractTest {

	private static final String NAME = "person name for CDC";

	@Autowired
	private PersonServiceClient personServiceClient;
	
	@PactTestFor(pactMethod = "pactPersonExists")
	@Test
	public void userExists() {
		final PersonDTO person = personServiceClient.getPerson("1");

		assertThat(person.getName()).isEqualTo(NAME);

	}

	@PactTestFor(pactMethod = "pactPersonDoesNotExist")
	@Test
	public void personDoesNotExist() {
		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
				() -> personServiceClient.getPerson("2"));
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
	}
	
	

	@Pact(consumer = "consumer-app")
	public RequestResponsePact pactPersonExists(PactDslWithProvider builder) {

		DslPart body = LambdaDsl.newJsonBody((o) -> o.stringType("name", NAME).numberType("id", (long) 1)).build();
		return builder.given("Person 1 exists").uponReceiving("A request to /api/persons/1").path("/api/persons/1")
				.method("GET").willRespondWith().status(200).body(body).toPact();

	}

	@Pact(consumer = "consumer-app")
	public RequestResponsePact pactUserDoesNotExist(PactDslWithProvider builder) {

		return builder.given("Person 2 does not exist").uponReceiving("A request to /api/persons/2")
				.path("/api/persons/2").method("GET").willRespondWith().status(404).toPact();
	}

	
}