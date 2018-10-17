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
 * @Description An interface that allows a user to get, insert, update. delete person data from a mySQL database also know as a REST Interface
 * 
 * @author Jack Stockley
 *
 * @version 1.0
 */
@Path("/person")
public class PersonController {

	/**
	 * Returns all the people in the database
	 * @return A list of people
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() throws SQLException{
		List<Person> people = Person.getAll(RESTController.getConnection());
		Gson json = new Gson();
		return Response.ok(json.toJson(people), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Returns a singular person based on the id the user passes
	 * @param id The id of the person the user wants returned
	 * @return A singular person
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") int id) throws SQLException{
		Person person = Person.getBy(RESTController.getConnection(), Integer.toString(id));
		Gson json = new Gson();
		return Response.ok(json.toJson(person), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Inserts a person into the database
	 * @param person A person object
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Person person) throws SQLException {
		Person.insert(RESTController.getConnection(),  person);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Removes a person from the database using the given id
	 * @param id The id of the person to remove
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteById(@PathParam("id") int id) throws SQLException{
		Person.remove(RESTController.getConnection(), id);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Updates a person from the database using the given id
	 * @param id The id of the person to update
	 * @param person A person object with the new first and last name, and middle initial
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Person person)throws SQLException{
		Person.update(RESTController.getConnection(), id, person);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
}