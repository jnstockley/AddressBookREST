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
 * Manages the RESTful side of occupation object
 * @author jackstockley
 * @version 2.00
 */

@Path("occupation")
public class OccupationController {

	private Connection conn = RESTController.getConnection();

	/**
	 * Returns JSON for all the occupations in the database or status code 204 if no occupations in databse
	 * @return Resposne with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOccupations() {
		try {
			List<Occupation> occupations = Occupation.getOccupation(conn);
			if(occupations!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(occupations), MediaType.APPLICATION_JSON).build();

			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "OccuaptionController.java", "getAllOccupations()")).build();
		}
	}

	/**
	 * Returns JSON for all the occupations in the database with similar field and data or status code 204 if no similar occupations
	 * @param field Field to match data with
	 * @param data Similar data between occupations
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{field}/{data}")
	public Response getSimilarOccupations(@PathParam("field") String field, @PathParam("data") String data) {
		try {
			if(field.equals("date") || field.equals("time")) {
				while(data.contains("*")) {
					data = data.replace("*", "%");
				}
			}
			List<Occupation> occupations = Occupation.getOccupation(conn, field, data);
			if(occupations!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(occupations), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "OccuaptionController.java", "getSimialrOccupations()")).build();
		}
	}

	/**
	 * Returns JSON for a singular occupation in the database or 204 if no occupation matches the id
	 * @param id ID of the occupation to be returned
	 * @return Response with either 200, 204, or 500
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getSingularOccupation(@PathParam("id") int id) {
		try {
			Occupation occupation = Occupation.getOccupation(conn, id);
			if(occupation!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(occupation), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.noContent().build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "OccuaptionController.java", "getSingularOccupation()")).build();
		}
	}

	/**
	 * Allows user to sumbit a POST request to update an occupation on the database
	 * @param id ID of the occupation to be updated
	 * @param occupation The new occupation that will replace the current occupation
	 * @return Response 200 and new occupation if updated otherwise 500
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update/{id}")
	public Response updateOccupation(@PathParam("id") int id, Occupation occupation) {
		try {
			Occupation newOccupation = Occupation.updateOccupation(conn, id, occupation.getCompanyName(), occupation.getJobTitle(), occupation.getEmploymentType(), occupation.getMonthlySalary(), occupation.getIndustry());
			if(newOccupation!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newOccupation), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500, Helper.log("Error updating occupation at ID:  " + id + "!", "OccupationController.java", "updateOccupation()")).build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "OccuaptionController.java", "updateOccupation()")).build();
		}
	}

	/**
	 * Allows user to sumbit a PUT reuest to create a new occupation on the database
	 * @param occupation The new occupation to be added
	 * @return Response 200 and new occupation if added otherwise 500
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insert")
	public Response insertOccupation(Occupation occupation) {
		try{
			Occupation newOccupation = Occupation.insertOccupation(conn, occupation.getCompanyName(), occupation.getJobTitle(), occupation.getEmploymentType(), occupation.getMonthlySalary(), occupation.getIndustry());
			if(newOccupation!=null) {
				Gson json = new Gson();
				return Response.ok(json.toJson(newOccupation), MediaType.APPLICATION_JSON).build();
			}else {
				return Response.status(500, Helper.log("Error inserting occupation!", "OccupationController.java", "insertOccupation()")).build();
			}
		}
		catch(Exception e) {
			return Response.status(500, Helper.log(e, "OccuaptionController.java", "insertOccupation()")).build();
		}
	}

	/**
	 * Allows user to submit a DELETE request to delete an occupation on the databse
	 * @param field The field to match with the occupation
	 * @param data Data of matchin field in person
	 * @return Response with JSON saying removed: true otherwise 500
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("remove/{field}/{data}")
	public Response removeOccupation(@PathParam("field") String field, @PathParam("data") String data) {
		try{
			boolean removed = Occupation.removeOccupation(conn, field, data);
			if(removed) {
				return Response.ok("{\"removed\": \"true\"}").build();
			}else {
				return Response.status(500, Helper.log("Error removing occupation with " + field + " and data " + data, "OccupationController.java", "removeOccupation()")).build();
			}
		}catch(Exception e) {
			return Response.status(500, Helper.log(e, "OccuaptionController.java", "removeOccupation()")).build();
		}
	}
}
