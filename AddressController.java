package com.jackstockley.addressbookrest;

import java.sql.Connection;
import java.util.List;
import com.google.gson.Gson;

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
 *
 */

@Path("address")
public class AddressController {

	private Connection conn = RESTController.getConnection();

	/**
	 * Returns JSON for all the addresses in the database or status code 204 if no addresses in databse
	 * @return Resposne with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAddresses() {
		try {
			List<Address> addresses = Address.getAddress(conn);
			if(addresses!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(addresses), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "AddressController.java", "getAllAddresses()")).build();
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
			List<Address> addresses = Address.getAddress(conn,field, data);
			if(addresses!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(addresses), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "AddressController.java", "getSimilarAddresses()")).build();
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
			Address address = Address.getAddress(conn, id);
			if(address!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(address), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "AddressController.java", "getSingularAddress()")).build();
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
			Address newAddress = Address.updateAddress(conn, id, address.getNumber(), address.getStreet(), address.getCity(), address.getState(), address.getZip());
			if(newAddress!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newAddress), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500, Helper.log("Error updating address at ID: " + id + "!", "AddressController.java", "updateAddress()")).build();
			}
		}catch(Exception e) {
			return Response.status(500, e.getMessage()).build();
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
			Address newAddress = Address.insertAddress(conn, address.getNumber(), address.getStreet(), address.getCity(), address.getState(), address.getZip());
			if(newAddress!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newAddress), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500, Helper.log("Error inserting address!", "AddressController.java", "insertAddress()")).build();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500, Helper.log(e, "AddressController.java", "insertAddress()")).build();
		}
	}

	/**
	 * Allows user to submit a DELETE request to delete an address on the databse
	 * @param field The field to match with the address
	 * @param data Data of matchin field in address
	 * @return Response with JSON saying removed: true otherwise 500
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("remove/{field}/{data}")
	public Response removeAddress(@PathParam("field") String field, @PathParam("data") String data) {
		try{
			boolean removed = Address.removeAddress(conn, field, data);
			if(removed) {
				return Response.ok("{\"removed\": \"true\"}").build();
			}else {
				return Response.status(500, Helper.log("Error removing address with " + field + " and data " + data + "!", "AddressController.java", "removeAddress()")).build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "AddressController.java", "removeAddress()")).build();
		}
	}
}
