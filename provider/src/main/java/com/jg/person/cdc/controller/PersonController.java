package com.jg.person.cdc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jg.person.cdc.entity.Person;
import com.jg.person.cdc.service.PersonService;



@RestController
@RequestMapping("/api/persons")
public class PersonController {

	@Autowired
	PersonService personService;
	
	@GetMapping
	  public List<Person> getAllPersons() {
	    return personService.findAll();
	  }
	
	@GetMapping("/{id}")
	  public Person getPerson(@PathVariable("id") Long id) {
		
		return	personService.findbyId(id);
				//.orElseThrow(() -> new NoPersonFoundException("Person with id:" + id +
			     // " not found"));
	  //  return personService.findbyId(id);
	  }
	
	@PostMapping()
	  public Person addPerson(@RequestBody() Person person) {
		
		return	personService.create(person);
				//.orElseThrow(() -> new NoPersonFoundException("Person with id:" + id +
			     // " not found"));
	  //  return personService.findbyId(id);
	  }
	
	
	
}
