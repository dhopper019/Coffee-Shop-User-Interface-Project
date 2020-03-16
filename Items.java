import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Items {
	
	/**
	 * Adds an item to the list of items
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void addItems(Connection conn) throws SQLException
	{
		String updateItems = "INSERT INTO Items VALUES (?,?,?,0,0)";
		  PreparedStatement pStat = null;
		  Scanner input = new Scanner(System.in);
		  pStat = conn.prepareStatement(updateItems);
		  System.out.print("Item name: ");
		  String Item_Name = input.next();
		  System.out.print("Item ID: ");
		  String Item_ID = input.next();
		  System.out.print("Item_Price: ");
		  String Item_Price = input.next();
		  pStat.setString(1,Item_Name);
		  pStat.setString(2,Item_ID);
		  pStat.setString(3,Item_Price);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
	}
	
	/**
	 * Prints all items and their information from the list of items
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public static void printItemsTable(Connection conn) throws SQLException, FileNotFoundException
	{
		
		Scanner in = new Scanner(System.in);
		Statement stat = conn.createStatement();
		System.out.println("Item_Name       Item_ID         Item_Price      Item_Quantity   Number_Sold");
		  ResultSet result = stat.executeQuery("SELECT * FROM Items");
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16.2f%-16s%-16s",result.getString("Item_Name"),result.getString("Item_ID"),result.getDouble("Item_Price"),result.getString("Item_Quantity"),result.getString("Number_Sold"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
		  System.out.println("Would you like to print the results out to a file? (Y/N)");
		  String answer = in.next();
		  if (answer.equals("Y"))
		  {
			  PrintWriter out = new PrintWriter("ItemsTable.txt");
			  result = stat.executeQuery("SELECT * FROM Items");
			  out.println("Item_Name       Item_ID         Item_Price      Item_Quantity   Number_Sold");
			  while (result.next())
			  {
				  out.printf("%-16s%-16s%-16.2f%-16s%-16s",result.getString("Item_Name"),result.getString("Item_ID"),result.getDouble("Item_Price"),result.getString("Item_Quantity"),result.getString("Number_Sold"));
				  out.println();
			  }
			  out.close();
		  }
		  System.out.print("\n\n");
		  
	}
	
	/**
	 * Removes an item from the list of items
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void removeItems(Connection conn) throws SQLException
	{
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		System.out.println("Item_ID:");
		  String updateItems = "DELETE FROM Items WHERE Item_ID = ?";
		  String Item_Name = input.next();
		  pStat = conn.prepareStatement(updateItems);
		  pStat.setString(1,Item_Name);
		  pStat.executeUpdate();
	}
	
	/**
	 * Finds an item by its name
	 * @param conn a Connection object to access the database
	 * @param name
	 * @throws SQLException
	 */
	public static void findByItem_Name(Connection conn, String name) throws SQLException
	{
		PreparedStatement pStat = null;
		  String updateItems = "SELECT * FROM Items WHERE Item_Name = ?";
		  String Item_Name = name;
		  System.out.println("Item_Name       Item_ID         Item_Price      Item_Quantity   Number_Sold");
		  pStat = conn.prepareStatement(updateItems);
		  pStat.setString(1,Item_Name);
		  ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16.2f%-16s%-16s",result.getString("Item_Name"),result.getString("Item_ID"),result.getDouble("Item_Price"),result.getString("Item_Quantity"),result.getString("Number_Sold"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Finds an item by its ID
	 * @param conn a Connection object to access the database
	 * @param name
	 * @throws SQLException
	 */
	public static void findByItem_ID(Connection conn, String name) throws SQLException
	{
		PreparedStatement pStat = null;
		  String updateItems = "SELECT * FROM Items WHERE Item_ID = ?";
		  String Item_ID = name;
		  System.out.println("Item_Name       Item_ID         Item_Price      Item_Quantity   Number_Sold");
		  pStat = conn.prepareStatement(updateItems);
		  pStat.setString(1,Item_ID);
		  ResultSet result = pStat.executeQuery();
		  while (result.next())
		  {
			  System.out.printf("%-16s%-16s%-16.2f%-16s%-16s",result.getString("Item_Name"),result.getString("Item_ID"),result.getDouble("Item_Price"),result.getString("Item_Quantity"),result.getString("Number_Sold"));
			  System.out.println();
		  }
		  System.out.print("\n\n");
	}
	
	/**
	 * Adds a quantity of an item to its stock
	 * @param conn a Connection object to access the database
	 * @throws SQLException
	 */
	public static void addItemQuantity(Connection conn) throws SQLException
	{
		PreparedStatement pStat = null;
		Scanner input = new Scanner(System.in);
		String update = "UPDATE Items SET Item_Quantity = Item_Quantity + ? WHERE Item_ID = ?";
		  pStat = conn.prepareStatement(update);
		  System.out.print("Item_ID: ");
		  String input1 = input.next();
		  System.out.print("Quantity: ");
		  String input2 = input.next();
		  pStat.setString(2,input1);
		  pStat.setString(1,input2);
		  pStat.executeUpdate();
		  System.out.print("\n\n");
	}
	
	/**
	 * Gets the name of the item
	 * @param conn a Connection object to access the database
	 * @param column retrieves the column of which the input argument can be used on to identify the record
	 * @param input a value to uniquely identify the record desired
	 * @throws SQLException
	 */
	public static void getItem_Name(Connection conn, String input) throws SQLException
	{
		String update = "SELECT * FROM Items WHERE Item_ID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,input);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("Item_Name"));
	}
	
	/**
	 * Gets the ID of the item
	 * @param conn a Connection object to access the database
	 * @param column retrieves the column of which the input argument can be used on to identify the record
	 * @param input a value to uniquely identify the record desired
	 * @throws SQLException
	 */
	public static void getItem_ID(Connection conn, String input) throws SQLException
	{
		String update = "SELECT * FROM Items WHERE Item_ID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,input);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getString("Item_ID"));
	}
	
	/**
	 * Gets the price of the item
	 * @param conn a Connection object to access the database
	 * @param column retrieves the column of which the input argument can be used on to identify the record
	 * @param input a value to uniquely identify the record desired
	 * @throws SQLException
	 */
	public static void getItem_Price(Connection conn, String input) throws SQLException
	{
		String update = "SELECT * FROM Items WHERE Item_ID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,input);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getDouble("Item_Price"));
	}
	
	/**
	 * Sets the name of the Item
	 * @param conn a Connection object to access the database
	 * @param column retrieves the column of which the input argument can be used on to identify the record
	 * @param input a value to uniquely identify the record desired
	 * @param newItemName an item name provided by the user to replace the old item name
	 * @throws SQLException
	 */
	public static void setItem_Name(Connection conn, String input, String newItemName) throws SQLException
	{
		String update = "UPDATE Items SET Item_Name = ? WHERE Item_ID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,newItemName);
		pStat.setString(2,input);
		pStat.executeUpdate();
	}
	
	/**
	 * Sets the price of the item
	 * @param conn a Connection object to access the database
	 * @param column retrieves the column of which the input argument can be used on to identify the record
	 * @param input a value to uniquely identify the record desired
	 * @param newItemPrice a price provided by the user to replace the old price
	 * @throws SQLException
	 */
	public static void setItem_Price(Connection conn, String input, String newItemPrice) throws SQLException
	{
		String update = "UPDATE Items SET Item_Price = ? WHERE Item_ID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,newItemPrice);
		pStat.setString(2,input);
		pStat.executeUpdate();
	}
	
	/**
	 * Gets the quantity of the item sold
	 * @param conn a Connection object to access the database
	 * @param column retrieves the column of which the input argument can be used on to identify the record
	 * @param input a value to uniquely identify the record desired
	 * @throws SQLException
	 */
	public static void getItem_Sold(Connection conn, String input) throws SQLException
	{
		String update = "SELECT * FROM Items WHERE Item_ID = ?";
		PreparedStatement pStat = conn.prepareStatement(update);
		pStat.setString(1,input);
		ResultSet result = pStat.executeQuery();
		result.next();
		System.out.println(result.getInt("Number_Sold"));
	}

	public static String storeByItemName(Connection conn, String input) throws SQLException {
		PreparedStatement pStat = null;
		  String update = "SELECT * FROM Items WHERE Item_Name = ?";
		  pStat = conn.prepareStatement(update);
		  pStat.setString(1,input);
		  ResultSet result = pStat.executeQuery();
		  result.next();
		  return result.getString("Item_ID");
	}
	
}
