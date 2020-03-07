package com.jackstockley.addressbookrest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This part of the program helps with random tasks to make the program function
 * Part of the functions are logging and allowing for any data or time searching on the database
 * @author jnstockley
 * @version 2.00
 *
 */

public class Helper {

	/**
	 * Takes an exception from a try catch method and print the error message to a log file in the current directory of the program
	 * @param e The error message from a try catch
	 * @param object The object or class the error message came from
	 * @param method The method or function the error message came from
	 * @return A string formatted with the error message, object, method, and date of error
	 */
	public static String log(Exception e, String object, String method) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //Sets up date and time of the error
		return object + ": " + method + " - " + formatter.format(date) + " " + e.getMessage();
	}

	/**
	 * Take an exception as a string and prints the error message to a log file in the current directory of the program
	 * @param e A hard coded error message in the program
	 * @param object The object or class the error message came from
	 * @param method The method or function the error message came from
	 * @return A string formatted with the error message, object, method, and date of error
	 */
	public static String log(String e, String object, String method) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //Sets up date and time of the error
		return object + ": " + method + " - " + formatter.format(date) + " " + e;
	}

	/**
	 * Returns the most recent address, occupation, or person based on ID in the database
	 * @param conn The MySQL connection
	 * @param table The table that the prepared statement will get the highest ID from
	 * @return An integer of he id of the most recent item in a given table
	 */
	public static int mostRecent(Connection conn, String table) {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) from " + table); //A statement that will find the newest item in a table based on ID
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int col = 1;
				return rs.getInt(col++);
			} 
			return -1;
		} catch (Exception e) {
			log(e, "Helper.java", "mostRecent()");
			return -1;
		} 
	}

	/**
	 * Checks if a newly created address was added to the database
	 * @param newAddress The new address that was inserted
	 * @param conn The MySQL connection
	 * @return True if the address was added to the database otherwise false
	 */
	public static boolean exists(Address newAddress, Connection conn) {
		List<Address> addresses = Address.getAddress(conn); //Gets all the addresses in the database
		for (Address address : addresses) { //Loops through all addresses
			if (address.equals(newAddress)) //Checks if an address on the database equals the newly created address
				return true; 
		} 
		return false;
	}

	/**
	 * Checks if a newly created occupation was added to the database
	 * @param newOccupation The new occupation that was inserted
	 * @param conn The MySL connection
	 * @return True if the occupation was added otherwise false
	 */
	public static boolean exists(Occupation newOccupation, Connection conn) {
		List<Occupation> occupations = Occupation.getOccupation(conn); //Gets all the occupations in the database
		for (Occupation occupation : occupations) { //Loops through all occupations
			if (occupation.equals(newOccupation)) //Checks if an occupation on the database equals the newly created occupation
				return true; 
		} 
		return false;
	}

	/**
	 * Checks if a newly created person was added to the database
	 * @param newPerson The new person that was inserted
	 * @param conn The MySQL connection
	 * @return True if the person was added otherwise false
	 */
	public static boolean exists(Person newPerson, Connection conn) {
		List<Person> people = Person.getPerson(conn); //Gets all the people in the database
		for (Person person : people) { //Loops through all people
			if (person.equals(newPerson)) //Checks if a person on the database equals the newly created person
				return true; 
		} 
		return false;
	}

	/**
	 * Takes in a string with no spaces and adds a space whenever the word has a capital letter
	 * @param word String with capital letter and no spaces
	 * @return String with capital letter and spaces
	 */
	public static String split(String word) {
		String splitWord = word.substring(0, 1);
		for (int i = 1; i < word.length(); i++) { //Loops through the whole word
			Character ch = Character.valueOf(word.charAt(i));
			if (Character.isUpperCase(ch.charValue())) { //Checks if a given character of a word if capitalized or not
				splitWord = String.valueOf(splitWord) + " " + ch;
			} else {
				splitWord = String.valueOf(splitWord) + ch;
			} 
		} 
		return splitWord;
	}

	/**
	 * Makes sure the directory when saving files is valid and doesn't include the bin folder or AddressBook.jar
	 * @param dir The current directory of the jar file
	 * @return The fixed directory not including AddressBook.jar or the bin folder
	 */
	public static String dirFixer(String dir) {
		if(dir.indexOf("AddressBook.jar") != -1) {
			return dir.substring(0, dir.length()-19);
		}else if(dir.indexOf("bin") != -1) {
			return dir.substring(0, dir.length()-4);
		}else {
			return dir;
		}
	}
}