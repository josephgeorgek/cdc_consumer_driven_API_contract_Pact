package com.jg.person.cdc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.jg.person.cdc.entity.Person;
import com.jg.person.cdc.repository.PersonRepository;
import com.jg.person.cdc.service.PersonService;


@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonRepository personRepository;
	
	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		
		Faker faker = new Faker();
        List<Person> books = new ArrayList<Person>();
 
        for(int i = 0; i < 10; i++) {
        	Person person = new Person();
        	person.setName(faker.artist().name());
        	person.setId(personRepository.save(person).getId());
          
        }
 
        
		return personRepository.findAll();
	}

	@Override
	public Person findbyId(Long id) {
		// TODO Auto-generated method stub
		
	//	return personRepository.findById(id).orElseThrow(() -> new NoPersonFoundException("Person with id:" + id +
		//	      " not found"));
		return personRepository.findById(id).get();

	}

	@Override
	public Person create(Person person) {
		// TODO Auto-generated method stub
		return findbyId(personRepository.save(person).getId());
	}
	
	

}
