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
import com.github.jnstockley.addressbook.Address;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * A RESTfull web service with full CRUD support for the Address table
 * Supports GET, PUT, POST, DELETE
 * @author jackstockley
 * @version 3.0
 */

@RestController
@RequestMapping("/address")
@Api(value = "Address", tags = "Address")
public class AddressController {


	private RESTController connection = new RESTController(); //REST Controller for getting mySQL connection
	private Connection conn = connection.getConnection(); //The mySQL connection
	private Address addressHelper = new Address(); //Address Helper to interface with the backend

	/**
	 * Gets all the addresses stored on the database and returns them
	 * @return A list of Addresses
	 */
	@GetMapping("/")
	@ApiOperation(value = "Gets all the addresses stored on the database and returns them",
	response = Address.class, responseContainer = "List")
	public List<Address> getAllAddresses() {
		try {
			if(conn!=null) {
				List<Address> addresses = addressHelper.getAllAddresses(conn); //Gets all the addresses from the database
				if(addresses!=null) { //Makes sure the backend got valid data
					return addresses; //Returns the addresses list
				}else {
					return null;
				}
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets a singular address from the database based on the id passed and returns it
	 * @param id ID of the address on the database
	 * @return An address object
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Gets a singular address from the database based on the id passed and returns it",
	response = Address.class)
	public Address getAddressByID(@PathVariable int id) {
		try {
			Address address = addressHelper.getSingularAddress(conn, id); //Gets a singular address from the database based on the id passed
			if(address!=null) { //Makes sure backend got valid data
				return address; //Returns the address
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates a user specified address at the id passed
	 * @param id ID of the address to be updated
	 * @param address The new address to update the current address
	 * @return An address object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a user specified address at the id passed",
	response = Address.class)
	public Address updateAddress(@PathVariable int id, @RequestBody Address address) {
		try {
			Address updatedAddress = addressHelper.updateAddress(conn, id, address); //Updates an address at the given id
			if(updatedAddress.equals(address)) { //Makes sure the user passed address and the returned address are the same
				return updatedAddress; //Returns the updated address
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Inserts a new address into the database
	 * @param address New address to be added
	 * @return New address added
	 */
	@PostMapping("/")
	@ApiOperation(value = "Inserts a new address into the database",
	response = Address.class)
	public Address insertAddress(@RequestBody Address address) {
		try {
			Address newAddress = addressHelper.insertAddress(conn, address); //Inserts a new address
			if(newAddress.equals(address)) { //Makes sure the user passed address and the returned address are the same
				return newAddress; //Returns the new address
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Removes a user specified address from the database
	 * @param id ID of the address to be removed
	 * @return True if address removed otherwise false
	 */
	@DeleteMapping("/")
	@ApiOperation(value = "Removes a user specified address from the database",
	response = boolean.class)
	public boolean removeAddress(@PathVariable int id) {
		try {
			boolean removed = addressHelper.removeAddress(conn, id); //Creates a boolean and removes the address from the database
			if(removed) { //Makes sure the backend removed the address
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
