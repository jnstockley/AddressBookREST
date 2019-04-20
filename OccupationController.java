package mysql;

import java.sql.SQLException;
import java.util.List;

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
import com.google.gson.Gson;

/**
 * @Date May 7, 2018
 * 
 *  @Description An interface that allows a user to get, insert, update, delete occupation data from a mySQL database also know as a REST Interface
 *  
 * @author 1124655
 * 
 * @version 1.0
 */

@Path("/occupation")
public class OccupationController {

	/**
	 * Returns all the occupations in the database
	 * @return A list of occupations
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() throws SQLException{
		List<Occupation> occupations = Occupation.getAll(RESTController.getConnection());
		Gson json = new Gson();
		return Response.ok(json.toJson(occupations), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}

	/**
	 * Returns a singular occupation based on the id a user passes
	 * @param id The id of the occupation the user wants returned
	 * @return A singular occupation
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") int id) throws SQLException{
		Occupation occupation = Occupation.getBy(RESTController.getConnection(), Integer.toString(id), "id");
		Gson json = new Gson();
		return Response.ok(json.toJson(occupation), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}

	/**
	 * Inserts an occupation into the database
	 * @param occupation An occupation object
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Occupation occupation) throws SQLException {
		Gson json = new Gson();
		occupation.setId(Occupation.insert(RESTController.getConnection(),  occupation.getOccupation()));
		return Response.ok(json.toJson(occupation)).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}

	/**
	 * Removes an occupation from the database using the given id
	 * @param id The id of the occupation to remove
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteById(@PathParam("id") int id) throws SQLException{
		Occupation.remove(RESTController.getConnection(), id);
		return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
	
	/**
	 * Updates an occupation from the database using the given id
	 * @param id The id of the occupation to update
	 * @param occupation An occupation object with the new occupation
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Occupation occupation)throws SQLException{
		Gson json = new Gson();
		Occupation.update(RESTController.getConnection(), id, occupation.getOccupation());
		return Response.ok(json.toJson(occupation)).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}
}