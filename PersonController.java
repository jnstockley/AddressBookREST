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
 * Manages the RESTful side of the person object
 * @author jackstockley
 * @version 2.00
 *
 */
@Path("person")
public class PersonController {

	private Connection conn = RESTController.getConnection();

	/**
	 * Returns JSON for all the people in the database or status code 204 if no people in databse
	 * @return Resposne with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPeople() {
		try {
			List<Person> people = Person.getPerson(conn);
			if(!people.isEmpty()) {
				Gson json = new Gson();
				//Response.ok().allow("GET");
				return Response.ok(json.toJson(people), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "PersonController.java", "getAllPeople()")).build();
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
			List<Person> people = Person.getPerson(conn, field, data);
			if(people!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(people), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "PersonController.java", "getSimilarPeople()")).build();
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
			Person person = Person.getPerson(conn, id);
			if(person!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(person), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "PersonController.java", "getSingularPerson()")).build();
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
			Person newPerson = Person.updatePerson(conn, id, person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getHomePhone(), person.getMobilePhone(), person.getWorkPhone(), person.getHomeEmail(), person.getWorkEmail(), person.getHeight(), person.getWeight(), person.getRace(), person.getGender(), person.getAddressId(), person.getOccupationId());
			if(newPerson!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newPerson), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500, Helper.log("Error updating person at ID: " + id + "!", "PersonController.java", "updatePerson()")).build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "PersonController.java", "updatePerson()")).build();
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
			Person newPerson = Person.insertPerson(conn, person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getHomePhone(), person.getMobilePhone(), person.getWorkPhone(), person.getHomeEmail(), person.getWorkEmail(), person.getHeight(), person.getWeight(), person.getRace(), person.getGender(), person.getAddressId(), person.getOccupationId());
			if(newPerson!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newPerson), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500, Helper.log("Error inserting address!", "PersonController.java", "insertPerson()")).build();
			}
		}
		catch(Exception e) {
			return Response.status(500, Helper.log(e, "PersonController.java", "insertPerson()")).build();
		}
	}

	/**
	 * Allows user to submit a DELETE request to delete a person on the databse
	 * @param field The field to match with the person
	 * @param data Data of matchin field in person
	 * @return Response with JSON saying removed: true otherwise 500
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("remove/{field}/{data}")
	public Response removePerson(@PathParam("field") String field, @PathParam("data") String data) {
		try{
			boolean removed = Person.removePerson(conn, field, data);
			if(removed) {
				return Response.ok("{\"removed\": \"true\"}").build();
			}else {
				return Response.status(500, Helper.log("Error removing person with " + field + " and data " + data, "PersonController.java", "removePerson()")).build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "PersonController.java", "removePerson()")).build();
		}
	}
}
