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
 * Manages the RESTful side of the person object
 * @author jackstockley
 * @version 2.6
 *
 */
@Path("person")
public class PersonController {

	private Person personHelper = new Person();
	private Connection conn = RESTController.getConnection();

	/**
	 * Returns JSON for all the people in the database or status code 204 if no people in databse
	 * @return Resposne with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPeople() {
		try {
			List<Person> people = personHelper.getAllPeople(conn);
			if(!people.isEmpty()) {
				Gson json = new Gson();
				//Response.ok().allow("GET");
				return Response.ok(json.toJson(people), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Returns JSON for all the people in the database with similar field and data or status code 204 if no similar people
	 * @param field Field to match data with
	 * @param data Similar data between people
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{field}/{data}")
	public Response getSimilarPeople(@PathParam("field") String field, @PathParam("data") String data) {
		try{
			if(field.equals("date") || field.equals("time")) {
				data = data.replace("*", "%");
			}
			List<Person> people = personHelper.getSimilarPeople(conn, field, data);
			if(people!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(people), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Returns JSON for a singular person in the database or 204 if no person matches the id
	 * @param id ID of the person to be returned
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getSingularPerson(@PathParam("id") int id) {
		try {
			Person person = personHelper.getSingularPerson(conn, id);
			if(person!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(person), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Allows user to sumbit a POST request to update a person on the database
	 * @param id ID of the person to be updated
	 * @param person The new person that will replace the current person
	 * @return Response 200 and new person if updated otherwise 500
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update/{id}") //Makes sense???
	public Response updatePerson(@PathParam("id") int id, Person person) {
		try {
			Person newPerson = personHelper.updatePerson(conn, id, person);
			if(newPerson!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newPerson), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500).build();
			}
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Allows user to sumbit a PUT reuest to create a new person on the database
	 * @param person The new person to be added
	 * @return Response 200 and new person if added otherwise 500
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("insert/")
	public Response insertPerson(Person person) {
		try{
			Person newPerson = personHelper.insertPerson(conn, person);
			if(newPerson!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newPerson), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500).build();
			}
		}
		catch(Exception e) {
			return Response.status(500).build();
		}
	}

	/**
	 * Allows user to submit a DELETE request to delete a person on the databse
	 * @param id The id of the person
	 * @return Response with JSON saying removed: true otherwise 500
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("remove/{id}")
	public Response removePerson(@PathParam("id") int id) {
		try{
			boolean removed = personHelper.removePerson(conn, id);
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
