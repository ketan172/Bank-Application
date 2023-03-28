package bankbone;

//import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.*;
//import java.sql.DriverManager;

public class JDBCBank {
	
	public static Request queryFire(Request currRequest, Connection myConn) {
		
		try {  
	        int choice = currRequest.operationNo;
	        currRequest.success = 1;
	        System.out.println("Choice is: " + choice);
	        
	        //Insert switch case from here.
	        
		        if (choice == -1) {	//Single Employee Details
		        	int eid = currRequest.employee.empID;
		        	//int emp_id = 0,emp_active;
		        	//String emp_name,emp_password;
		        	
		        	Statement myStat = myConn.createStatement();
		        	String q="Select * from employee where empID='" + eid + "'";
		        	
		        	//to execute query
		        	ResultSet rs = myStat.executeQuery(q);
		        	
		        	//to print the result set on console
		        	if(rs.next())
		        	{
		        		
		        		System.out.println("Record found");
		        		currRequest.employee.empID = rs.getInt("empID");
		        		currRequest.employee.name = rs.getString("name");
		        		currRequest.employee.password = rs.getString("password");
					}
					else {
						currRequest.success = 0;
						System.out.println("Record Not Found..."); //record not found in and else part also not includede in final code
					}
		        	return(currRequest);
			    }
                else if(choice == -2) { //Add new Employee
			    	Statement myStat = myConn.createStatement();
			    	int emp_id;
			    	String emp_name = currRequest.employee.name;
			    	String emp_password = currRequest.employee.password;
			    	
			    	String q ="insert into employee(name,password) values('" + emp_name + "','" + emp_password + "');";
			    	int rowsAffected = myStat.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);	    	
			    	
			    	if(rowsAffected == 1) {	//Insertion successful
			    		ResultSet rs = myStat.getGeneratedKeys();			//Check this part by running
			    		rs.next();
			    		int key = rs.getInt(1);
			    		currRequest.employee.empID = key;
			    		System.out.println("Test : empID of new Employee is : " + key );
			    	}
			    	else {
						currRequest.success = 0;
						System.out.println("Couldnt add record"); 	//Couldnt add record
					}
			    	return(currRequest);
                }
                else if(choice == -3) {	//Delete single employee
			    	int eid = currRequest.employee.empID;
			    	Statement myStat = myConn.createStatement();
			    	String q="Delete from employee where empID='" + eid + "';";
			    	int rowsAffected = myStat.executeUpdate(q);
			    	if(rowsAffected != 0) {
			    		System.out.println("Record Deleted sucessfully");
			    	}
			    	else {
						currRequest.success = 0;
						System.out.println("Couldnt delete record"); //Couldnt delete record
					}
			    	return(currRequest);
                }
                else if(choice == -4) {		//Update Employee Details
                	int eid = currRequest.employee.empID;
                	int emp_id;
                	String emp_name = currRequest.employee.name, emp_password = currRequest.employee.password;
                	try {
	                	Statement myStat = myConn.createStatement();
	                	String q="update employee set name='" + emp_name + "', password='" + emp_password + "' where empID='" + eid + "';";
	                	myStat.executeUpdate(q);
	                	System.out.println("Record updated sucessfully");
                	}
                	catch(Exception e) {
						currRequest.success = 0;
						System.out.println("Couldnt update record"); //Couldnt update record
					}
                	return(currRequest);
                }
                else if(choice == 0) {	//Logout
                	//No need to do anything here
                }
                else if(choice == 1) {	//Add new account
                	int acc_No;
                	int acc_balance = currRequest.account.balance;
                	String acc_name = currRequest.account.name;
                	String acc_password = currRequest.account.password;
                	Statement myStat = myConn.createStatement();
                	String q="insert into account(name,password,balance)values('" + acc_name + "','" + acc_password + "','" + acc_balance + "');";
                	int rowsAffected = myStat.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);
                	
                	if(rowsAffected == 1) {	//Insertion successful
			    		ResultSet rs = myStat.getGeneratedKeys();			//Check this part by running
			    		rs.next();
			    		int key = rs.getInt(1);
			    		currRequest.account.accNo = key;
			    		System.out.println("Test : accNo of new Account is : " + key );
			    	}
			    	else {
						currRequest.success = 0;
						System.out.println("Couldnt add account"); 	//Couldnt add account
					}
                	return(currRequest);
                }
               else if(choice == 2) {	//Display single account details
            	   int acc_No = currRequest.account.accNo;
            	   
            	   Statement myStat = myConn.createStatement();
            	   String q="Select * from account where accNo='"+ acc_No +"';";
            	   
            	   ResultSet rs = myStat.executeQuery(q);
            	   
            	   if(rs.next()) {
            		   currRequest.account.accNo = rs.getInt("accNo");
            		   currRequest.account.name = rs.getString("name");
            		   currRequest.account.password = rs.getString("password");
            		   currRequest.account.balance = rs.getInt("balance");
            		   if(rs.getInt("active") != 0)
            			   currRequest.account.active = true;
            		   else
            			   currRequest.account.active = false;
            		   System.out.println("Record found");
            	   }
            	   else {
            		   currRequest.success = 0;
            		   System.out.println("Record Not Found"); //record not found in and else part also not included in final code
            	   }
            	   return(currRequest);
               }
               else if(choice == 3) {	//Change Details of Account
            	   int acc_No = currRequest.account.accNo;
            	   String acc_name = currRequest.account.name;
            	   String acc_password = currRequest.account.password;
            	   try {
	            	   Statement myStat = myConn.createStatement();
	            	   String q = "update account set name='" + acc_name + "', password='" + acc_password + "' where accNo='"+ acc_No +"';";
	            	   myStat.executeUpdate(q);
	            	   System.out.println("Record updated sucessfully"); //Updated successfully
            	   }
            	   catch(Exception e) {
            		   currRequest.success = 0;
            		   System.out.println("Couldnt update account"); 	//Couldnt update account
            	   }
            	   return(currRequest);
                }
		        
               	else if(choice == 4) {	//Deposit Money in Account
               		int receiverAccNo = currRequest.transaction.receiverAccNo;
               		int senderAccNo = currRequest.transaction.senderAccNo;
               		int amount = currRequest.transaction.amount;
	    			currRequest.transaction.success = 1;
               		
               		int inter_Acc = 0, success=1;
               		myConn.setAutoCommit(false);
               		try {
               			Statement myStat = myConn.createStatement();
	               		String q = "update account set balance=(balance+'" + amount + "')where accNo='" + receiverAccNo + "';";
	               		myStat.executeUpdate(q);
	               		System.out.println("Amount deposited successfully");//transfer the details to the object back
	               		Statement mystat = myConn.createStatement();
	               		String p = "insert into transaction(senderAcc,receiverAcc,amount,interAcc,success) values('" + senderAccNo + "','" + receiverAccNo + "','" + amount + "','" + inter_Acc + "','" + success + "');";
	               		mystat.executeUpdate(p);
	               		myConn.commit();
	               		System.out.println("Transaction done successfully");
	               		currRequest.transaction.success = 1;
               			myConn.setAutoCommit(true);
               		}
               		catch(Exception e) {
		    			success = 0;
		    			currRequest.success = 0;
		    			currRequest.transaction.success = 0;
		    			myConn.rollback();
               			myConn.setAutoCommit(true);
			    		Statement MyStat = myConn.createStatement();
				    	String t="insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + senderAccNo + "','" + receiverAccNo + "','" + amount + "','" + currRequest.transaction.interAcc + "','" + success + "');";
				        MyStat.executeUpdate(t);
               		}
               		return(currRequest);
               	}
		        
               	else if(choice == 5) {	//Withdraw money from account
               		int acc_balance;
               		int senderAccNo = currRequest.transaction.senderAccNo;
               		int receiverAccNo = currRequest.transaction.receiverAccNo;
               		int amount = currRequest.transaction.amount;
	    			currRequest.transaction.success = 1;
               		
			    	int inter_Acc=0, success=1;
			    	Statement mystat = myConn.createStatement();
			    	String r = "Select balance from account where accNo='" + senderAccNo + "';";
			    	
			    	ResultSet rs = mystat.executeQuery(r);
			    	//mystat.executeUpdate(q);
			    	if(rs.next()) {
			    		acc_balance=rs.getInt("balance");
						System.out.println("Record found"); //record found will be in comment
					}
			    	//System.out.printf("account balance : " + acc_balance);
			    	if(amount < rs.getInt("balance")) {
			    		myConn.setAutoCommit(false);
			    		try {
				    		Statement myStat = myConn.createStatement();
					    	String q = "update account set balance=(balance-'" + amount + "')where accNo='" + senderAccNo + "';";
					        myStat.executeUpdate(q);
					        System.out.println("Amount withdraw successfully");//transfer the details to the object back
					        Statement Mystat = myConn.createStatement();
					    	String s = "insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + senderAccNo + "','" + receiverAccNo + "','" + amount + "','" + inter_Acc + "','" + success + "');";
					        Mystat.executeUpdate(s);
					        System.out.println("Transaction done successfully");
					        myConn.commit();
	               			myConn.setAutoCommit(true);
			    		}
			    		catch(Exception e) {
			    			success = 0;
			    			currRequest.success = 0;
			    			currRequest.transaction.success = 0;
			    			myConn.rollback();
	               			myConn.setAutoCommit(true);
				    		Statement MyStat = myConn.createStatement();
					    	String t="insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + senderAccNo + "','" + receiverAccNo + "','" + amount + "','" + currRequest.transaction.interAcc + "','" + success + "');";
					        MyStat.executeUpdate(t);
			    		}
				    }
			    	else {
		    			success = 0;
		    			currRequest.success = 0;
		    			currRequest.transaction.success = 0;
		    			myConn.rollback();
               			myConn.setAutoCommit(true);
			    		Statement MyStat = myConn.createStatement();
				    	String t="insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + senderAccNo + "','" + receiverAccNo + "','" + amount + "','" + currRequest.transaction.interAcc + "','" + success + "');";
				        MyStat.executeUpdate(t);	
			    	}
			    	return(currRequest);
               	}
		        
               	else if(choice == 6) {	//Transfer Money
               		int acc_balance = 0, amount = currRequest.transaction.amount;
			    	int sender_Acc = currRequest.transaction.senderAccNo;
			    	int receiver_Acc = currRequest.transaction.receiverAccNo;
			    	int inter_Acc = 1, success = 1;
			    	
			    	Statement mystat = myConn.createStatement();
			    	String p = "Select balance from account where accNo='" + sender_Acc + "';";
			        ResultSet rs = mystat.executeQuery(p);
			    	//mystat.executeUpdate(q);
			    	if(rs.next()) {
						System.out.println("Record found"); //record found will be in comment
			    		acc_balance = rs.getInt("balance");
					}
			    	System.out.printf("account balance : " +acc_balance);
/*			    	Statement Mystat = myConn.createStatement();
			    	String q="Select balance from account where accNo='"+receiver_Acc+"'";
			    	ResultSet r=Mystat.executeQuery(q);
			    	mystat.executeUpdate(q);
			    	if(r.next()) {
			    		acc_balance=r.getInt("balance");
			    		System.out.println("Record found"); //record found will be in comment
			    	}

			    	//acc_balance=rs.getInt("balance");
*/
			    	if(amount < rs.getInt("balance")) {
			    		try {
			    			myConn.setAutoCommit(false);
				    		Statement myStat = myConn.createStatement();
				    		String s = "update account set balance=(balance+'" + amount + "')where accNo='" + receiver_Acc + "';";
				    		myStat.executeUpdate(s);
				    		Statement MyStat = myConn.createStatement();
				    		String t = "update account set balance=(balance-'" + amount + "')where accNo='" + sender_Acc + "';";
				    		myStat.executeUpdate(t);
				    		Statement MyStaT = myConn.createStatement();
				    		String u = "insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + sender_Acc + "','" + receiver_Acc + "','" + amount + "','" + inter_Acc + "','" + success + "');";
				    		MyStat.executeUpdate(u);
				    		System.out.println("Transaction done successfully");
				    		myConn.commit();
				    		myConn.setAutoCommit(true);

			    		}
			    		catch(Exception e) {
			    			success = 0;
			    			currRequest.success = 0;
			    			currRequest.transaction.success = 0;
			    			myConn.rollback();
	               			myConn.setAutoCommit(true);
				    		Statement MyStat = myConn.createStatement();
					    	String t="insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + sender_Acc + "','" + receiver_Acc + "','" + amount + "','" + currRequest.transaction.interAcc + "','" + success + "');";
					        MyStat.executeUpdate(t);	
			    		}
			    	}
			    	else  {
		    			success = 0;
		    			currRequest.success = 0;
		    			currRequest.transaction.success = 0;
		    			myConn.rollback();
               			myConn.setAutoCommit(true);
			    		Statement MyStat = myConn.createStatement();
				    	String t="insert into transaction(senderAcc,receiverAcc,amount,interAcc,success)values('" + sender_Acc + "','" + receiver_Acc + "','" + amount + "','" + currRequest.transaction.interAcc + "','" + success + "');";
				        MyStat.executeUpdate(t);
			    	}
			    	return(currRequest);
               	}
		        
                	else if(choice == 7) {
			    	int acc_No = currRequest.account.accNo;
			        Statement myStat = myConn.createStatement();
			    	String q = "update account set active=0 where accNO='" + acc_No + "';";
			        int rowsAffected = myStat.executeUpdate(q);
			        if(rowsAffected != 0)
			        	System.out.println("Account deactivated");
			        else {
			        	System.out.println("Couldnt deactivate");
			        	currRequest.success = 0;
			        }
			        return(currRequest);
			    }
			}
			catch(Exception e) {
				System.out.println(e);
				currRequest.success = 0;
				return(currRequest);
			}
		return(currRequest);
	}
}
