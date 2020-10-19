package com.jg.person.cdc;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import com.jg.person.cdc.controller.exception.NotFoundException;
import com.jg.person.cdc.entity.Person;
import com.jg.person.cdc.service.PersonService;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Provider("person-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//pact_broker is the service name in docker-compose pact_broker
@PactBroker(host = "localhost", tags = "${pactbroker.tags:prod}")
public class GenericStateWithParameterContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @MockBean
    private PersonService personService;

    @State("default")
    public void toDefaultState(Map<String, Object> params) {
        final boolean personExists = (boolean) params.get("personExists");
        if (personExists) {
            when(personService.findbyId(any())).thenReturn(Person.builder()
                .id((long)1)
                .name("Joseph")
             
                .build());
        } else {
            when(personService.findbyId(any())).thenThrow(NotFoundException.class);
        }
    }


}

