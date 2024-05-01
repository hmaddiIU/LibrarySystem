package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Project: Library System
 * Author     : Andrea Howey
 * Description: This class will be used to create a reports system.
 */
public class Reports implements Serializable {
		
	//create arrayList for Patron info
	public ArrayList<Patron> patrons = new ArrayList<>(); {
	    
	}
	//load patron info from file system
	
		private void patronReport() {
	        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("patrons.ser"))) {
	            patrons = (ArrayList<Patron>) in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            patrons = new ArrayList<>();
	        }

         }
		
		//create arrayList for available Books 
  public ArrayList<Book> books = new ArrayList<> (); {
	 
  }
	  private void bookReport() {
	        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("books.ser"))) {
	            books = (ArrayList<Book>) in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            books = new ArrayList<>();
	        }

       }
