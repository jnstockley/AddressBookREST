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
 * @Description An interface that allows a user to get, insert, update, delete address data from a mySQL database also known as a REST Interface
 * 
 * @author Jack Stockley
 * 
 * @version 1.0
 *
 */

@Path("/address")
public class AddressController {

	/**
	 * Returns all the address in the database
	 * @return A list of addresses
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() throws SQLException{
		List<Address> addresses = Address.getAll(RESTController.getConnection());
		Gson json = new Gson();
		return Response.ok(json.toJson(addresses), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Returns a singular address based on the id a user passes
	 * @param id The id of the address the user wants returned
	 * @return A singular address
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") int id) throws SQLException{
		Address address = Address.getBy(RESTController.getConnection(), Integer.toString(id), "id");
		Gson json = new Gson();
		return Response.ok(json.toJson(address), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Inserts an address into the database
	 * @param address An address object
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Address address) throws SQLException {
		Address.insert(RESTController.getConnection(),  address);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Removes an address from the database using the given id
	 * @param id The id of the address to remove
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteById(@PathParam("id") int id) throws SQLException{
		Address.remove(RESTController.getConnection(), id);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Updates an address from the database using the given id
	 * @param id The id of the address to update
	 * @param address An address object with the new number, name, city, state, and zip
	 * @return A response telling the user the task was good or bad
	 * @throws SQLException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Address address)throws SQLException{
		Address.update(RESTController.getConnection(), id, address);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
}