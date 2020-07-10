package com.github.jnstockley.addressbookrest;

import java.sql.Connection;
import java.sql.DriverManager;

public class RESTController{

	private Updater updater = new Updater();
	private double version = Updater.Version;

	public Connection getConnection() {
		try {
			boolean upToDate = updater.upToDate(version);
			if(upToDate) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				return DriverManager.getConnection("jdbc:mysql://10.0.0.191/addressBook?user=Jack&password=Dr1v3r0o&serverTimezone=UTC");
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
