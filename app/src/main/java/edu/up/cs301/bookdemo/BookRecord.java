package edu.up.cs301.bookdemo;

import java.io.*;

/**
 * Defines a book-record that consists of a title, and author, and a price
 *
 * @author Steven R. Vegdahl
 * @version 4 November 2013
 * Status: complete; allegedly debugged, but not well-tested
 *
 */
public class BookRecord implements Serializable {

	// to satisfy the Serializable interface
	private static final long serialVersionUID = -3443192416586596539L;

	// instance variables for our three pieces of information
	private String title;   // the book's title
	private String author;  // its author
	private int price;      // its price, in dollars


	/**
	 * BookRecord - constructor for BookRecord
	 * 
	 * calling sequence:
	 *   bookRec = new BookRecord(aTitle, anAuthor, aPrice)
	 * 
	 * parameters:
	 *   aTitle - the book's title
	 *   anAuthor - the book's author
	 *   aPrice - the books price, in cents
	 * 
	 * result:
	 *   a new BookRecord is created
	 * 
	 * side-effects:
	 *   none
	 * 
	 * bugs/anomolies
	 *   none known
	 *
	 */
	public BookRecord(String ttl, String auth, int pr) {
		// set the instance variables
		title = ttl;
		author = auth;
		price = pr;
	}

	/**
	 * returns title of the book
	 * 
	 * @return
	 *   	book's title
	 *
	 */
	public String getTitle() { return title; }

	/**
	 * returns the book's author
	 * 
	 * @return
	 *   book's author
	 */
	public String getAuthor() { return author; }

	/**
	 * returns the book's price
	 * 
	 * @return
	 *   book's price, in dollars
	 */
	public int getPrice() { return price; }
}
