package com.github.jnstockley.addressbookrest;

import java.sql.Connection;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.jnstockley.addressbook.Occupation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * A RESTful web service with full CRUD support for the Occupation table
 * Supports GET, PUT, POST, DELETE
 * @author jackstockley
 * @version 3.0
 */

@RestController
@RequestMapping("/occupation")
@Api(value = "Occupation", tags = "Occupation")
public class OccupationController {

	private RESTController connection = new RESTController(); //REST Controller for getting mySQL connection
	private Connection conn = connection.getConnection(); //The mySQL connection
	private Occupation occupationHelper = new Occupation(); //Occupation Helper to interface with the backend

	/**
	 * Gets all the occupations stored on the database and returns them
	 * @return A list of occupations
	 */
	@GetMapping("/")
	@ApiOperation(value = "Gets all the occupations stored on the database and returns them",
	response = Occupation.class, responseContainer = "List")
	public List<Occupation> getAllOccupations() {
		try {
			List<Occupation> occupations = occupationHelper.getAllOccupations(conn); //Gets all the occupations from the database
			if(occupations!=null) { //Makes sure backend got valid data
				return occupations; //Returns the occupations list
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets a singular occupation from the database based on the id passed and returns it
	 * @param id ID of the occupation on the database
	 * @return An occupation object
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Gets a singular occupation from the database based on the id passed and returns it",
	response = Occupation.class)
	public Occupation getOccupationById(@PathVariable int id) {
		try {
			Occupation occupation = occupationHelper.getSingularOccupation(conn, id); //Gets a singular occupation from the database based on the id passed
			if(occupation!=null) { //Makes sure backend got valid data
				return occupation; //Returns the occupation
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates a user specified occupation at the id passed
	 * @param id ID of the occupation to be updated
	 * @param occupation The new occupation to update the current occupation
	 * @return An occupation object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a user specified occupation at the id passed",
	response = Occupation.class)
	public Occupation updateOccupation(@PathVariable int id, @RequestBody Occupation occupation) {
		try {
			Occupation updatedOccupation = occupationHelper.updateOccupation(conn, id, occupation); //Updates an occupation at the given id
			if(updatedOccupation.equals(occupation)) { //Makes sure the user passed occupation and the returned occupation are the same
				return updatedOccupation; //Returns the updated occupation
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Inserts a new occupation into the database
	 * @param occupation New occupation to be added
	 * @return New occupation added
	 */
	@PostMapping("/")
	@ApiOperation(value = "Inserts a new occupation into the database",
	response = Occupation.class)
	public Occupation insertOccupation(@RequestBody Occupation occupation) {
		try {
			Occupation newOccupation = occupationHelper.insertOccupation(conn, occupation); //Inserts a new occupation
			if(newOccupation.equals(occupation)) { //Makes sure the user passed occupation and the returned occupation are the same
				return newOccupation; //Returns the new occupation
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Removes a user specified occupation from the database
	 * @param id  ID of the occupation to be deleted
	 * @return True if occupation removed otherwise false
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Removes a user specified occupation from the database",
	response = boolean.class)
	public boolean removeOccupation(@PathVariable int id) {
		try {
			boolean removed = occupationHelper.removeOccupation(conn, id); //Creates a boolean and removes the occupation from the database
			if(removed) { //Makes sure the backend removed the occupation
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

