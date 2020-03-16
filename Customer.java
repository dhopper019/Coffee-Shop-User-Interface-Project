import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Customer {
	
	/**
	 * Adds a customer to the list of customers
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void addCustomer(Connection conn) throws SQLException
	{
		String updateCustomer = "INSERT INTO Customer VALUES (?,?,?,?,(SELECT COUNT(*) FROM Visit WHERE CustomerID = ?),0)";
		  PreparedStatement pStat = null;
		  Scanner input = new Scanner(System.in);
		  pStat = conn.prepareStatement(updateCustomer);
		  System.out.print("Customer ID: ");
		  String input1 = input.next();
		  System.out.print("Customer First Name: ");
		  String input2 = input.next();
		  System.out.print("Customer Last Name: ");
		  String input3 = input.next();
		  System.out.print("Customer Phone Number: ");
		  String input4 = input.next();
		  pStat.setString(1,input1);
		  pStat.setString(2,input2);
		  pStat.setString(3,input3);
		  pStat.setString(4,input4);
		  pStat.setString(5,input1);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
	}
	
	/**
	 * Prints a table listing all customers and their information
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void printCustomerTable(Connection conn) throws SQLException, FileNotFoundException
	{
		
		Scanner in = new Scanner(System.in);
		Statement stat = conn.createStatement();
		System.out.println("Customer ID     First Name      Last Name       Phone Number    No_Visits       Rewards Number");
		  ResultSet result = stat.executeQuery("SELECT * FROM Customer");
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16s%-16s%-16d%-1d",result.getString("CustomerID"),result.getString("CFirstName"),result.getString("CLastName"),result.getString("CPhone"),result.getInt("TotalVisits"),result.getInt("RewardsNumber"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
		  System.out.println("Would you like to print the results out to a file? (Y/N)");
		  String answer = in.next();
		  if (answer.equals("Y"))
		  {
			  PrintWriter out = new PrintWriter("CustomerTable.txt");
			  result = stat.executeQuery("SELECT * FROM Customer");
			  out.println("Customer ID     First Name      Last Name       Phone Number    No_Visits       Rewards Number");
			  while (result.next())
			  {
				  out.printf("%-16s%-16s%-16s%-16s%-16d%-1d",result.getString("CustomerID"),result.getString("CFirstName"),result.getString("CLastName"),result.getString("CPhone"),result.getInt("TotalVisits"),result.getInt("RewardsNumber"));
				  out.println();
			  }
			  out.close();
		  }
		  System.out.print("\n\n");
		  
	}
	
	/**
	 * Removes a customer from the list of customers
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void removeCustomer(Connection conn) throws SQLException
	{
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		System.out.println("Customer ID:");
		  String update = "DELETE FROM CustomerRewards WHERE CustomerID = ?";
		  String input1 = input.next();
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
		  update = "DELETE FROM Visit WHERE CustomerID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
		  update = "DELETE FROM Expenditure WHERE CustomerID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
		  update = "DELETE FROM Customer WHERE CustomerID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.executeUpdate();
	}
	
	/**
	 * Finds a customer by using the customer's full name
	 * @param conn a Connection object to access the database
	 * @param inputFirst the first name of the customer
	 * @param inputLast the last name of the customer
	 * @throws SQLException
	 */
	public static void findByCustomerName(Connection conn, String inputFirst, String inputLast) throws SQLException
	{
		PreparedStatement pStat = null;
		  String update = "SELECT * FROM Customer WHERE CFirstName = ? AND CLastName = ?";
		  System.out.println("Customer ID     First Name      Last Name       Phone Number    No_Visits       Rewards Number");
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,inputFirst);
		  pStat.setString(2,inputLast);
		  ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16s%-16s%-16d%-1d",result.getString("CustomerID"),result.getString("CFirstName"),result.getString("CLastName"),result.getString("CPhone"),result.getInt("TotalVisits"),result.getInt("RewardsNumber"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Stores a customer's ID by the customer's name provided by the user to be used in looking up specific information about a customer
	 * @param conn a Connection object to access the database
	 * @param inputFirst the first name of the customer
	 * @param inputLast the last name of the customer
	 * @return the customer's ID
	 * @throws SQLException
	 */
	public static String storeByCustomerName(Connection conn, String inputFirst, String inputLast) throws SQLException
	{
		PreparedStatement pStat = null;
		  String update = "SELECT * FROM Customer WHERE CFirstName = ? AND CLastName = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,inputFirst);
		  pStat.setString(2,inputLast);
		  ResultSet result = pStat.executeQuery();
		  result.next();
		  return result.getString("CustomerID");
	}
	
	/**
	 * Finds a customer record by a customer's ID
	 * @param conn a Connection object to access the database
	 * @param inputID the customer's ID provided by the user
	 * @throws SQLException
	 */
	public static void findByCustomerID(Connection conn, String inputID) throws SQLException
	{
		PreparedStatement pStat = null;
		  String update = "SELECT * FROM Customer WHERE CustomerID = ?";
		  System.out.println("Customer ID     First Name      Last Name       Phone Number    No_Visits       Rewards Number");
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,inputID);
		  ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16s%-16s%-16d%-1d",result.getString("CustomerID"),result.getString("CFirstName"),result.getString("CLastName"),result.getString("CPhone"),result.getInt("TotalVisits"),result.getInt("RewardsNumber"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Records an expenditure of the customer on a quantity of a specific type of item, and asks whether the customer wants to apply their discount if the customer is a rewards member
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void recordCustomerExpenditure(Connection conn) throws SQLException
	{
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		  String discount = "0";
		  System.out.print("Customer ID: ");
		  String customerID = input.next();
		  System.out.print("Item ID: ");
		  String itemID = input.next();
		  String update = "SELECT Item_Quantity FROM Items WHERE Item_ID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,itemID);
		  ResultSet result = pStat.executeQuery();
		  result.next();
		  int itemQ = 0;
		  while (true)
		  {
			  System.out.print("Item Quantity: ");
			  itemQ = input.nextInt();
			  //Reject the number ordered if it is greater than the amount in stock
			  if (itemQ <= result.getInt("Item_Quantity"))
			  {
				  break;
			  }
			  else
			  {
				  System.out.println("Error, the amount ordered exceeds the amount in stock: "+result.getInt("Item_Quantity"));
			  }
		  }
		  update = "SELECT Item_Price FROM Items WHERE Item_ID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,itemID);
		  result = pStat.executeQuery();
		  result.next();
		  Double expendAmtCalc = ((double) (itemQ)) * result.getDouble("Item_Price");
		  //If the customer is a rewards member, ask if the customer would like to apply the discount rate provided by the rewards program
		  if (Customer.isRewardsMember(conn, customerID))
		  {
			  System.out.println("This customer is in the rewards program.\n Do you want to apply a discount to this expenditure? (Y/N)");
			  String inputCommand = input.next();
			  if(inputCommand.equals("Y"))
			  {
				  update = "SELECT DiscountRate FROM CustomerRewards WHERE CustomerID = ?";
				  pStat = conn.prepareStatement(update);
				  pStat.setString(1,customerID);
				  pStat.executeQuery();
				  result = pStat.executeQuery();
				  result.next();
				  expendAmtCalc = expendAmtCalc * (1 - result.getDouble("DiscountRate"));
				  discount = result.getString("DiscountRate");
			  }
		  }
		  String expendAmt = String.valueOf(expendAmtCalc);
		  System.out.print("Expenditure Date: ");
		  String expendDate = input.next();
		  update = "INSERT INTO Expenditure VALUES (?,?,?,?)";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,customerID);
		  pStat.setString(2,expendAmt);
		  pStat.setString(3,expendDate);
		  pStat.setString(4,discount);
		  pStat.executeUpdate();
		  //Update the quantity of an item left in stock
		  update = "UPDATE Items SET Item_Quantity = Item_Quantity - ? WHERE Item_ID = ?";
		  pStat = conn.prepareStatement(update);
		  String itemQString = String.valueOf(itemQ);
		  pStat.setString(1,itemQString);
		  pStat.setString(2,itemID);
		  pStat.executeUpdate();
		  //Update the amount of an item sold
		  update = "UPDATE Items SET Number_Sold = Number_Sold + ? WHERE Item_ID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,itemQString);
		  pStat.setString(2,itemID);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
	}
	
	/**
	 * Prints a list of all expenditures by customers and information regarding the expenditures
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void printExpenditureTable(Connection conn) throws SQLException, FileNotFoundException
	{
		
		Scanner in = new Scanner(System.in);
		Statement stat = conn.createStatement();;
		Scanner input = new Scanner(System.in);
		System.out.println("Customer ID     Expenditure Amount      Expenditure Date        Discount Rate");
		  ResultSet result = stat.executeQuery("SELECT * FROM Expenditure");
		  while (result.next())
		  {
			  System.out.printf("%-16s%-24.2f%-24s%-16.2f",result.getString("CustomerID"),result.getDouble("Expend_Amt"),result.getDate("Expend_Date"),result.getDouble("DiscountRate"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
		  //Ask if the user would like the results printed to a text file called "ExpenditureTable.txt"
		  System.out.println("Would you like to print the results out to a file? (Y/N)");
		  String answer = in.next();
		  if (answer.equals("Y"))
		  {
			  PrintWriter out = new PrintWriter("ExpenditureTable.txt");
			  result = stat.executeQuery("SELECT * FROM Expenditure");
			  out.println("Customer Name   Expenditure Amount      Expenditure Date        Discount Rate");
			  while (result.next())
			  {
				  out.printf("%-16s%-24.2f%-24s%-16.2f",result.getString("CustomerID"),result.getDouble("Expend_Amt"),result.getDate("Expend_Date"),result.getDouble("DiscountRate"));
				  out.println();
			  }
			  out.close();
		  }
		  System.out.print("\n\n");
		  
	}
	
	/**
	 * Records a visit by a customer to the shop
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void recordCustomerVisit(Connection conn) throws SQLException
	{
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		String update = "INSERT INTO Visit VALUES (?,?)";
		  pStat = conn.prepareStatement(update);
		  System.out.print("CustomerID: ");
		  String input1 = input.next();
		  System.out.print("Visit date: ");
		  String input2 = input.next();
		  pStat.setString(1,input1);
		  pStat.setString(2,input2);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
		  update = "UPDATE Customer SET TotalVisits = (SELECT COUNT(*) FROM Visit WHERE CustomerID = ?) WHERE CustomerID = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input1);
		  pStat.setString(2,input1);
		  pStat.executeUpdate();
	}
	
	/**
	 * Prints a table listing all visits by all customers and information about the visit
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void printVisit(Connection conn) throws SQLException, FileNotFoundException
	{
		
		Scanner in = new Scanner(System.in);
		Statement stat = conn.createStatement();
		Scanner input = new Scanner(System.in);
		System.out.println("Customer ID     Visit_Date");
		  ResultSet result = stat.executeQuery("SELECT * FROM Visit");
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s",result.getString("CustomerID"),result.getDate("Visit_Date"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
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
	 * Gets the full name of a customer
	 * @param conn a Connection object to access the database
	 * @param inputID the customer's ID
	 * @throws SQLException
	 */
	public static void getCustomerName(Connection conn, String inputID) throws SQLException
	{
		String update = "SELECT * FROM Customer WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("CFirstName")+" "+result.getString("CLastName"));
	}
	
	/**
	 * Gets the ID of a customer
	 * @param conn a Connection object to access the database
	 * @param inputID the customer's ID
	 * @throws SQLException
	 */
	public static void getCustomerID(Connection conn, String inputID) throws SQLException
	{
		String update = "SELECT * FROM Customer WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("CustomerID"));
	}
	
	/**
	 * Gets the phone number of the customer
	 * @param conn a Connection object to access the database
	 * @param inputID the customer's ID
	 * @throws SQLException
	 */
	public static void getCustomerPhone(Connection conn, String inputID) throws SQLException
	{
		String update = "SELECT * FROM Customer WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("CPhone"));
	}
	
	/**
	 * Sets a new name for the customer
	 * @param conn a Connection object to access the database
	 * @param inputID the customer's ID
	 * @param newCustomerFirst the customer's new first name provided by the user
	 * @param newCustomerLast the customer's new last name provided by the user
	 * @throws SQLException
	 */
	public static void setCustomerName(Connection conn, String inputID, String newCustomerFirst, String newCustomerLast) throws SQLException
	{
		String update = "UPDATE Customer SET CFirstName = ?, CLastName = ? WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,newCustomerFirst);
		pStat.setString(2,newCustomerLast);
		pStat.setString(3,inputID);
		pStat.executeUpdate();
	}
	
	/**
	 * Sets a new phone number for the customer
	 * @param conn a Connection object to access the database
	 * @param inputID the customer's ID
	 * @param newPhoneNo the customer's new phone number provided by the user
	 * @throws SQLException
	 */
	public static void setCustomerPhone(Connection conn, String inputID, String newPhoneNo) throws SQLException
	{
		String update = "UPDATE Customer SET CPhone = ? WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,newPhoneNo);
		pStat.setString(2,inputID);
		pStat.executeUpdate();
	}
	
	/**
	 * Gets the total amount expended by a customer in a particular month on a particular year
	 * @param conn a Connection object to access the database
	 * @param inputID the customer ID provided by the user
	 * @param month the month of the expenditures to be selected
	 * @param year the year of the expenditures to be selected
	 * @throws SQLException
	 */
	public static void getCustomerExpendituresMonth(Connection conn, String inputID, String month, String year) throws SQLException
	{
		String update = "SELECT CustomerID, MONTH(Expend_Date), YEAR(Expend_Date), SUM(Expend_Amt) FROM Expenditure GROUP BY CustomerID, MONTH(Expend_Date), YEAR(Expend_Date) HAVING CustomerID = ? AND MONTH(Expend_Date) = ? AND YEAR(Expend_Date) = ?";
		System.out.println("Customer ID     Total Expenditures");
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		pStat.setString(2,month);
		pStat.setString(3,year);
		ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-24.2f",result.getString("CustomerID"),result.getDouble(4));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Gets the total amount expended by a customer on a particular year
	 * @param conn a Connection object to access the database
	 * @param inputID the customer ID provided by the user
	 * @param year the year of the expenditures to be selected
	 * @throws SQLException
	 */
	public static void getCustomerExpendituresYear(Connection conn, String inputID, String year) throws SQLException
	{
		String update = "SELECT CustomerID, YEAR(Expend_Date), SUM(Expend_Amt) FROM Expenditure GROUP BY CustomerID, YEAR(Expend_Date) HAVING CustomerID = ? AND YEAR(Expend_Date) = ?";
		System.out.println("Customer ID     Total Expenditures");
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		pStat.setString(2,year);
		ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-24.2f",result.getString("CustomerID"),result.getDouble(3));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Evaluates whether a customer is a member of the rewards program
	 * @param conn a Connection object to access the database
	 * @param inputID
	 * @return true if the customer is a rewards member or false if the customer is not a rewards member
	 * @throws SQLException
	 */
	public static boolean isRewardsMember(Connection conn, String inputID) throws SQLException
	{
		String update = "SELECT RewardsNumber FROM Customer WHERE CustomerID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,inputID);
		ResultSet result = pStat.executeQuery();
		result.next();
		if (result.getInt("RewardsNumber") == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
