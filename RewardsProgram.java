import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RewardsProgram {
	
	/**
	 * Adds a customer to the rewards program
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void addRewardsCustomer(Connection conn) throws SQLException
	{
		String update = "INSERT INTO CustomerRewards VALUES (?,?,?)";
		  PreparedStatement pStat = null;
		  Scanner input = new Scanner(System.in);
		  pStat = conn.prepareStatement(update);
		  System.out.print("Customer ID: ");
		  String input1 = input.next();
		  System.out.print("Date Joined: ");
		  String input2 = input.next();
		  System.out.print("Discount Rate: ");
		  String input3 = input.next();
		  pStat.setString(1,input1);
		  pStat.setString(2,input2);
		  pStat.setString(3,input3);
		  pStat.executeUpdate();
		  update = "UPDATE Customer SET RewardsNumber = 1 WHERE CustomerID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
	}
	
	/**
	 * Finds a rewards customer by using their ID
	 * @param conn a Connection object to access the database
	 * @param inputID the ID of the customer provided by the user
	 * @throws SQLException
	 */
	public static void findRewardsCustomerbyID(Connection conn, String inputID) throws SQLException
	{
		PreparedStatement pStat = null;
		  String update = "SELECT * FROM CustomerRewards WHERE CustomerID = ?";
		  String input1 = inputID;
		  System.out.println("Customer ID     DiscountRate    DateJoined");
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16s",result.getString("CustomerID"),result.getBigDecimal("DiscountRate"),result.getDate("DateJoined"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Finds a rewards customer by using their full name
	 * @param conn a Connection object to access the database
	 * @param inputFirst the first name of the customer provided by the user
	 * @param inputLast the last name of the customer provided by the user
	 * @throws SQLException
	 */
	public static void findRewardsCustomerbyName(Connection conn, String inputFirst, String inputLast) throws SQLException
	{
		PreparedStatement pStat = null;
		  String update = "SELECT C.CustomerID, R.DiscountRate, R.DateJoined FROM CustomerRewards R, Customer C WHERE C.CustomerID = R.CustomerID AND C.CFirstName = ? AND C.CLastName = ?";
		  System.out.println("Customer ID     DiscountRate    DateJoined");
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,inputFirst);
		  pStat.setString(2,inputLast);
		  ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16.2f%-16s",result.getString("CustomerID"),result.getDouble("DiscountRate"),result.getDate("DateJoined"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Removes a rewards customer from the rewards program
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void removeRewardsCustomer(Connection conn) throws SQLException
	{
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		System.out.println("Customer ID:");
		  String update = "DELETE FROM CustomerRewards WHERE CustomerID = ?";
		  String input1 = input.next();
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
		  update = "UPDATE Customer SET RewardsNumber = 0 WHERE CustomerID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
	}
	
	/**
	 * Prints a table of all the rewards customers and their information
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void printCustomerRewardsTable(Connection conn) throws SQLException, FileNotFoundException
	{
		
		Scanner in = new Scanner(System.in);
		Statement stat = conn.createStatement();
		System.out.println("Customer ID     Discount Rate   Date Joined");
		  ResultSet result = stat.executeQuery("SELECT * FROM CustomerRewards");
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16.2f%-16s",result.getString("CustomerID"),result.getDouble("DiscountRate"),result.getDate("DateJoined"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
		  //Ask the user to print results to a text file called "Visit.txt"
		  System.out.println("Would you like to print the results out to a file? (Y/N)");
		  String answer = in.next();
		  if (answer.equals("Y"))
		  {
			  PrintWriter out = new PrintWriter("Visit.txt");
			  result = stat.executeQuery("SELECT * FROM Visit");
			  out.println("Customer ID     Visit_Date");
			  while (result.next())
			  {
				  out.printf("%-16s%-16s",result.getString("CustomerID"),result.getDate("Visit_Date"));
				  out.println();
			  }
			  out.close();
		  }
		  System.out.print("\n\n");
		  
	}
	
	/**
	 * Gets the discount rate of a rewards customer
	 * @param conn a Connection object to access the database
	 * @param inputID the ID of the customer which is used to access the rewards record of the customer
	 * @throws SQLException
	 */
	public static void getRewardsCustomerDiscount(Connection conn, String inputID) throws SQLException
	{
		String update = "SELECT CustomerID, DiscountRate FROM CustomerRewards WHERE CustomerID = ?";
		System.out.println("Customer ID     DiscountRate");
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16.2f",result.getString("CustomerID"),result.getDouble(2));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Sets the discount rate of a rewards customer
	 * @param conn a Connection object to access the database
	 * @param inputID the ID of the customer which is used to access the rewards record of the customer
	 * @param newDiscountRate the new discount rate of the rewards customer provided by the user
	 * @throws SQLException
	 */
	public static void setRewardsCustomerDiscount(Connection conn, String inputID, String newDiscountRate) throws SQLException
	{
		String update = "UPDATE CustomerRewards SET DiscountRate = ? WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,newDiscountRate);
		pStat.setString(2,inputID);
		pStat.executeUpdate();
	}
}
