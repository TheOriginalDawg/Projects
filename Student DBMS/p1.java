import java.util.*;
import java.io.*;
import java.sql.*;
import java.awt.*;
import java.lang.*;

class p1 {

  private static String driver;
  private static String url;
  private static String username;
  private static String password;



   public static String name;
   public static int ID;
   public static char gender;
   public static int acc_num;
   public static int age;
   public static int account_balance;
   public static char acc_type;
   public static int pin;
  
// java p1 filename
  public static void main(String argv[]) {


    Scanner choice = new Scanner(System.in);
  	int select = 0;


    if (argv.length != 1) {
      System.out.println("Need database properties filename");
    } else 
      {
      init(argv[0]);

      try {
     /*   Class.forName("com.ibm.db2.jcc.DB2Driver");                                                                  //load the driver
        //Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
        Connection con = DriverManager.getConnection("jdbc:mysql://hostname/databaseName", "stuff from data base");                  //Create the connection
        Statement stmt = con.createStatement();                                                 //Create a statement
        String query = "SELECT FIRSTNME, LASTNAME, EDLEVEL, SALARY FROM EMPLOYEE";              //The query to run
        ResultSet rs = stmt.executeQuery(query);                                                //Executing the query and storing the results in a Result Set
       */ 


                                    
   
	   //Loop through the menu until a selection is made
      System.out.println("Welcome to Bank of K");
	  System.out.println("Please select from the following:");
	  System.out.println("1. Open a new account");
	  System.out.println("2. Login to an existing account");
	  System.out.println("3. Exit");

		select = choice.nextInt();  

	                      
	  switch (select)
	   {

		case 1: Menu(argv);
		case 2: Menu2(argv);
		case 3: System.out.println("Leaving session.....");
				System.exit(0);
		default: System.out.println("Invalid selection");
				main(argv);
	    }
	
/*
		rs.close();                                                                             //Close the result set after we are done with the result set
        stmt.close();                                                                           //Close the statement after we are done with the statement
        con.close();
        */
	}                                                                            //Close the connection after we are done with everything
	
       catch (Exception e) {
        System.out.println("Exception in main()");
        e.printStackTrace();
      }
  }
    
  }//main




public static void Menu(String gbtm[])
{

	Scanner sc = new Scanner(System.in);
	String fname = "";
	String lname = "";
	String temp_read;
	char status = 'A';


 try {
 		Class.forName(driver);  																//load the driver
 		Connection conn = DriverManager.getConnection(url, username, password);                  //Create the connection

 		String query1 = "INSERT into P1.ACCOUNT (id,balance,type,status) values (?,?,?,?)"; 		//The querty to run
        PreparedStatement stmt1 = conn.prepareStatement(query1);                                //Create a statement
        
        String query = "INSERT into P1.CUSTOMER (name,gender,age,pin) values (?,?,?,?)"; 	   	//The query to run
      	PreparedStatement stmt = conn.prepareStatement(query);                                 //Executing the query

      	String getID = "Select id from p1.CUSTOMER WHERE pin = ?";

      	PreparedStatement newstmt = conn.prepareStatement(getID);

      	ResultSet rs = null;
															 
                                                                          
 
	System.out.println("Welcome new memeber!");
	System.out.println("");
	System.out.print("Please Enter your first name: ");
	fname = sc.nextLine();
	System.out.println(" ");
	System.out.print("Please enter your last name: ");
	lname = sc.nextLine();

	name = fname + " " + lname;	 

	System.out.println(" ");
	System.out.print("Please enter your gender M or F: ");

	temp_read = sc.nextLine();

	
	//temp_read.toUpperCase();
	//gender = Character.toUpperCase(gender)
	gender = temp_read.charAt(0);



	if(gender == 'M' || gender == 'F') 
		{
			System.out.println("You have entered an invaild selection...");
			main(gbtm);
		}

  	System.out.println("Enter the account you wish to open a savings S or checking C");

  					temp_read = sc.nextLine();
  	
  			acc_type = temp_read.charAt(0);

  			//acc_type = Character.toUpperCase(acc_type);

			
	if(acc_type == 'S' || acc_type == 'C') 
		{
			System.out.println("You have entered an invaild type of account...");
			main(gbtm); 
			
		}

	System.out.print("Please enter your age: ");
	age = sc.nextInt();

	System.out.println(" ");

	System.out.println("Please select a Personal Identification Number (PIN): ");
	pin = sc.nextInt();


	System.out.print("Please enter how much you would like to add to your account: ");

	account_balance = sc.nextInt();


		stmt.setString(1, name);
		stmt.setString(2, String.valueOf(gender));
		stmt.setInt(3, age);
		stmt.setInt(4, pin);
		stmt.executeUpdate(); 		//Input data into customer database

		

		newstmt.setInt(1,pin);
		rs = newstmt.executeQuery();

	while(rs.next())
	{
		
		ID = rs.getInt("id");
	}
		


		stmt1.setInt(1,ID);
		stmt1.setInt(2, account_balance);
		stmt1.setString(3, String.valueOf(acc_type));
		stmt1.setString(4,String.valueOf(status));
		stmt1.executeUpdate();		//Input data into accounts database



	                                                                         			 //Close the connection after we are done with everything			

rs.close();                                                                             //Close the result set after we are done with the result set
stmt1.close();																					//Close the first Statement once we are done
stmt.close();                                                                           //Close the statement after we are done with the statement
conn.close(); 



}//end of try block
       catch (Exception e) 
		{
       		 System.out.println("Exception in Menu()");
      		  e.printStackTrace();
     		 }
main(gbtm);

}//Menu



public static void Menu2(String form[])
{



	Scanner input = new Scanner(System.in);
	int ID_check;  			//Input check for ID
	
	int pin_in;			//Variable to store account from DB
	int choice;				//Menu choice of customer

	
try
{
		Class.forName(driver);                                                                 							 //load the driver
        Connection conn = DriverManager.getConnection(url, username, password);                 					 //Create the connection                         			  			
    	String query = "SELECT ID, PIN FROM p1.CUSTOMER WHERE ID = " + ID_check + " AND pin = " + pin_in;              //The query to run
        Statement pstmt = conn.createStatement();												//Create a  prepared statement  
        ResultSet rs = null; 
                                            											 					  //Executing the query and storing the results in a Result Set
	System.out.print("Please enter your PIN");
	
	pin_in = input.nextInt();
	
	System.out.println("Please enter your 3 digit customer Identification");
	
	ID_check = input.nextInt();

	rs = pstmt.executeQuery(query);
	
	
	while(rs.next()) {
	ID = rs.getInt(1);
	pin = rs.getInt(2);
	}
	rs.close();
	pstmt.close();
	

	if (pin_in != pin || ID != ID_check)
	{
		System.out.println("Incorrect pin or account number");
		main(form);
		
	
	  }


	else if (pin_in == 0 && ID_check == 0)
	{
		
			
			boolean admin = false;

			while(admin != true)
			{
				System.out.println("Overlord, Please select from the following:");
	 		 	System.out.println("1. Account Summary for a Customer");
				System.out.println("2. Report A: With all balances in decreasing order");
				System.out.println("3. Report B: Find the total average between age groups");
			 	System.out.println("3. Exit");

				choice = input.nextInt();
			

	
			
				switch (choice)
				{
					case (1): User_Summary();break;
					case (2): Report1();break;
				//	case (3): Report2();
					case (3): main(form);
				}
			}
		
		}	
	 
	else if (pin_in == pin && ID_check == ID)
	 {
			
	
	  			 //Loop through the menu until a selection is made
         
			 System.out.println("Please select from the following:");
	 		 System.out.println("1. Open account");
			 System.out.println("2. Close account");
			 System.out.println("3. Deposit");
	 		 System.out.println("4. Withdraw");
			 System.out.println("5. Transfer");
			 System.out.println("6. Account Summary");
			 System.out.println("7. Exit");
			 
			  choice = input.nextInt();

			  acc_num = ID;
			
			switch (choice)
			{

				case 1: openAccount(acc_num);
				Menu2(form);
				case 2: closeAccount(acc_num);
				Menu2(form);
				case 3: deposit(acc_num);
				Menu2(form);
				case 4:	withdraw(acc_num);
				Menu2(form);
		//		case 5: transfer(acc_num);
				case 6: summary(ID);
				Menu2(form);
				case 7: main(form);

			}
		

	}
}//end of try block

catch (Exception e) {
        System.out.println("Exception in Menu2()");
        e.printStackTrace();
      }
	
}//Menu2


public static void openAccount(int account_id)
{


	Scanner input = new Scanner(System.in);
	char type;
	int amount;
	System.out.println(account_id);
	try
	{
		Class.forName(driver);                                                                  //load the driver
      		Connection conn = DriverManager.getConnection(url, username, password);                  //Create the connection
       		PreparedStatement stmt = conn.prepareStatement("insert into p1.account (id, balance, type, status) values ('?','?','?','A')");                                                 //Create a statement
       		
       		ResultSet rs = stmt.executeQuery();   							//run the the statement

		System.out.println("What type of account would you like to open?");
		System.out.print(" ");
		System.out.println("Savings S or Checking C  (Please enter S or C)");
		
		type = input.nextLine().charAt(0);
		type = Character.toUpperCase(type);


		

		if(type != 'S' || type != 'C')
			{
				System.out.println("Please enter either S or C");
				type = input.nextLine().charAt(0);
			}

 

		System.out.println("How much would you like to open the account with?");

		amount = input.nextInt();


		stmt.setInt(1, account_id);
		stmt.setInt(2, amount);
		stmt.setString(3, String.valueOf(type));
		stmt.executeUpdate();

		stmt.close();
		conn.close();
		rs.close();

	}


		catch (Exception e) 
			{
      		  System.out.println("Exception in openAccount()");
       			 e.printStackTrace();
			}


}//openAccount()    



public static void closeAccount(int account_id)
{

Scanner scanner = new Scanner(System.in);
int response;
ID = account_id;

try {
//Create the connection

		System.out.println("Are you sure you want to close the account?");
		System.out.print("Y/N: ");
		String next = scanner.nextLine();
		//String next1 = next.toLowerCase();
		if (next.equals("y")) {
		
			
		 Class.forName(driver);  
      	Connection con = DriverManager.getConnection(url, username, password);  
		Statement stmt = con.createStatement();    
			
			String close_account = "UPDATE p1.ACCOUNT set status = 'I' WHERE id = " + ID;
			stmt.executeUpdate(close_account);
		stmt.close();
		con.close();

		}
		
       
}//try block

catch (Exception e) {
        System.out.println("Exception in closeAccount()");
        e.printStackTrace();
      }


      
}//closeAccount



public static void transfer(int account_info)
{

	Scanner input = new Scanner(System.in);
	int from_account;
	int to_account;
try 
{

		Class.forName(driver);                                                                  //load the driver
      	Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
       	                                   //Create a statement
		String query ="SELECT (balance - ?) from p1.account where number = ? and status = 'A' "; //The query to run
		PreparedStatement stmt = con.prepareStatement(query);
		            	stmt.executeUpdate();
		ResultSet rs = stmt.executeQuery(query);  										 
   	 
	
	System.out.println("Which account would you like to transfer FROM? Enter account number");
	 from_account = input.nextInt();


	 System.out.println("Which account would you like to transfer TO? Enter account number");

	  to_account = input.nextInt();


	  stmt.setInt(1, from_account);
	  stmt.setInt(2, account_balance);
	  stmt.executeUpdate();


	  account_balance = rs.getInt(1);

	  deposit(account_balance);
}// end of try block


catch (Exception e) {
        System.out.println("Exception in transfer()");
        e.printStackTrace();
      }

}


public static void deposit(int account_id)
{
	
	

	Scanner input = new Scanner(System.in);
	System.out.println("How much would you like to deposit?");
	String deposit = input.nextLine();

try{
	Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
               
		Statement stmt = con.createStatement();
		String updater = "Update p1.account SET balance = balance + " + deposit + " where ID = " + account_id;
		stmt.executeUpdate(updater);
		
		
		
		stmt.close();
		con.close();

}//try block



catch (Exception e) {
        System.out.println("Exception in deposit()");
        e.printStackTrace();
      }
	

}//deposit




public static void withdraw(int account_id)
{
	
	

	Scanner input = new Scanner(System.in);
	System.out.println("How much would you like to withdraw?");
	String withdraw = input.nextLine();

try{
	Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
               
		Statement stmt = con.createStatement();
		String updater = "Update p1.account SET balance = balance - " + withdraw + " where ID = " + account_id;
		stmt.executeUpdate(updater);
		
		
		
		stmt.close();
		con.close();

}//try block






catch (Exception e) {
        System.out.println("Exception in deposit()");
        e.printStackTrace();
      }
	


}//withdraw




public static void summary(int account_id)
{
	int sum_balance;   	//sum of total balance from all accounts
	int user_accounts;	  		 //account numbers
	int balance; 	  	 //balance from an account
	ID = account_id;
	try
	{

		Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
        Statement stmt = con.createStatement();
	
	
String view_balance = "SELECT SUM(BALANCE) FROM p1.account where ID = '" + ID + "'";
	
	String total = "'";
	Statement stmt2 = con.createStatement();
	ResultSet rs2 = stmt2.executeQuery(view_balance);
	
		while(rs2.next())
		{
			total = rs2.getString(1);
			System.out.println("Total Balance: " + total);
		}
	
	
	rs2.close();
	stmt2.close();
	con.close();	//You left off here 


}//end of try block

catch (Exception e) {
        System.out.println("Exception in summary()");
        e.printStackTrace();
      }


}//summary



public static void User_Summary()
{

	int sum_balance;   //sum of total balance from all accounts
	int accounts;	   //account numbers
	int balance; 	   //balance from an account
	Scanner input = new Scanner(System.in);

	try
	{

		Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection	
		System.out.println("Please enter in a user ID");

		ID = input.nextInt();

	String sql_query = "Select number, balance from p1.account where Status = 'A' and ID = " + ID;
	String view_balance = "SELECT SUM(BALANCE) FROM p1.account where ID = '" + ID + "'";
	
	String total = "'";
	Statement stmt2 = con.createStatement();
	ResultSet rs2 = stmt2.executeQuery(view_balance);
	
		while(rs2.next())
		{
			total = rs2.getString(1);
			System.out.println("Total Balance: " + total);
		}
	
	
	rs2.close();
	stmt2.close();
	con.close();
	
}//end of try block

catch (Exception e) {
        System.out.println("Exception in User_Summary()");
        e.printStackTrace();
      }

}//User_Summary



public static void Report1()
{

	int sum_balance;   	//sum of total balance from all accounts
	int user_accounts;	   //account numbers
	int balance;

try
{
		Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
     //   Statement stmt = con.createStatement();
	
	
	/*String sql_query = "Select p1.account.id,p1.customer.name,p1.customer.age,p1.customer.gender," +
 "p1.account.balance from p1.account, p1.customer WHERE p1.customer.id = p1.account.id and status = " + "'" + "A" + "'" + "order by balance DESC";
	*/
	
	String sql_query = "Select p1.customer.id, p1.customer.name, p1.customer.age, p1.customer.gender, sum(balance) as Total from p1.customer INNER JOIN p1.account " +
	"ON p1.customer.id = p1.account.id where p1.account.status = 'A' GROUP by p1.customer.id, p1.customer.id,p1.customer.name,p1.customer.age,p1.customer.gender ORDER BY TOTAL desc";
	
	String view_balance = "select sum(balance) as Total from p1.account, p1.customer where p1.customer.id = p1.account.id and status =" + "'" + 
	"A" + "'";

	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(sql_query); 
	
		while(rs.next())
		{
			ID = rs.getInt(1);
			name = rs.getString(2);
			age = rs.getInt(3);
			gender = rs.getString(4).charAt(0);
			balance = rs.getInt(5);

			System.out.println("Bank ID: " + ID + " Name: "  + name + " Age: " + age + " Gender: " + gender + " Balance: "  + balance);		// Display a user's accounts with balances
			System.out.println();
		}
	
		rs = stmt.executeQuery(view_balance);
		while(rs.next())
		{
		sum_balance = rs.getInt(1);
		System.out.print("Total Balance: " + sum_balance); //Display total balance of the bank
		System.out.println();
		}
rs.close();
stmt.close();
con.close();
}//end of try block

catch (Exception e) {
        System.out.println("Exception in Report1()");
        e.printStackTrace();
      }

}//Report1


/*public static void Report2()
{
	int min_age;
	int max_age;
	Scanner input = new Scanner(System.in);

	try
	{

	Class.forName(driver);                                                                  //load the driver
        Connection con = DriverManager.getConnection(url, username, password);                  //Create the connection
     Statement stmt = con.createStatement();
        
	
	String query = SELECT AVG(Age) AS AVG_AGE, AVG(Balance) AS AVG_BALANCE FROM P1.Customer INNER JOIN " +
	"P1.Account ON P1.Customer.ID = P1.Account.ID WHERE P1.Account.Status = 'A' AND AGE>" + min_age + " AND AGE<" + max_age; //The query to run
	
	ResultSet rs = stmt.executeQuery(query);

	
	System.out.println("Enter a min age range:");
	min_age = input.nextInt();

	System.out.print("Enter a max age range:");
	max_age = input.nextInt();

	//max_age = max_age - min_age;

	



stmt.close();
rs.close();
con.close();
}//end of try block

catch (Exception e) {
        System.out.println("Exception in Report2()");
        e.printStackTrace();
      }

}//Report2
*/		

  static void init(String filename) {
    try {
      Properties props = new Properties();                                                      //Create a new Properties object
      FileInputStream input = new FileInputStream(filename);                                    //Create a new FileInputStream object using our filename parameter
      props.load(input);                                                                        //Load the file contents into the Properties object
      driver = props.getProperty("jdbc.driver");                                                //Load the driver
      url = props.getProperty("jdbc.url");                                                      //Load the url
      username = props.getProperty("jdbc.username");                                            //Load the username
      password = props.getProperty("jdbc.password");                                            //Load the password
    } catch (Exception e) {
      System.out.println("Exception in initalizing file init()");
      e.printStackTrace();
    }
  }//init
}//End p1