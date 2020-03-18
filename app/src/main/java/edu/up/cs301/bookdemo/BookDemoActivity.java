package edu.up.cs301.bookdemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BookDemoActivity extends Activity {
	
	// EditTexts for giving title/author/price for a new book
	private EditText titleText;
	private EditText authorText;
	private EditText priceText;
	
	// TextView for displaying information for current book
	private TextView bookDisplayText;
	
	// list of books in our database
	private ArrayList<BookRecord> bookList = null;
	
	// index of the book we're currently viewing
	private int currentBookIndex;
	
/**
 * method executed when activity is created
 * 
 * @param savedInstanceState
 * 		saved state
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// perform superclass initialization, load layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_demo);

        // get the EditText objects
        titleText = (EditText)findViewById(R.id.title_field);
        authorText = (EditText)findViewById(R.id.author_field);
        priceText = (EditText)findViewById(R.id.price_field);
        
        // get the object for displaying
        bookDisplayText = (TextView)findViewById(R.id.book_display);
        
        // initialze book-index to first book
        currentBookIndex = 0;
                
		// read the serialized book-list
        try {
        	// create input stream
        	FileInputStream fos = openFileInput("bookData.dat");
        	ObjectInputStream ios = new ObjectInputStream(fos);
        	
        	// read object and check if it's an ArrayList
        	Object obj = ios.readObject();
        	if (obj instanceof ArrayList<?>) {
        		bookList = (ArrayList<BookRecord>)obj;
        	}
        	
        	// close input file
        	ios.close();
        }
        catch (IOException iox) {
        	// ignore exception
        }
        catch (ClassNotFoundException cnfx) {
        	// ignore exception
        }
        
        // if object was not initialized (e.g., due to error), create a dummied-up
        // version with three books
        if (bookList == null) {
        	bookList = new ArrayList<BookRecord>();
        	bookList.add(new BookRecord("The Cat in the Hat", "Dr. Suess", 10));
        	bookList.add(new BookRecord("Snow Treasure", "Marie Mcswigan", 21));
        	bookList.add(new BookRecord("Software Testing", "Ron Patton", 40));
        }
        
        // display the current book
        displayCurrentBook();
    }
    
    /**
     * helper method to display the current book
     */
    private void displayCurrentBook() {
    	// ensure index is in range
    	currentBookIndex = Math.max(0, currentBookIndex);
    	currentBookIndex = Math.min(bookList.size()-1, currentBookIndex);
    	
    	// display the book at that index
    	BookRecord currentRec = bookList.get(currentBookIndex);
    	bookDisplayText.setText(currentRec.getTitle()+"\n"+
    			currentRec.getAuthor()+"\n"+
    			currentRec.getPrice());
    }

    /**
     * callback method: "Previous" button pressed
     * 
     * @param v
     * 		the button
     */
    public void previousPressed(View v) {
    	currentBookIndex--; // decrement index
    	displayCurrentBook(); // display book
    }

    /**
     * callback method: "Next" button pressed
     * 
     * @param v
     * 		the button
     */
    public void nextPressed(View v) {
    	currentBookIndex++; // increment index
    	displayCurrentBook(); // display book
    }
    
    /**
     * callback method: "Add" button pressed
     * 
     * @param v
     * 		the button
     */
    public void addPressed(View v) {
    	
    	// get the text from the three fields
    	String title = titleText.getText().toString();
    	String author = authorText.getText().toString();
    	String priceString = priceText.getText().toString();
    	
    	// if either title or author is blank, bail
    	if (title.trim().isEmpty() || author.trim().isEmpty()) {
    		return;
    	}
    	
    	// convert price field to integer; if problem, bail
    	int price;
    	try {
    		price = Integer.parseInt(priceString);
    	}
    	catch (NumberFormatException nfx) {
    		return;
    	}
    	
    	// create a record and add it to the list
    	BookRecord newRec = new BookRecord(title, author, price);
    	bookList.add(newRec);
    	
    	// clear the text fields
    	titleText.setText("");
    	authorText.setText("");
    	priceText.setText("");
    	
		// write the (updated version of) the serialized book-list
        try {
        	// open output stream
    		FileOutputStream fos = openFileOutput("bookData.dat", Context.MODE_PRIVATE);
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		
    		// write the ArrayList object
    		oos.writeObject(bookList);
    		
    		// close the file
    		oos.close();
        }
        catch (IOException iox) {
        	// ignore exceptions
        }  	
    }
    
    /**
     * callback method: "Quit" button pressed
     * 
     * @param v
     * 		the button
     */
    public void quitPressed(View v) {
    	// finish the application
    	finish();
    }
    
}
