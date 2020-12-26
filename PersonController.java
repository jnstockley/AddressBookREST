package com.github.jnstockley.addressbookrest;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnstockley.addressbook.BackendHelper;
import com.github.jnstockley.addressbook.Person;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A RESTful web service with full CRUD support for the Person table
 * Supports GET, PUT, POST, DELETE
 * @author jackstockley
 * @version 3.2
 */

@RestController
@RequestMapping("/people")
@Api(value = "People", tags = "People")
@SuppressWarnings("rawtypes")
public class PersonController {

	private RESTController connection = new RESTController(); //REST Controller for getting mySQL connection
	private Connection conn = connection.getConnection(); //The mySQL connection
	private Person personHelper = new Person(); //Person Helper to interface with the backend
	private Locale locale = new Locale("en","US"); //Default locale for resource bundle
	private ResourceBundle bundle = ResourceBundle.getBundle("com.github.jnstockley.addressbookrest.bundle", locale); //Resource bundle with error messages

	/**
	 * Gets all the people stored in the database and returns them
	 * @return A list of people
	 */
	@GetMapping("/")
	@ApiOperation(value = "Gets all the people stored in the database and returns them", response = Person.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All the people"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity getAllPeople() {
		try {
			List<Person> people = personHelper.get(conn); //Gets all the people from the database
			if(people!=null) { //Makes sure backend got valid data
				return new ResponseEntity<List<Person>>(people, HttpStatus.OK); //Returns the people list
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("people_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Gets a singular person from the database based on the id passed and returns it
	 * @param id ID of the person on the database
	 * @return A person object
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Gets a singular person from the database based on the id passed and returns it", response = Person.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The given person"),
			@ApiResponse(code = 404, message = "Invalid ID"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity getPersonByID(@PathVariable int id) {
		try {
			Person person = personHelper.get(conn, id); //Gets a singular person from the database based on the id passed
			if(person!=null) { //Makes sure backed got valid data
				return new ResponseEntity<Person>(person, HttpStatus.OK); //Returns the person
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("person_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Updates a user specified person at the id passed
	 * @param id ID of the person to be updated
	 * @param person The new person to update the current person
	 * @return A person object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a user specified person at the id passed", response = Person.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The updated person"),
			@ApiResponse(code = 400, message = "Person already exists"),
			@ApiResponse(code = 404, message = "Person not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity updatePerson(@PathVariable int id, @RequestBody Person person) {
		try {
			Person updatedPerson = personHelper.update(conn, id, person); //Updates a person at the given id
			if(updatedPerson.equals(person)) { //Makes sure the user passed person and the returned person are the same
				return new ResponseEntity<Person>(updatedPerson, HttpStatus.OK); //Returns the updated person
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("person_not_found"));
			}

		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Inserts a new person into the database
	 * @param person New person to be added
	 * @return New person added
	 */
	@PostMapping("/")
	@ApiOperation(value = "Inserts a new person into the database", response = Person.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The new person"),
			@ApiResponse(code = 400, message = "Person already exists"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity insertPerson(@RequestBody Person person) {
		try {
			BackendHelper helper = new BackendHelper();
			boolean exists = helper.exisits(conn, person);
			if(!exists) {
				Person newPerson = personHelper.insert(conn, person); //Inserts a new person
				if(newPerson.equals(person)) { //Makes sure the user passed person and the return person are the same
					return new ResponseEntity<Person>(newPerson, HttpStatus.OK); //Returns the new person
				}else {
					throw new AddressException(HttpStatus.INTERNAL_SERVER_ERROR, bundle.getString("person_not_created"));
				}
			}else {
				throw new AddressException(HttpStatus.BAD_REQUEST, bundle.getString("person_exists"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Removes a user specified person from the database
	 * @param id ID of the person to be removed
	 * @return True if person removed otherwise false
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Removes a user specified person from the database", response = boolean.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Address was removed"),
			@ApiResponse(code = 404, message = "Address not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity deletePerson(@PathVariable int id) {
		try {
			BackendHelper helper = new BackendHelper();
			boolean exists = helper.exisits(conn, personHelper.get(conn, id));
			if(exists) {
				boolean removed = personHelper.delete(conn, id); //Creates a boolean and removes the person from the database
				if(removed) { //Makes sure the backend removed the person
					return new ResponseEntity(HttpStatus.OK); //Returns true
				}else {
					throw new AddressException(HttpStatus.INTERNAL_SERVER_ERROR, bundle.getString("person_not_deleted"));
				}
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("person_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}
}