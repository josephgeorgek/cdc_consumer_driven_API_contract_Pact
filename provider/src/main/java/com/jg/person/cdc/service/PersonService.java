package com.jg.person.cdc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jg.person.cdc.entity.Person;


public interface PersonService {
	
	List<Person> findAll();
	Person findbyId(Long id);
	Person create(Person person);
	

}
