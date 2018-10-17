package mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Address {
	private int id;
	private String number;
	private String name;
	private String city;
	private String state;
	private String zip;

	public Address(){
	}

	public int getId(){
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Prints out the id, the house number, the street name, the city, the state, and the zip code of the address called
	 */
	public void print() {
		System.out.println("Id: " + this.getId() + " " + this.getNumber() + " " + this.getName() + " " + this.getCity() + ", " + this.getState() + " " + this.getZip());
	}

	/**
	 * Returns id, the house number, the street name, the city, the state, and the zip code of the address called
	 */
	public String display() {
		return "Id: " + this.getId() + " " + this.getNumber() + " " + this.getName() + " " + this.getCity() + ", " + this.getState() + " " + this.getZip();
	}

	/**
	 * Returns all the addresses in the table
	 * @param conn The mySQL connection
	 * @return The List of all the addresses in the address table
	 */
	public static List<Address> getAll(Connection conn){
		List<Address> addresses = new ArrayList<Address>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT id, number, name, city, state, zip FROM address");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Address address = new Address();
				int col = 1;
				address.setId(rs.getInt(col++));
				address.setNumber(rs.getString(col++));
				address.setName(rs.getString(col++));
				address.setCity(rs.getString(col++));
				address.setState(rs.getString(col++));
				address.setZip(rs.getString(col++));
				addresses.add(address);
			}
			return addresses;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Returns a singular address by the passed in field Name
	 * @param conn The mySQL connection
	 * @param value The value the fieldName needs to equal for the address to be returned
	 * @param fieldName The field the user is looking up
	 * @return The singular address the user wants
	 */
	public static Address getBy(Connection conn, String value, String fieldName){
		try {
			Address address = new Address();
			PreparedStatement ps = conn.prepareStatement("SELECT id, number, name, city, state, zip FROM address where " + fieldName + "  = ?");
			if(fieldName.equals("id") || fieldName.equals("zip")){
				ps.setInt(1,  Integer.parseInt(value));
			}else{
				ps.setString(1, value);
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				int col = 1;
				address.setId(rs.getInt(col++));
				address.setNumber(rs.getString(col++));
				address.setName(rs.getString(col++));
				address.setCity(rs.getString(col++));
				address.setState(rs.getString(col++));
				address.setZip(rs.getString(col++));
			}
			return address;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Takes an address object and passes it to the insert method
	 * @param conn The mySQL connection
	 * @param address An address object with the new number, name, city, state, and zip
	 */
	public static void insert(Connection conn, Address address){
		try {
			Address.insert(conn, address.getNumber(), address.getName(), address.getCity(), address.getState(), address.getZip());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Inserts a new row into the table
	 * @param conn The mySQL connection
	 * @param number The house number the user entered
	 * @param name The street name the user entered
	 * @param city The city the user entered
	 * @param state The state the user entered
	 * @param zip The zip code the user entered
	 */
	public static void insert(Connection conn, String number, String name, String city, String state, String zip){
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO address (number, name, city, state, zip) values(?,?,?,?,?)");
			ps.setString(1, number);
			ps.setString(2, name);
			ps.setString(3, city);
			ps.setString(4, state);
			ps.setString(5, zip);
			ps.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Takes an address object and passes it to the update method
	 * @param conn The mySQL connection
	 * @param id The id of the address the user wants to update
	 * @param address An address object with the new number, name, city, state, and zip
	 */
	public static void update(Connection conn, int id, Address address){
		try {
			Address.update(conn, id, address.getNumber(), address.getName(), address.getCity(), address.getState(), address.getZip());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Updates all the fields of a given data table in the address table
	 * @param conn The mySQL connection
	 * @param id The id of the data the user wants to update
	 * @param number The new house number
	 * @param name The new street name
	 * @param city The new city name
	 * @param state The new state
	 * @param zip The new zip code
	 */
	public static void update(Connection conn, int id, String number, String name, String city, String state, String zip){
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE address SET number=?,name=?,city=?,state=?,zip=? WHERE id = ?");
			ps.setString(1, number);
			ps.setString(2, name);
			ps.setString(3, city);
			ps.setString(4, state);
			ps.setString(5, zip);
			ps.setInt(6, id);
			ps.executeUpdate();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Removes data from the mySQL table where the id's equal each other
	 * @param conn The mySQL connection
	 * @param id The id of the data in the table the user wants to remove
	 */
	public static void remove(Connection conn, int id){
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM address WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
}