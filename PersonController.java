package com.github.jnstockley.addressbookrest;

import java.sql.Connection;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.jnstockley.addressbook.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTful web service with full CRUD support for the Person table
 * Supports GET, PUT, POST, DELETE
 * @author jackstockley
 * @version 3.0
 */
@RestController
@RequestMapping("/person")
@Api(value = "Person", tags = "Person")
public class PersonController {

	private RESTController connection = new RESTController(); //REST Controller for getting mySQL connection
	private Connection conn = connection.getConnection(); //The mySQL connection
	private Person personHelper = new Person(); //Person Helper to interface with the backend
	
	/**
	 * Gets all the people stored in the database and returns them
	 * @return A list of people
	 */
	@GetMapping("/")
	@ApiOperation(value = "Gets all the people stored in the database and returns them",
					response = Person.class, responseContainer = "List")
	public List<Person> getAllPeople() {
		try {
			List<Person> people = personHelper.getAllPeople(conn); //Gets all the people from the database
			if(people!=null) { //Makes sure backend got valid data
				return people; //Returns the people list
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets a singular person from the database based on the id passed and returns it
	 * @param id ID of the person on the database
	 * @return A person object
	 */
	@GetMapping("/{id")
	@ApiOperation(value = "Gets a singular person from the database based on the id passed and returns it",
					response = Person.class)
	public Person getPersonByID(@PathVariable int id) {
		try {
			Person person = personHelper.getSingularPerson(conn, id); //Gets a singular person from the database based on the id passed
			if(person!=null) { //Makes sure backed got valid data
				return person; //Returns the person
			}else {
				return null;	
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Updates a user specified person at the id passed
	 * @param id ID of the person to be updated
	 * @param person The new person to update the current person
	 * @return A person object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a user specified person at the id passed",
					response = Person.class)
	public Person updatePerson(@PathVariable int id, @RequestBody Person person) {
		try {
			Person updatedPerson = personHelper.updatePerson(conn, id, person); //Updates a person at the given id
			if(updatedPerson.equals(person)) { //Makes sure the user passed person and the returned person are the same
				return updatedPerson; //Returns the updated person
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Inserts a new person into the database
	 * @param person New person to be added
	 * @return New person added
	 */
	@PostMapping("/")
	@ApiOperation(value = "Inserts a new person into the database",
					response = Person.class)
	public Person insertPerson(@RequestBody Person person) {
		try {
			Person newPerson = personHelper.insertPerson(conn, person); //Inserts a new person
			if(newPerson.equals(person)) { //Makes sure the user passed person and the return person are the same
				return newPerson; //Returns the new person
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Removes a user specified person from the database
	 * @param id ID of the person to be removed
	 * @return True if person removed otherwise false
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Removes a user specified person from the database",
					response = boolean.class)
	public boolean removePerson(@PathVariable int id) {
		try {
			boolean removed = personHelper.removePerson(conn, id); //Creates a boolean and removes the person from the database
			if(removed) { //Makes sure the backend removed the person
				return true; //Returns true
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

