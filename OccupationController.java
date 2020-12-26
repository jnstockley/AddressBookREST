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
import com.github.jnstockley.addressbook.Occupation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A RESTful web service with full CRUD support for the Occupation table
 * Supports GET, PUT, POST, DELETE
 * @author jackstockley
 * @version 3.2
 */

@RestController
@RequestMapping("/occupation")
@Api(value = "Occupation", tags = "Occupation")
@SuppressWarnings("rawtypes")
public class OccupationController {

	private RESTController connection = new RESTController(); //REST Controller for getting mySQL connection
	private Connection conn = connection.getConnection(); //The mySQL connection
	private Occupation occupationHelper = new Occupation(); //Occupation Helper to interface with the backend
	private Locale locale = new Locale("en","US"); //Default locale for resource bundle
	private ResourceBundle bundle = ResourceBundle.getBundle("com.github.jnstockley.addressbookrest.bundle", locale); //Resource bundle with error messages

	/**
	 * Gets all the occupations stored on the database and returns them
	 * @return A list of occupations
	 */
	@GetMapping("/")
	@ApiOperation(value = "Gets all the occupations stored on the database and returns them", response = Occupation.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All the occupations"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity getAllOccupations() {
		try {
			List<Occupation> occupations = occupationHelper.get(conn); //Gets all the occupations from the database
			if(occupations!=null) { //Makes sure backend got valid data
				return new ResponseEntity<List<Occupation>>(occupations, HttpStatus.OK);//Returns the occupations list
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("occupations_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Gets a singular occupation from the database based on the id passed and returns it
	 * @param id ID of the occupation on the database
	 * @return An occupation object
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Gets a singular occupation from the database based on the id passed and returns it", response = Occupation.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The given occupation"),
			@ApiResponse(code = 404, message = "Invalid ID"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity getOccupationById(@PathVariable int id) {
		try {
			Occupation occupation = occupationHelper.get(conn, id); //Gets a singular occupation from the database based on the id passed
			if(occupation!=null) { //Makes sure backend got valid data
				return new ResponseEntity<Occupation>(occupation, HttpStatus.OK); //Returns the occupation
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("occupation_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Updates a user specified occupation at the id passed
	 * @param id ID of the occupation to be updated
	 * @param occupation The new occupation to update the current occupation
	 * @return An occupation object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a user specified occupation at the id passed", response = Occupation.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The updated occupation"),
			@ApiResponse(code = 400, message = "Occupatiom already exists"),
			@ApiResponse(code = 404, message = "Occupation not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity updateOccupation(@PathVariable int id, @RequestBody Occupation occupation) {
		try {
			Occupation updatedOccupation = occupationHelper.update(conn, id, occupation); //Updates an occupation at the given id
			if(updatedOccupation.equals(occupation)) { //Makes sure the user passed occupation and the returned occupation are the same
				return new ResponseEntity<Occupation>(updatedOccupation, HttpStatus.OK); //Returns the updated occupation
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("occupation_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Inserts a new occupation into the database
	 * @param occupation New occupation to be added
	 * @return New occupation added
	 */
	@PostMapping("/")
	@ApiOperation(value = "Inserts a new occupation into the database", response = Occupation.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The new occupation"),
			@ApiResponse(code = 400, message = "Occupation already exists"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity insertOccupation(@RequestBody Occupation occupation) {
		try {
			BackendHelper helper = new BackendHelper();
			boolean exists = helper.exisits(conn, occupation);
			if(!exists) {
				Occupation newOccupation = occupationHelper.insert(conn, occupation); //Inserts a new occupation
				if(newOccupation.equals(occupation)) { //Makes sure the user passed occupation and the returned occupation are the same
					return new ResponseEntity<Occupation>(newOccupation, HttpStatus.OK); //Returns the new occupation
				}else {
					throw new AddressException(HttpStatus.INTERNAL_SERVER_ERROR, bundle.getString("occupation_not_found"));
					}
			}else {
				throw new AddressException(HttpStatus.BAD_REQUEST, bundle.getString("occupation_exists"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Removes a user specified occupation from the database
	 * @param id  ID of the occupation to be deleted
	 * @return True if occupation removed otherwise false
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Removes a user specified occupation from the database", response = boolean.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Occupation was removed"),
			@ApiResponse(code = 404, message = "Occupation not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity deleteOccupation(@PathVariable int id) {
		try {
			BackendHelper helper = new BackendHelper();
			boolean exists = helper.exisits(conn, occupationHelper.get(conn, id));
			if(exists) {
				boolean removed = occupationHelper.delete(conn, id); //Creates a boolean and removes the occupation from the database
				if(removed) { //Makes sure the backend removed the occupation
					return new ResponseEntity(HttpStatus.OK); //Returns true
				}else {
					throw new AddressException(HttpStatus.INTERNAL_SERVER_ERROR, bundle.getString("occupation_not_deleted"));
				}
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("occupation_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}
}