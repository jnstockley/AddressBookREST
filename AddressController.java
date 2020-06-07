package com.jackstockley.AddressBookREST;

import java.sql.Connection;
import java.util.List;
import com.google.gson.Gson;
import jackstockley.addressbook.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manages the RESTful side of the address object
 * @author jackstockley
 * @version 2.6
 */

@Path("address")
public class AddressController {

	private Address addressHelper = new Address();
	private Connection conn = RESTController.getConnection();

	/**
	 * Returns JSON for all the addresses in the database or status code 204 if no addresses in databse
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAddresses() {
		try {
			List<Address> addresses = addressHelper.getAllAddresses(conn);
			if(addresses!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(addresses), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	/**
	 * Returns JSON for all the addresses in the database with similar field and data or status code 204 if no similar addresses
	 * @param field Field to match data with
	 * @param data Similar data between addresses
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{field}/{data}")
	public Response getSimilarAddresses(@PathParam("field") String field, @PathParam("data") String data) {
		try{
			if(field.equals("date") || field.equals("time")) {
				while(data.contains("*")) {
					data = data.replace("*", "%");
				}
			}
			List<Address> addresses = addressHelper.getSimilarAddress(conn,field, data);
			if(addresses!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(addresses), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Returns JSON for a singular address in the database or 204 if no address matches the id
	 * @param id ID of the address to be returned
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getSingularAddress(@PathParam("id") int id) {
		try {
			Address address = addressHelper.getSingularAddress(conn, id);
			if(address!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(address), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Allows user to sumbit a POST request to update an address on the database
	 * @param id ID of the address to be updated
	 * @param address The new address that will replace the current address
	 * @return Response 200 and new address if updated otherwise 500
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update/{id}") //Makes sense???
	public Response updateAddress(@PathParam("id") int id, Address address) {
		try {
			Address newAddress = addressHelper.updateAddress(conn, id, address);
			if(newAddress!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newAddress), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500).build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Allows user to sumbit a PUT reuest to create a new address on the database
	 * @param address The new address to be added
	 * @return Response 200 and new address if added otherwise 500
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("insert")
	public Response insertAddress(Address address) {
		try{
			Address newAddress = addressHelper.insertAddress(conn, address);
			if(newAddress!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newAddress), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500).build();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	/**
	 * Allows user to submit a DELETE request to delete an address on the databse
	 * @param id The id of the address
	 * @return Response with JSON saying removed: true otherwise 500
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("remove/{id}")
	public Response removeAddress(@PathParam("id") int id) {
		try{
			boolean removed = addressHelper.removeAddress(conn, id);
			if(removed) {
				return Response.ok("{\"removed\": \"true\"}").build();
			}else {
				return Response.status(500).build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
}
