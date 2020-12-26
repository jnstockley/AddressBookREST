package com.github.jnstockley.addressbookrest;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This small part of the program helps make the connection to the MySQL database
 * @author jackstockley
 * @version 3.2
 */

public class RESTController{

	private Updater updater = new Updater(); // Object that helps with checking for program updates
	private double version = Updater.getVersion(); // Get the current project version

	/**
	 * Checks to make sure the program is up to date before creating a connection to the database
	 * @return Connection to the database
	 */
	public Connection getConnection() {
		try {
			boolean upToDate = updater.upToDate(version); //Makes sure the program is up to date
			if(upToDate) {
				Class.forName("com.mysql.cj.jdbc.Driver"); //Loads driver
				return DriverManager.getConnection("jdbc:mysql://10.0.0.5/addressBook?user=jackstockley&password=Dr1v3r0o&serverTimezone=UTC"); //Build the MySQL connection change IP username and password to connect to own database
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
