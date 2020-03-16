import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//Note: You must use the SimpleDataSource.java as the data file, use database.properties file, and you must run with the program argument: src\database.properties
public class Manager {

	public static void main(String[] args) throws IOException, SQLException,
    ClassNotFoundException {
		if (args.length == 0)
	      {
	         System.out.println("Usage: java Manager propertiesFile");
	         System.exit(0);
	      }

	      SimpleDataSource.init(args[0]);
	      
	      
	      
	      try (Connection conn = SimpleDataSource.getConnection())
	      {
	    	  
	    	  Scanner input = new Scanner(System.in);
	    	  
	    	  Statement stat = conn.createStatement();
	    	  
	      try
	      {
	    	  stat.execute("CREATE TABLE Employees (EmployeeID VARCHAR(30), Employee_FName VARCHAR(30), Employee_LName VARCHAR(30), StartDate DATE, Employee_PhoneNo VARCHAR(30), CONSTRAINT Employee_PK PRIMARY KEY(EmployeeID))");
	    	  stat.execute("CREATE TABLE Customer (CustomerID Integer, CFirstName VARCHAR(255), CLastName VARCHAR(255), CPhone VARCHAR(255), TotalVisits Integer, RewardsNumber Integer NOT NULL CHECK(RewardsNumber in (0,1)), CONSTRAINT Customer_PK PRIMARY KEY(CustomerID))");
	    	  stat.execute("CREATE TABLE Items (Item_Name VARCHAR(30), Item_ID VARCHAR(30), Item_Price DOUBLE, Item_Quantity INTEGER,  Number_Sold Integer, CONSTRAINT Item_PK PRIMARY KEY(Item_ID))");
	    	  stat.execute("CREATE TABLE Expenditure (CustomerID Integer, Expend_Amt DOUBLE, Expend_Date DATE, DiscountRate Double)");
	    	  stat.execute("CREATE TABLE Visit (CustomerID Integer, Visit_Date DATE)");
	    	  stat.execute("CREATE TABLE CustomerRewards (CustomerID Integer, DateJoined Date, DiscountRate Double, CONSTRAINT CR_PK PRIMARY KEY(CustomerID), CONSTRAINT CR_FK FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID))");
	      }
	      catch (SQLException exception)
	      {
	    	  System.out.println("All tables have been created.");
	      }
	    	  
	    	  String inputCommand = "";
	    	  
	    	  PreparedStatement pStat;
	    	  
	    	  
	    	  //The main menu for the manager UI
	    	  while(true)
	    	  {
	    		  System.out.println("Select from the following options");
	    		  System.out.println("(Q) Quit");
	    		  System.out.println("(C) Customer Menu");
	    		  System.out.println("(RC) Rewards Customer Menu");
	    		  System.out.println("(E) Employee Menu");
	    		  System.out.println("(I) Item Menu");
	    		  inputCommand = input.next();
	    		  if (inputCommand.equals("Q"))
	    		  {
	    			  break;
	    		  }
	    		  else if (inputCommand.equals("C"))
	    		  {
	    			  //The main menu for the customer portion of the interface (includes interactions with Customer, Expenditure, Visit, and CustomerRewards tables).
		    	  while(true)
		    	  {
		    		  System.out.println("Select from the following options");
		    		  System.out.println("(Q) Quit");
		    		  System.out.println("(AC) Add a customer");
		    		  System.out.println("(FCN) Find a customer by name");
		    		  System.out.println("(FCID) Find a customer by ID");
		    		  System.out.println("(RCE) Record expenditures to a customer");
		    		  System.out.println("(RCV) Record a visit to a customer");
		    		  System.out.println("(RC) Remove customer from the table");
		    		  System.out.println("(PC) Print the entire customers table");
		    		  System.out.println("(PE) Print the entire expenditures table");
		    		  System.out.println("(PV) Print all visits");
		    		  System.out.println("(GME) Get the total expenditures for a customer in a month");
		    		  System.out.println("(GYE) Get the total expenditures for a customer in a year");
		    		  inputCommand = input.next();
		    		  if (inputCommand.equals("Q"))
		    		  {
		    			  break;
		    		  }
		    		  else if (inputCommand.equals("AC"))
		    		  {
		    			  Customer.addCustomer(conn);
		    		  }
		    		  else if (inputCommand.equals("PC"))
		    		  {
		    			  Customer.printCustomerTable(conn);
		    		  }
		    		  else if (inputCommand.equals("RC"))
		    		  {
		    			  Customer.removeCustomer(conn);
		    		  }
		    		  else if (inputCommand.equals("FCN"))
		    		  {
		    			  System.out.println("Customer First Name:");
		    			  String inputFirst = input.next();
		    			  System.out.println("Customer Last Name:");
		    			  String inputLast = input.next();
		    			  Customer.findByCustomerName(conn, inputFirst, inputLast);
		    			  String customerID = Customer.storeByCustomerName(conn, inputFirst, inputLast);
		    			  while (true)
		    			  {
		    				  //The menu for getting and setting values related to a specific customer when the customer name is provided.
		    				  System.out.println("Select from the following options for this customer");
		    				  System.out.println("(Q) Quit out of this customer");
		    				  System.out.println("(GCN) Get the customer's name");
		    				  System.out.println("(GCID) Get the customer's id");
		    				  System.out.println("(GCPN) Get the customer's phone number");
		    				  System.out.println("(SCN) Set the customer's name");
		    				  System.out.println("(SCPN) Set the customer's phone number");
		    				  String inputCommand2 = input.next();
		    				  if (inputCommand2.equals("Q"))
		    	    		  {
		    	    			  break;
		    	    		  }
		    	    		  else if (inputCommand2.equals("GCN"))
		    	    		  {
		    	    			  Customer.getCustomerName(conn, customerID);
		    	    		  }
		    	    		  else if (inputCommand2.equals("GCID"))
		    	    		  {
		    	    			  Customer.getCustomerID(conn, customerID);
		    	    		  }
		    	    		  else if (inputCommand2.equals("GCPN"))
		    	    		  {
		    	    			  Customer.getCustomerPhone(conn, customerID);
		    	    		  }
		    	    		  else if (inputCommand2.equals("SCN"))
		    	    		  {
		    	    			  System.out.println("New Customer First Name:");
		    	    			  String input2 = input.next();
		    	    			  System.out.println("New Customer Last Name:");
		    	    			  String input3 = input.next();
		    	    			  Customer.setCustomerName(conn, customerID, input2, input3);
		    	    		  }
		    	    		  else if (inputCommand2.equals("SCPN"))
		    	    		  {
		    	    			  System.out.println("New Customer Phone:");
		    	    			  String input2 = input.next();
		    	    			  Customer.setCustomerPhone(conn, customerID, input2);
		    	    		  }
		    			  }
		    		  }
		    		  else if (inputCommand.equals("FCID"))
		    		  {
		    			  System.out.println("Customer ID:");
		    			  String customerID = input.next();
		    			  Customer.findByCustomerID(conn, customerID);
		    			//The menu for getting and setting values related to a specific customer when the customer ID is provided.
		    			  while (true)
		    			  {
		    				  System.out.println("Select from the following options for this customer");
		    				  System.out.println("(Q) Quit out of this customer");
		    				  System.out.println("(GCN) Get the customer's name");
		    				  System.out.println("(GCID) Get the customer's id");
		    				  System.out.println("(GCPN) Get the customer's phone number");
		    				  System.out.println("(SCN) Set the customer's name");
		    				  System.out.println("(SCPN) Set the customer's phone number");
		    				  String inputCommand2 = input.next();
		    				  if (inputCommand2.equals("Q"))
		    	    		  {
		    	    			  break;
		    	    		  }
		    	    		  else if (inputCommand2.equals("GCN"))
		    	    		  {
		    	    			  Customer.getCustomerName(conn, customerID);
		    	    		  }
		    	    		  else if (inputCommand2.equals("GCID"))
		    	    		  {
		    	    			  Customer.getCustomerID(conn, customerID);
		    	    		  }
		    	    		  else if (inputCommand2.equals("GCPN"))
		    	    		  {
		    	    			  Customer.getCustomerPhone(conn, customerID);
		    	    		  }
		    	    		  else if (inputCommand2.equals("SCN"))
		    	    		  {
		    	    			  System.out.println("New Customer First Name:");
		    	    			  String input2 = input.next();
		    	    			  System.out.println("New Customer Last Name:");
		    	    			  String input3 = input.next();
		    	    			  Customer.setCustomerName(conn, customerID, input2, input3);
		    	    		  }
		    	    		  else if (inputCommand2.equals("SCPN"))
		    	    		  {
		    	    			  System.out.println("New Customer Phone:");
		    	    			  String input2 = input.next();
		    	    			  Customer.setCustomerPhone(conn, customerID, input2);
		    	    		  }
		    			  }
		    		  }
		    		  else if (inputCommand.equals("RCE"))
		    		  {
		    			  Customer.recordCustomerExpenditure(conn);
		    		  }
		    		  else if (inputCommand.equals("PE"))
		    		  {
		    			  Customer.printExpenditureTable(conn);
		    		  }
		    		  else if (inputCommand.equals("RCV"))
		    		  {
		    			  Customer.recordCustomerVisit(conn);
		    		  }
		    		  else if (inputCommand.equals("PV"))
		    		  {
		    			  Customer.printVisit(conn);
		    		  }
		    		  else if (inputCommand.equals("GME"))
		    		  {
		    			  System.out.println("Customer ID:");
		    			  String input1 = input.next();
		    			  System.out.println("Number of the Month:");
		    			  String input2 = input.next();
		    			  System.out.println("Number of the Year:");
		    			  String input3 = input.next();
		    			  Customer.getCustomerExpendituresMonth(conn, input1, input2, input3);
		    		  }
		    		  else if (inputCommand.equals("GYE"))
		    		  {
		    			  System.out.println("Customer ID:");
		    			  String input1 = input.next();
		    			  System.out.println("Number of the Year:");
		    			  String input2 = input.next();
		    			  Customer.getCustomerExpendituresYear(conn, input1, input2);
		    		  }
		    	  }
	    		  }
	    		  else if (inputCommand.equals("RC"))
	    		  {
	    			  //The main menu for the customer rewards part of the interface.
		    		  while(true)
		    		  {
		    		  System.out.println("Select from the following options");
				      System.out.println("(Q) Quit");
				      System.out.println("(ARC) Add a customer to the rewards program");
				      System.out.println("(FRCN) Find a rewards customer by name");
				      System.out.println("(FRCID) Find a rewards customer by ID");
				      System.out.println("(RRC) Remove a rewards customer");
				      System.out.println("(PRC) Print the table of customer rewards");
				      System.out.println("(GRCD) Get the rewards customer discount of a customer");
				      System.out.println("(SRCD) Set the rewards customer discount of a customer");
				      inputCommand = input.next();
			    		  if (inputCommand.equals("Q"))
			    		  {
			    			  break;
			    		  }
				          else if (inputCommand.equals("ARC"))
			    		  {
			    			  RewardsProgram.addRewardsCustomer(conn);
			    		  }
			    		  else if (inputCommand.equals("FRCN"))
			    		  {
			    			  System.out.println("Customer First Name:");
			    			  String inputFirst = input.next();
			    			  System.out.println("Customer Last Name:");
			    			  String inputLast = input.next();
			    			  RewardsProgram.findRewardsCustomerbyName(conn, inputFirst, inputLast);
			    		  }
			    		  else if (inputCommand.equals("FRCID"))
			    		  {
			    			  System.out.println("Customer ID:");
			    			  String inputID = input.next();
			    			  RewardsProgram.findRewardsCustomerbyID(conn, inputID);
			    		  }
			    		  else if (inputCommand.equals("RRC"))
			    		  {
			    			  RewardsProgram.removeRewardsCustomer(conn);
			    		  }
			    		  else if (inputCommand.equals("PRC"))
			    		  {
			    			  RewardsProgram.printCustomerRewardsTable(conn);
			    		  }
			    		  else if (inputCommand.equals("GRCD"))
			    		  {
			    			  System.out.println("Customer ID:");
			    			  String inputID = input.next();
			    			  RewardsProgram.getRewardsCustomerDiscount(conn, inputID);
			    		  }
			    		  else if (inputCommand.equals("SRCD"))
			    		  {
			    			  System.out.println("Customer ID:");
			    			  String inputID = input.next();
			    			  System.out.println("New Discount:");
			    			  String newID = input.next();
			    			  RewardsProgram.setRewardsCustomerDiscount(conn, inputID, newID);
			    		  }
	    			  }
		    	  }
	    		  else if (inputCommand.equals("E"))
	    		  {
	    			  //The main menu for the employee portion of the interface.
	    			  while(true)
	    	    	  {
	    	    		  System.out.println("Select from the following options");
	    	    		  System.out.println("(Q) Quit");
	    	    		  System.out.println("(AE) Add an Employee");
	    	    		  System.out.println("(FEN) Find an employee by name");
	    	    		  System.out.println("(FEID) Find an employee by ID");
	    	    		  System.out.println("(RE) Remove employee from the table");
	    	    		  System.out.println("(PE) Print the entire employees table");
	    	    		  inputCommand = input.next();
	    	    		  if (inputCommand.equals("Q"))
	    	    		  {
	    	    			  break;
	    	    		  }
	    	    		  else if (inputCommand.equals("AE"))
	    	    		  {
	    	    			  Employees.addEmployee(conn);
	    	    		  }
	    	    		  else if (inputCommand.equals("PE"))
	    	    		  {
	    	    			  Employees.printEmployeeTable(conn);
	    	    		  }
	    	    		  else if (inputCommand.equals("RE"))
	    	    		  {
	    	    			  Employees.removeEmployees(conn);
	    	    		  }
	    	    		  else if (inputCommand.equals("FEN"))
	    	    		  {
	    	    			  System.out.println("Employee First Name:");
	    	    			  String input1 = input.next();
	    	    			  System.out.println("Employee Last Name:");
	    	    			  String input2 = input.next();
	    	    			  Employees.findByEmployeeName(conn, input1, input2);
	    	    			  String employeeID = Employees.storeByEmployeeName(conn, input1, input2);
	    	    			  //The menu for getting and setting values related to an employee when the employee name is provided.
	    	    			  while (true)
	    	    			  {
	    	    				  System.out.println("Select from the following options for this employee");
	    	    				  System.out.println("(Q) Quit out of this employee");
	    	    				  System.out.println("(GEN) Get the employee's name");
	    	    				  System.out.println("(GEID) Get the employee's id");
	    	    				  System.out.println("(GEP) Get the employee's phone number");
	    	    				  System.out.println("(GED) Get the employee's start date");
	    	    				  System.out.println("(SEN) Set the employee's name");
	    	    				  System.out.println("(SEP) Set the employee's phone number");
	    	    				  System.out.println("(SED) Set the employee's start date");
	    	    				  String inputCommand2 = input.next();
	    	    				  if (inputCommand2.equals("Q"))
	    	    	    		  {
	    	    	    			  break;
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GEN"))
	    	    	    		  {
	    	    	    			 Employees.getEmployeeName(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GEID"))
	    	    	    		  {
	    	    	    			  Employees.getEmployeeID(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GEP"))
	    	    	    		  {
	    	    	    			  Employees.getEmployeePhone(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GED"))
	    	    	    		  {
	    	    	    			  Employees.getEmployeeStartDate(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SEN"))
	    	    	    		  {
	    	    	    			  System.out.println("New Employee First Name:");
	    	    	    			  String inputFirst = input.next();
	    	    	    			  System.out.println("New Employee Last Name:");
	    	    	    			  String inputLast = input.next();
	    	    	    			  Employees.setEmployeeName(conn, employeeID, inputFirst, inputLast);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SEP"))
	    	    	    		  {
	    	    	    			  System.out.println("New Employee Phone Number:");
	    	    	    			  String inputPhone = input.next();
	    	    	    			  Employees.setEmployeePhone(conn, employeeID, inputPhone);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SED"))
	    	    	    		  {
	    	    	    			  System.out.println("New Employee Start Date:");
	    	    	    			  String inputDate = input.next();
	    	    	    			  Employees.setEmployeeStartDate(conn, employeeID, inputDate);
	    	    	    		  }
	    	    			  }
	    	    		  }
	    	    		  else if (inputCommand.equals("FEID"))
	    	    		  {
	    	    			  System.out.println("Employee ID:");
	    	    			  String employeeID = input.next();
	    	    			  Employees.findByEmployeeID(conn, employeeID);
	    	    			//The menu for getting and setting values related to an employee when the employee ID is provided.
	    	    			  while (true)
	    	    			  {
	    	    				  System.out.println("Select from the following options for this employee");
	    	    				  System.out.println("(Q) Quit out of this employee");
	    	    				  System.out.println("(GEN) Get the employee's name");
	    	    				  System.out.println("(GEID) Get the employee's id");
	    	    				  System.out.println("(GEP) Get the employee's phone number");
	    	    				  System.out.println("(GED) Get the employee's start date");
	    	    				  System.out.println("(SEN) Set the employee's name");
	    	    				  System.out.println("(SEP) Set the employee's phone number");
	    	    				  System.out.println("(SED) Set the employee's start date");
	    	    				  String inputCommand2 = input.next();
	    	    				  if (inputCommand2.equals("Q"))
	    	    	    		  {
	    	    	    			  break;
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GEN"))
	    	    	    		  {
	    	    	    			 Employees.getEmployeeName(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GEID"))
	    	    	    		  {
	    	    	    			  Employees.getEmployeeID(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GEP"))
	    	    	    		  {
	    	    	    			  Employees.getEmployeePhone(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GED"))
	    	    	    		  {
	    	    	    			  Employees.getEmployeeStartDate(conn, employeeID);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SEN"))
	    	    	    		  {
	    	    	    			  System.out.println("New Employee First Name:");
	    	    	    			  String inputFirst = input.next();
	    	    	    			  System.out.println("New Employee Last Name:");
	    	    	    			  String inputLast = input.next();
	    	    	    			  Employees.setEmployeeName(conn, employeeID, inputFirst, inputLast);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SEP"))
	    	    	    		  {
	    	    	    			  System.out.println("New Employee Phone Number:");
	    	    	    			  String inputPhone = input.next();
	    	    	    			  Employees.setEmployeePhone(conn, employeeID, inputPhone);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SED"))
	    	    	    		  {
	    	    	    			  System.out.println("New Employee Start Date:");
	    	    	    			  String inputDate = input.next();
	    	    	    			  Employees.setEmployeeStartDate(conn, employeeID, inputDate);
	    	    	    		  }
	    	    			  }
	    	    		  }
	    	    		  
	    	    		  else if (inputCommand.equals("PE"))
	    	    		  {
	    	    			  Employees.printEmployeeTable(conn);
	    	    		  }
	    	    	  }
	    		  }
	    		  else if (inputCommand.equals("I"))
	    		  {
	    			  //The main menu for the items portion of the interface.
	    			  while(true)
	    	    	  {
	    	    		  System.out.println("Select from the following options");
	    	    		  System.out.println("(Q) Quit");
	    	    		  System.out.println("(AI) Add an Item");
	    	    		  System.out.println("(FIN) Find an Item by name");
	    	    		  System.out.println("(FIID) Find an Item by ID");
	    	    		  System.out.println("(AIQ) Add Item Quantity");
	    	    		  System.out.println("(RI) Remove Item from the table");
	    	    		  System.out.println("(PI) Print the entire Items table");
	    	    		  inputCommand = input.next();
	    	    		  if (inputCommand.equals("Q"))
	    	    		  {
	    	    			  break;
	    	    		  }
	    	    		  else if (inputCommand.equals("AI"))
	    	    		  {
	    	    			  Items.addItems(conn);
	    	    		  }
	    	    		  else if (inputCommand.equals("PI"))
	    	    		  {
	    	    			  Items.printItemsTable(conn);
	    	    		  }
	    	    		  else if (inputCommand.equals("RI"))
	    	    		  {
	    	    			  Items.removeItems(conn);
	    	    		  }
	    	    		  else if (inputCommand.equals("FIN"))
	    	    		  {
	    	    			  System.out.println("Item Name:");
	    	    			  String input1 = input.next();
	    	    			  Items.findByItem_Name(conn, input1);
	    	    			  Items.storeByItemName(conn, input1);
	    	    			  //The menu for getting and setting values related to a specific item when an item name is provided.
	    	    			  while (true)
	    	    			  {
	    	    				  System.out.println("Select from the following options for this item");
	    	    				  System.out.println("(Q) Quit out of this item");
	    	    				  System.out.println("(GIN) Get the item's name");
	    	    				  System.out.println("(GIID) Get the item's id");
	    	    				  System.out.println("(GIP) Get the item's price");
	    	    				  System.out.println("(GIS) Get the number of items sold");
	    	    				  System.out.println("(SIN) Set the item's name");
	    	    				  System.out.println("(SIP) Set the item's price");
	    	    				  String inputCommand2 = input.next();
	    	    				  if (inputCommand2.equals("Q"))
	    	    	    		  {
	    	    	    			  break;
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIN"))
	    	    	    		  {
	    	    	    			  Items.getItem_Name(conn, input1);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIID"))
	    	    	    		  {
	    	    	    			  Items.getItem_ID(conn, input1);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIP"))
	    	    	    		  {
	    	    	    			  Items.getItem_Price(conn, input1);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SIN"))
	    	    	    		  {
	    	    	    			  System.out.println("New Item Name:");
	    	    	    			  String input2 = input.next();
	    	    	    			  Items.setItem_Name(conn, input1, input2);
	    	    	    			  input1 = input2;
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SIP"))
	    	    	    		  {
	    	    	    			  System.out.println("New Item price:");
	    	    	    			  String input2 = input.next();
	    	    	    			  Items.setItem_Price(conn, input1, input2);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIS"))
	    	    	    		  {
	    	    	    			  Items.getItem_Sold(conn, input1);
	    	    	    		  }
	    	    			  }
	    	    		  }
	    	    		  else if (inputCommand.equals("FIID"))
	    	    		  {
	    	    			  System.out.println("Item ID:");
	    	    			  String input1 = input.next();
	    	    			  Items.findByItem_ID(conn, input1);
	    	    			//The menu for getting and setting values related to a specific item when an item ID is provided.
	    	    			  while (true)
	    	    			  {
	    	    				  System.out.println("Select from the following options for this item");
	    	    				  System.out.println("(Q) Quit out of this item");
	    	    				  System.out.println("(GIN) Get the item's name");
	    	    				  System.out.println("(GIID) Get the item's id");
	    	    				  System.out.println("(GIP) Get the item's price");
	    	    				  System.out.println("(GIS) Get the number of items sold");
	    	    				  System.out.println("(SIN) Set the item's name");
	    	    				  System.out.println("(SIP) Set the item's price");
	    	    				  String inputCommand2 = input.next();
	    	    				  if (inputCommand2.equals("Q"))
	    	    	    		  {
	    	    	    			  break;
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIN"))
	    	    	    		  {
	    	    	    			  Items.getItem_Name(conn, input1);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIID"))
	    	    	    		  {
	    	    	    			  Items.getItem_ID(conn, input1);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIP"))
	    	    	    		  {
	    	    	    			  Items.getItem_Price(conn, input1);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SIN"))
	    	    	    		  {
	    	    	    			  System.out.println("New Item Name:");
	    	    	    			  String input2 = input.next();
	    	    	    			  Items.setItem_Name(conn, input1, input2);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("SIP"))
	    	    	    		  {
	    	    	    			  System.out.println("New Item Price:");
	    	    	    			  String input2 = input.next();
	    	    	    			  Items.setItem_Price(conn, input1, input2);
	    	    	    		  }
	    	    	    		  else if (inputCommand2.equals("GIS"))
	    	    	    		  {
	    	    	    			  Items.getItem_Sold(conn, input1);
	    	    	    		  }
	    	    			  }
	    	    		  }
	    	    		  
	    	    		  else if (inputCommand.equals("AIQ"))
	    	    		  {
	    	    			  Items.addItemQuantity(conn);
	    	    		  }
	    	    	  }
	    		  }
	    	  }
	      }

	}

}
