

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * @author Xiaoxian Wang, Douglas Hopper
 *
 */
public class Employees {

	/**
	 * Adds an employee to the list of employees
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void addEmployee(Connection conn) throws SQLException {
		String updateItems = "INSERT INTO Employees VALUES (?,?,?,?,?)";
		  PreparedStatement pStat = null;
		  Scanner input = new Scanner(System.in);
		  pStat = conn.prepareStatement(updateItems);
		  System.out.print("Employee ID: ");
		  String input1 = input.next();
		  System.out.print("First Name: ");
		  String input2 = input.next();
		  System.out.print("Last Name: ");
		  String input3 = input.next();
		  System.out.print("Start Date: ");
		  String input4 = input.next();
		  System.out.print("Phone Number: ");
		  String input5 = input.next();
		  pStat.setString(1,input1);
		  pStat.setString(2,input2);
		  pStat.setString(3,input3);
		  pStat.setString(4,input4);
		  pStat.setString(5,input5);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
	}
	
	/**
	 * Prints a table listing all employees
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void printEmployeeTable(Connection conn) throws SQLException, FileNotFoundException {
		
		Scanner in = new Scanner(System.in);
		Statement stat = conn.createStatement();
		System.out.println("Employee_FName  Employee_LName  Employee_ID     Employee_Phone  StartDate");
		ResultSet result = stat.executeQuery("SELECT * FROM Employees");
		while (result.next()) {
			System.out.printf("%-16s%-16s%-16s%-16s%-16s", result.getString("EmployeeID"),
					result.getString("Employee_FName"), result.getString("Employee_LName"), result.getString("Employee_PhoneNo"), result.getString("StartDate"));
			System.out.println();
		}
		System.out.print("\n\n");
		//Ask the user to print the results in a text file called "EmployeeTable.txt"
		System.out.println("Would you like to print the results out to a file? (Y/N)");
		  String answer = in.next();
		  if (answer.equals("Y"))
		  {
			  PrintWriter out = new PrintWriter("EmployeeTable.txt");
			  out.println("Employee_FName  Employee_LName  Employee_ID     Employee_Phone  StartDate");
			  result = stat.executeQuery("SELECT * FROM Employees");
			  while (result.next())
			  {
				  out.printf("%-16s%-16s%-16s%-16s%-16s", result.getString("EmployeeID"),
							result.getString("Employee_FName"), result.getString("Employee_LName"), result.getString("Employee_PhoneNo"), result.getString("StartDate"));
					out.println();
			  }
			  out.close();
		  }
		  System.out.print("\n\n");
		  
	}
	
	/**
	 * Removes an employee from the employees table
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void removeEmployees(Connection conn) throws SQLException {
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		System.out.println("Employee ID:");
		String update = "DELETE FROM Employees WHERE EmployeeID = ?";
		String input1 = input.next();
		pStat = conn.prepareStatement(update);
		pStat.setString(1, input1);
		pStat.executeUpdate();
	}
	
	/**
	 * Finds an employee by their full name
	 * @param conn a Connection object to access the database
	 * @param inputFirst the first name of the employee provided by the user
	 * @param inputLast the last name of the employee provided by the user
	 * @throws SQLException
	 */
	public static void findByEmployeeName(Connection conn, String inputFirst, String inputLast) throws SQLException {
		PreparedStatement pStat = null;
		String updateItems = "SELECT * FROM Employees WHERE Employee_FName = ? AND Employee_LName = ?";
		System.out.println("Employee_FName  Employee_LName  Employee_ID     Employee_Phone      StartDate");
		pStat = conn.prepareStatement(updateItems);
		pStat.setString(1, inputFirst);
		pStat.setString(2, inputLast);
		ResultSet result = pStat.executeQuery();
		while (result.next()) {
			System.out.printf("%-16s%-16s%-16s%-16s%-16s", result.getString("EmployeeID"),
					result.getString("Employee_FName"), result.getString("Employee_LName"), result.getString("Employee_PhoneNo"), result.getString("StartDate"));
			System.out.println();
		}
		System.out.print("\n\n");
	}

	/**
	 * Finds an employee by their ID
	 * @param conn a Connection object to access the database
	 * @param ID the ID of the employee provided by the user
	 * @throws SQLException
	 */
	public static void findByEmployeeID(Connection conn, String ID) throws SQLException {
		PreparedStatement pStat = null;
		String updateItems = "SELECT * FROM Employees WHERE EmployeeID = ?";
		System.out.println("Employee_Name   Employee_ID     Employee_Price      StartDate");
		pStat = conn.prepareStatement(updateItems);
		pStat.setString(1, ID);
		ResultSet result = pStat.executeQuery();
		while (result.next()) {
			System.out.printf("%-16s%-16s%-16s%-16s%-16s", result.getString("EmployeeID"),
					result.getString("Employee_FName"), result.getString("Employee_LName"), result.getString("Employee_PhoneNo"), result.getString("StartDate"));
			System.out.println();
		}
		System.out.print("\n\n");

	}

	/**
	 * Gets an employee's ID
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @throws SQLException
	 */
	public static void getEmployeeID(Connection conn, String inputID) throws SQLException {
		String update = "SELECT * FROM Employees WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("EmployeeID"));
	}
	
	/**
	 * Gets an employee's full name
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @throws SQLException
	 */
	public static void getEmployeeName(Connection conn, String inputID) throws SQLException {
		String update = "SELECT * FROM Employees WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.printf("%-16s%-16s", result.getString("Employee_FName"), result.getString("Employee_LName"));
		System.out.println();
	}

	/**
	 * Gets an employee's phone number
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @throws SQLException
	 */
	public static void getEmployeePhone(Connection conn, String inputID) throws SQLException {
		String update = "SELECT * FROM Employees WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("Employee_PhoneNo"));
	}

	/**
	 * Gets the employee's job start date
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @throws SQLException
	 */
	public static void getEmployeeStartDate(Connection conn, String inputID) throws SQLException {
		String update = "SELECT * FROM Employees WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("StartDate"));
	}

	/**
	 * Sets an employee's full name
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @param inputFirst the new first name of the employee provided by the user
	 * @param inputLast the new last name of the employee provided by the user
	 * @throws SQLException
	 */
	public static void setEmployeeName(Connection conn, String inputID, String newFirstName, String newLastName)
			throws SQLException {
		String update = "UPDATE Employees SET Employee_FName = ?, Employee_LName = ? WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, newFirstName);
		pStat.setString(2, newLastName);
		pStat.setString(3, inputID);
		pStat.executeUpdate();
	}

	/**
	 * Sets an employee's phone number
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @param newPhoneNo the new phone number provided by the user
	 * @throws SQLException
	 */
	public static void setEmployeePhone(Connection conn, String inputID, String newPhoneNo)throws SQLException {
		String update = "UPDATE Employees SET Employee_PhoneNo = ? WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, newPhoneNo);
		pStat.setString(2, inputID);
		pStat.executeUpdate();
		
	}

	/**
	 * Sets an employee's job start date
	 * @param conn a Connection object to access the database
	 * @param inputID the employee ID provided by the user to find an employee's record
	 * @param newStartDate the new start date provided by the user
	 * @throws SQLException
	 */
	public static void setEmployeeStartDate(Connection conn, String inputID, String newStartDate)throws SQLException {
		String update = "UPDATE Employees SET StartDate = ? WHERE EmployeeID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1, newStartDate);
		pStat.setString(2, inputID);
		pStat.executeUpdate();
	}
	
	/**
	 * Stores an employee's ID for reference by the employee's name
	 * @param conn a Connection object to access the database
	 * @param inputFirst the first name of the employee
	 * @param inputLast the last name of the employee
	 * @return the employee's ID to be stored for reference
	 * @throws SQLException
	 */
	public static String storeByEmployeeName(Connection conn, String inputFirst, String inputLast)throws SQLException {
		PreparedStatement pStat = null;
		  String update = "SELECT * FROM Employees WHERE Employee_FName = ? AND Employee_LName = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,inputFirst);
		  pStat.setString(2,inputLast);
		  ResultSet result = pStat.executeQuery();
		  result.next();
		  return result.getString("EmployeeID");
	}
	
}
