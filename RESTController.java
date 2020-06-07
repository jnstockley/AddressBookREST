package com.jackstockley.AddressBookREST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


/**
 * Handles making the connection between the RESTfull web service and the MySQL server
 * @author jackstockley
 * @version 2.6
 *
 */
@Path("connect")
public class RESTController {

	private static Connection conn = null;

	/**
	 * Checks if the connection is null and if true returns test server else returns conn
	 * @return The MySQL connection
	 */
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if(conn==null) {
				conn = DriverManager.getConnection("jdbc:mysql://10.0.0.191/addressBook?user=********&password=********&serverTimezone=UTC");
				return conn;
			}else {
				return conn;
			}
		}catch(Exception e) {
			Helper.log(e, "RESTController.java", "getConnection()");
			return null;
		}
	}

	/**
	 * Allows user to use REST server to save a connection to file
	 * @param server The server address
	 * @param database The database name
	 * @param userName The userName for the database
	 * @param password The password for the database
	 * @return Response if the connection was saved or not
	 */
	@GET
	@Path("{server}/{database}/{userName}/{password}")
	public Response setUpConnection(@PathParam("server") String server, @PathParam("database") String database, @PathParam("userName") String userName, @PathParam("password") String password) {
		try{
			String connection = MessageFormat.format("jdbc:mysql://{0}/{1}?user={2}&password={3}", new Object[] { server, database, userName, password});
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connection);
			if(conn.isValid(1)) {
				return Response.ok("Connection set up with server " + server + " and database " + database + " and user " + userName).build();
			}else {
				return Response.status(500).build();
			}
		}catch(Exception e) {
			return Response.status(500).build();

		}
	}
}
