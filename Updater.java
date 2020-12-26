package com.github.jnstockley.addressbookrest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This small part of the program helps check for version updated of the program and tells the user how to download them
 * @author jnstockley
 * @version 3.2
 *
 */

public class Updater {
	
	private final static double VERSION = 3.2; //Current project version
	
	
	/**
	 * @return the version
	 */
	public static double getVersion() {
		return VERSION;
	}

	
	/**
	 * Checks a text file stored on my github repository and gets the current version number and checks if the program version is less then or equal to the newest version
	 * @param appVersion A variable stored in the main class of the program that has the current program version
	 * @return False is needs updating or true if up to date
	 */
	public boolean upToDate(double appVersion) {
		try{
			Document doc = Jsoup.connect("https://github.com/jnstockley/AddressBookREST/blob/master/Version.txt").get(); //The URL to where the version text file is stored
			String currentVersion = doc.select("table").first().text(); //Find the string of text in the version.txt file
			if(appVersion<Double.parseDouble(currentVersion)) { //Checks if the version number on the github repository is greater then the program version
				return false;
			}else {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}

	/**
	 * Returns a string informing the user the program is out-of-date
	 * @return String with out-of-date message
	 */
	public String updateMessage() {
		return "Server is out of date! Please update to the latest version at https://github.com/jnstockley/AddressBookREST/releases";
	}
}
