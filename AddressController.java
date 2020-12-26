package com.github.jnstockley.addressbookrest;

import java.sql.Connection;
import java.sql.SQLException;
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

import com.github.jnstockley.addressbook.Address;
import com.github.jnstockley.addressbook.BackendHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A RESTfull web service with full CRUD support for the Address table
 * Supports GET, PUT, POST, DELETE
 * @author jackstockley
 * @version 3.2
 */

@RestController
@RequestMapping("/address")
@Api(value = "Address", tags = "Address")
@SuppressWarnings("rawtypes")
public class AddressController {

	private RESTController connection = new RESTController(); //REST Controller for getting mySQL connection
	private Connection conn = connection.getConnection(); //The mySQL connection
	private Address addressHelper = new Address(); //Address Helper to interface with the backend
	private Locale locale = new Locale("en","US"); //Default local for resource bundle
	private ResourceBundle bundle = ResourceBundle.getBundle("com.github.jnstockley.addressbookrest.bundle", locale); //Resource bundle with error messages
	

	/**
	 * Gets all the addresses stored on the database and returns them
	 * @return A list of Addresses
	 * @throws SQLException WIP
	 */
	@GetMapping("/")
	@ApiOperation(value = "Gets all the addresses stored on the database and returns them", response = Address.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All the addresses"),
			@ApiResponse(code = 404, message = "Addresses not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity getAllAddresses() throws SQLException {
		try {
			List<Address> addresses = addressHelper.get(conn); //Gets all the addresses from the database
			if(addresses!=null) { //Makes sure the backend got valid data
				return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK); //Returns the addresses list
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("addresses_not_found")); 
			}
		}catch(AddressException e) {
			System.out.println("exception");
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Gets a singular address from the database based on the id passed and returns it
	 * @param id ID of the address on the database
	 * @return An address object
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Gets a singular address from the database based on the id passed and returns it", response = Address.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The given address"),
			@ApiResponse(code = 404, message = "Invalid ID"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity getAddressByID(@PathVariable int id) {
		try {
			Address address = addressHelper.get(conn, id); //Gets a singular address from the database based on the id passed
			if(address!=null) { //Makes sure backend got valid data
				return new ResponseEntity<Address>(address, HttpStatus.OK); //Returns the address
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("address_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Updates a user specified address at the id passed
	 * @param id ID of the address to be updated
	 * @param address The new address to update the current address
	 * @return An address object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a user specified address at the id passed", response = Address.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The updated address"),
			@ApiResponse(code = 400, message = "Address already exists"),
			@ApiResponse(code = 404, message = "Address not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity updateAddress(@PathVariable int id, @RequestBody Address address) {
		try {
			Address updatedAddress = addressHelper.update(conn, id, address); //Updates an address at the given id
			if(updatedAddress.equals(address)) { //Makes sure the user passed address and the returned address are the same
				return new ResponseEntity<Address>(updatedAddress, HttpStatus.OK); //Returns the updated address
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND,bundle.getString("address_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Inserts a new address into the database
	 * @param address New address to be added
	 * @return New address added
	 */
	@PostMapping("/")
	@ApiOperation(value = "Inserts a new address into the database", response = Address.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The new address"),
			@ApiResponse(code = 400, message = "Address already exists"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity insertAddress(@RequestBody Address address) {
		try {
			BackendHelper helper = new BackendHelper();
			boolean exists = helper.exisits(conn, address);
			if(!exists) {
				Address newAddress = addressHelper.insert(conn, address); //Inserts a new address
				if(newAddress.equals(address)) { //Makes sure the user passed address and the returned address are the same
					return new ResponseEntity<Address>(newAddress, HttpStatus.OK); //Returns the new address
				}else {
					throw new AddressException(HttpStatus.INTERNAL_SERVER_ERROR, bundle.getString("address_not_created"));
				}
			}else {
				throw new AddressException(HttpStatus.BAD_REQUEST, bundle.getString("address_exists"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}

	/**
	 * Removes a user specified address from the database
	 * @param id ID of the address to be removed
	 * @return True if address removed otherwise false
	 */
	@DeleteMapping("/")
	@ApiOperation(value = "Removes a user specified address from the database")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Address was removed"),
			@ApiResponse(code = 404, message = "Address not found"),
			@ApiResponse(code = 500, message = "Backend error")})
	public ResponseEntity deleteAddress(@PathVariable int id) {
		try {
			BackendHelper helper = new BackendHelper();
			boolean exists = helper.exisits(conn, addressHelper.get(conn, id));
			if(exists) {
				boolean removed = addressHelper.delete(conn, id); //Creates a boolean and removes the address from the database
				if(removed) { //Makes sure the backend removed the address
					return new ResponseEntity(HttpStatus.OK); //Returns true
				}else {
					throw new AddressException(HttpStatus.INTERNAL_SERVER_ERROR, bundle.getString("address_not_deleted"));
				}
			}else {
				throw new AddressException(HttpStatus.NOT_FOUND, bundle.getString("address_not_found"));
			}
		}catch(AddressException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), e.getHttp());
		}
	}
}