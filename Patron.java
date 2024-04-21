/*
Project     : Library System
Author      : Andrea Howey 
Created on  : 04/06/2024
Updated by  : Hamid Mddi
Updated on  : 04/21/24
Description : This is the Patron Class for the Library System.
*/

public class Patron {
	// Attributes
	private int patronID;
	private String name;
	private String phone;
	private String email;

	// default constructor
	public  Patron() {}

	// constructor
	public Patron(int patronID, String name, String phone, String email) {
		this.patronID = patronID;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	// getter methods
	public int getPatronID() {
		return patronID;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	// setters methods
	public void setPatronID(int patronID) {
		this.patronID = patronID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
	



