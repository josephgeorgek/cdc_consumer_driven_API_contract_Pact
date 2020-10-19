package com.jg.cdc.consumer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.jg.cdc.consumer.data.PersonDTO;

@Component
public class PersonServiceClient {

    private final RestTemplate restTemplate;

    public PersonServiceClient(@Value("${person-service.base-url}") String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public PersonDTO getPerson(String id) {
        final PersonDTO person = restTemplate.getForObject("/api/persons/" + id, PersonDTO.class);
        Assert.hasText(person.getName(), "Name is blank.");
        return person;
    }

}
