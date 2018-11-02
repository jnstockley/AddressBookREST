package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Date May 7, 2018
 * 
 * @Description Sets up the mySQL connection for the REST Interface
 * 
 * @author Jack Stockley
 * 
 * @version 1.0
 *
 */

public class RESTController {
	public static String IP; //NULL Variables for the mySQL server IP
	public static final String user = "user"; //Username for the mySQL sever
	public static final String password = "pass"; //Password for the mySQL server
	static Connection conn; //NULL connection to get connected to the mySQL server

	/**
	 * Gets a connection to server
	 * @return The mySQL connection
	 * @throws SQLException
	 */
	static Connection getConnection() throws SQLException {
		if (conn != null) {
			return conn;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");

		}
		catch (Exception e3){
		}

		try{
			conn = DriverManager.getConnection("jdbc:mysql://10.0.0.183:8080/mydb",user,password);
			return conn;
		}catch(SQLException e){
			try {
				conn = DriverManager.getConnection("jdbc:mysql://jackstockley.ddns.net:8080/mydb",user,password);
				return conn;
			}
			catch (SQLException e2){
				try{
				conn = DriverManager.getConnection("jdbc:mysql://localhost:8080/mydb", user, password);
				}catch (Exception e5){
					throw e5;
				}
			}	
			catch (Exception e3){
				throw e3;
			}
		}
		return conn;
	}
}