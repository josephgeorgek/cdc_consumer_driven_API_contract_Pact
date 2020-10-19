package com.jg.person.cdc;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import com.jg.person.cdc.controller.exception.NotFoundException;
import com.jg.person.cdc.entity.Person;
import com.jg.person.cdc.service.PersonService;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Provider("person-service")
@PactFolder("pacts")
//@PactBroker(host = "localhost", tags = "${pactbroker.tags:prod}")

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
public class MockedPersonServiceContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @MockBean
    private PersonService personService;

    @State("Person 1 exists")
    public void person1Exists() {
        when(personService.findbyId(any())).thenReturn(Person.builder()
            .id((long)1)
            .name("Joseph")
            .build());
    }

    @State("Person 2 does not exist")
    public void person2DoesNotExist() {
        when(personService.findbyId(any())).thenThrow(NotFoundException.class);
    }

}

