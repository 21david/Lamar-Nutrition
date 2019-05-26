/*Jeremy Lovelace, Chris Blackwell, David Espinosa, Bilal Mahmood
  CPSC 4360 Spring 2019
  Estimating Scores of Nutrition Facts for Meals on Restaurant Menus and Home
*/

/*
   This class takes care of the connection to the database
   and querying the database.
 */

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

	// String variable for the database file path
	private String dbPath;
	
	// Variable for the dB connection
	private Connection connection = null;
	
	// Variable for the statement to send queries
	private Statement statement = null;
	
	// Variable for the result of a query
	private ResultSet resultSet = null;

	// DBConnection constructor
	public DBConnection (String path) {
		// path passed from calling class
		dbPath = path;

		// Load/register JDBC driver class with ry-catch
		try {
			// load driver
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException driverLoadError) {
			// print error if driver loading fails
			System.out.println("Problem in loading"
					+ " MS Access JDBC driver");
			driverLoadError.printStackTrace();
		}

		// Open the database connection with try-catch
		try {
			// load dB using driver and specified path (in classpath [bin folder])
		//	 String dbURL = "jdbc:ucanaccess:// " + DBConnection.class.getResource(dbPath).getPath();
			String dbURL = "jdbc:ucanaccess://" + dbPath;
			
			// new dB connection
			connection = DriverManager.getConnection(dbURL); 
			
			// new statement for queries
			statement = connection.createStatement();

		}
		catch(SQLException dbError){
			// print error if db connection fails
			System.out.println("Problem in connecting to"
					+ " database");
			dbError.printStackTrace();
		}
		catch (NullPointerException nullError ) {
			System.out.println("Problem in query (NullPointerException)");
			nullError.printStackTrace();		
		}
	} // end of constructor

	
	// execute a query with a single value returned 
	// (i.e. protein per 100g of an ingredient)
	public String executeQuery (String query) { 
		// try-catch in case of query error
		try {
			// resultSet is what gets returned by query
			resultSet = this.statement.executeQuery(query);
			while(resultSet.next()) {
				// return single result as string (first result)
				return resultSet.getString(1);
			}			
			// if resultSet was empty, return empty string
			return "";

		} catch (SQLException queryError) {			
			// print error message and return empty string
			System.out.println("Problem in query");
			queryError.printStackTrace();
			return "";
		} catch (NullPointerException nullError ) {
			System.out.println("Problem in query");
			nullError.printStackTrace();
			return "ERROR";
		}
	} // end of executeQuery method 1
	

	// execute a query with multiple values expected (i.e. complete list of products)
	// returned as ArrayList<String[]> 
	// numCols is expected number of columns for the result based on the query
	public ArrayList<String[]> executeQuery (String query, int numCols) {
		// temporary ArrayList<String[]> for result of the query
		ArrayList<String[]> tempArray = new ArrayList<String[]>();
		
		try {
			// resultSet is what gets returned by query
			resultSet = this.statement.executeQuery(query);
			
			// keeping getting results while there is more rows
			while(resultSet.next()) {
				// temp string array for the fields of the result of each row
				String[] tempString = new String[numCols];
				
				// loop through getting each field per row
				// num iterations determined by numCols (expected columns)
				for (int i = 0; i < numCols; i++) {
					tempString[i] = resultSet.getString(i + 1);
				}
				
				// add the current row of fields (tempString[]) to ArrayList
				tempArray.add(tempString);
			}

		} catch (SQLException queryError) {
			// print error message if query fails
			System.out.println("Problem in query (SQLException)");
			queryError.printStackTrace();
			
		} catch (NullPointerException nullError ) {
			System.out.println("Problem in query (NullPointerException)");
			nullError.printStackTrace();
		}
		
		// return ArrayList<String[]> of all query results
		return tempArray;
	} // end of executeQuery method 2
	

	// method to close the dB connection when finished
	public void closeConnection () {
		// use try-catch in case of error while closing
		try {
			if(null != connection) {
				// close and cleanup resources after processing
				resultSet.close();
				statement.close();

				// close connection
				connection.close();
			}
		}
		catch (SQLException dbCloseError) {
			// print error message if close fails
			System.out.println("Problem in closing database");
			dbCloseError.printStackTrace();
		}
	} // end of closeConnection method
	
} // end of DBConnection class