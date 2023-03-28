package bankbone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Integer;
//import java.util.*;

public class BankBone {

	public static void main(String[] args) throws IOException, Exception {
				
		//Variables used all over the Bank Code
		boolean loggedIn = false;
		int ch = 0, empID;
		String password;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		
		while(loggedIn == false) {
			
			System.out.println("Welcome to the KPKVM Bank :-");
			System.out.print("Please enter your ID : ");
			empID = Integer.parseInt(reader.readLine());
			System.out.print("\nEnter your Password : ");
			password = reader.readLine();
			
			Employee currEmployee;
			currEmployee = new Employee(empID);
			Request currRequest = new Request(-1, currEmployee);	//-2 for details of single employee
			
			currRequest = Client.clientSend(currRequest);		//Send login id to JDBC Server to check password

			if(currRequest.success == 0) {
				System.out.println("Error Logging In");
				currRequest = null;
				continue;			//Skip all rest and continue to start of loop
			}

			currEmployee = currRequest.getEmployee();
			currRequest = null;
			
			if(empID == currEmployee.getID() && password.contentEquals(currEmployee.getPassword())) {
				loggedIn = true;
				System.out.println("\nLogged In.\nEmployee ID : " + currEmployee.empID + "\nEmployee Name : " + currEmployee.name + "\n");
			}
			else {
				System.out.println("\nPlease enter correct Credentials.\n");
			}
			while(loggedIn == true) {
				
				//int success = 1;
				System.out.println("\n\nWelcome to KPKVM Bank :-");
				System.out.println("Operations Available :-");
				
				//ADMIN EXCLUSIVE OPTIONS
				if(currEmployee.empID == 1) {
					System.out.println("-1 : Details of one Employee");
					System.out.println("-2 : Add new Employee");
					System.out.println("-3 : Delete Employee");
					System.out.println("-4 : Change Employee Details");
				}
				
				//EMPLOYEE OPTIONS
				System.out.println("1  : Open a new Acount");
				System.out.println("2  : Details of one Account");
				System.out.println("3  : Change Details of Account");
				System.out.println("4  : Deposit Money in Account");
				System.out.println("5  : Withdraw Money from Account");
				System.out.println("6  : Transfer Money to Account");
				System.out.println("7  : Deactivate Account");
				System.out.println("0  : Logout");
				
				//Collect response from user on which operation to perform in int 'ch'
				System.out.println("Enter your choice : ");
				ch = Integer.parseInt(reader.readLine());
				
				System.out.println("\nChoice : " + ch);
				
/************************ADMIN EXCLUSIVE OPERATIONS*****************************/
					
					if(ch == -1) {	//Single Employee Details
						//Call frame for single employee datails
						
						System.out.print("Please enter Employee ID for details : ");
						int detailEmpID = Integer.parseInt(reader.readLine());
						
						Employee detailEmployee = new Employee(detailEmpID);
						currRequest = new Request(-1, detailEmployee);
						
						currRequest = Client.clientSend(currRequest);		//Send employee id to JDBC Server to get info
	
						if(currRequest.success == 0) {
							System.out.println("Please enter Valid Employee Number");
							currRequest = null;
							continue;
						}
						detailEmployee = currRequest.getEmployee();
						currRequest = null;
						detailEmployee.printEmployee();
						System.out.println("Password : " + detailEmployee.password);
					}
					
					else if(ch == -2) {	//New Employee
						//Call frame for new employee
						
						System.out.print("Please enter name of Employee : ");
						String newName = reader.readLine();
						String newPassword = validatePassword();
						
						Employee newEmployee = new Employee(-1, newName, newPassword);
						currRequest = new Request(-2, newEmployee);
						
						currRequest = Client.clientSend(currRequest);		//Send info to JDBC Server to add employee
	
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						newEmployee = currRequest.getEmployee();
						currRequest = null;
						System.out.println("\nNew Employee added :-\n");
						newEmployee.printEmployee();
						System.out.println("Password : " + newEmployee.password);
					}
					
					else if(ch == -3) {	//Delete Employee
						//Call frame for del employee
						
						System.out.print("Please enter Employee ID to delete : ");
						int delEmpID = Integer.parseInt(reader.readLine());
						
						Employee delEmployee = new Employee(delEmpID,"","");
						currRequest = new Request(-3, delEmployee);
						
						currRequest = Client.clientSend(currRequest);		//Send employee id to JDBC Server to delete
	
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						currRequest = null;
						System.out.println("\nEmployee deleted.\n");
						
					}
					
					else if(ch == -4) {	//Change Employee Name/Pass
						//Call frame for Change Name/Pass Emp
						
						System.out.print("Please enter Employee ID to update : ");
						int changeEmpID = Integer.parseInt(reader.readLine());
						System.out.print("Please enter name of Employee: ");
						String changeName = reader.readLine();
						String changePassword = validatePassword();
						
						Employee changeEmployee = new Employee(changeEmpID, changeName, changePassword);
						currRequest = new Request(-4, changeEmployee);
						
						currRequest = Client.clientSend(currRequest);		//Send employee info to JDBC Server to update
	
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						changeEmployee = currRequest.getEmployee();
						currRequest = null;
						System.out.println("\nEmployee details updated :-\n");
						changeEmployee.printEmployee();
						System.out.println("Password : " + changeEmployee.password);
					}
					
	/***************************LOGOUT*****************************/
					else if(ch == 0) {
						currRequest = new Request(0);
						//currRequest = Client.clientSend(currRequest);			//Pass this request to server so that server stops listening
						currEmployee = null;
						loggedIn = false;
						empID = 0;
						password = "";
						System.out.println("\nLogged Out.");
					}
					
	/*********************NORMAL EMPLOYEE OPERATIONS******************/
					else if(ch == 1) {	//Add new Account
						//Call frame for adding new account
						
						System.out.print("Please enter name of Account: ");
						String newName = reader.readLine();
						System.out.print("Please enter Start Balance to deposit : ");
						int newBal = Integer.parseInt(reader.readLine());
						String newPassword = validatePassword();
						
						Account newAccount = new Account(newBal, newName, newPassword);
						currRequest = new Request(1, newAccount);
						
						currRequest = Client.clientSend(currRequest);		//Send account info to JDBC Server to create account
	
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						newAccount = currRequest.getAccount();
						currRequest = null;
						System.out.println("\nNew Account created :-\n");
						newAccount.printAccount();
						System.out.println("Password : " + newAccount.password);
					}
										
					else if(ch == 2) {	//Details of one account
						//Frame
						System.out.print("Please enter Account No. : ");
						int detailAccNo = Integer.parseInt(reader.readLine());
						
						Account detailAccount = new Account(detailAccNo);
						currRequest = new Request(2, detailAccount);
						
						currRequest = Client.clientSend(currRequest);		//Send account no. to JDBC Server to get details
						
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						detailAccount = currRequest.getAccount();
						currRequest = null;
						System.out.println("\nAccount details are :-\n");
						detailAccount.printAccount();
						System.out.println("Password : " + detailAccount.password);
						
					}
					
					else if(ch == 3) {	//Change Details of Account
						//Frame
						System.out.print("Please enter Account No. : ");
						int changeAccNo = Integer.parseInt(reader.readLine());
						System.out.print("Please enter name of Account: ");
						String changeName = reader.readLine();
						String changePassword = validatePassword();
						
						Account changeAccount = new Account(changeAccNo, changeName, changePassword);
						currRequest = new Request(3, changeAccount);
						
						currRequest = Client.clientSend(currRequest);		//Send account details to JDBC Server to update
						
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						changeAccount = currRequest.getAccount();
						currRequest = null;
						System.out.println("\nAccount details updated successfully :-\n");
						changeAccount.printAccount();
						System.out.println("Password : " + changeAccount.password);
						
					}
					
					else if(ch == 4) {	//Deposit Money
						//Frame
						System.out.print("Please enter Account No. to deposit money : ");
						int depositAccNo = Integer.parseInt(reader.readLine());
						System.out.print("Please enter amount of money to deposit : ");
						int depositAmount = Integer.parseInt(reader.readLine());
						
						Transaction depositTransaction = new Transaction(1, depositAccNo, depositAmount, false);	//1 for Bank Cash Account. false for interAcc transfer
						currRequest = new Request(4, depositTransaction);
						
						currRequest = Client.clientSend(currRequest);		//Send transaction to JDBC Server to deposit money
						
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						depositTransaction = currRequest.getTransaction();
						currRequest = null;
						System.out.println("\nAmount deposited successfully :-\n");
						depositTransaction.printTransactionResult();
						
					}
					
					else if(ch == 5) {	//Withdraw Money
						//Frame
						System.out.print("Please enter Account No. to withdraw money : ");
						int withdrawAccNo = Integer.parseInt(reader.readLine());
						System.out.print("Please enter amount of money to withdraw : ");
						int withdrawAmount = Integer.parseInt(reader.readLine());
						
						Transaction withdrawTransaction = new Transaction(withdrawAccNo, 1, withdrawAmount, false);	//1 for Bank Cash Account. false for interAcc transfer
						currRequest = new Request(5, withdrawTransaction);
						
						currRequest = Client.clientSend(currRequest);		//Send transaction to JDBC Server to withdraw money
						
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						withdrawTransaction = currRequest.getTransaction();
						currRequest = null;
						System.out.println("\nAmount withdrawn successfully :-\n");
						withdrawTransaction.printTransactionResult();
						
					}
					
					else if(ch == 6) {	//Transfer Money
						//Frame
						System.out.print("Please enter Account No. to transfer money from : ");
						int senderAccNo = Integer.parseInt(reader.readLine());
						System.out.print("Please enter Account No. to transfer money to : ");
						int receiverAccNo = Integer.parseInt(reader.readLine());
						System.out.print("Please enter amount of money to transfer : ");
						int transferAmount = Integer.parseInt(reader.readLine());
						
						Transaction transferTransaction = new Transaction(senderAccNo, receiverAccNo, transferAmount, true);	//true for interAcc transfer
						currRequest = new Request(6, transferTransaction);
						
						currRequest = Client.clientSend(currRequest);		//Send transaction to JDBC Server to TRANSFER MONEY
						
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						transferTransaction = currRequest.getTransaction();
						currRequest = null;
						System.out.println("\nAmount transfered successfully :-\n");
						transferTransaction.printTransactionResult();
						
					}
										
					else if(ch == 7) {	//Deactivate Account
						//Frame
						System.out.print("Please enter Account No. to deactivate : ");
						int deleteAccNo = Integer.parseInt(reader.readLine());
	
						Account deleteAccount = new Account(deleteAccNo, false);
						currRequest = new Request(7, deleteAccount);
						
						currRequest = Client.clientSend(currRequest);		//Send account no. to JDBC Server to deactivate
						
						if(currRequest.success == 0) {
							System.out.println("Some Error Occurred");
							currRequest = null;
							continue;
						}
						currRequest = null;
						System.out.println("\nAccount deleted successfully\n");
						
					}
					
					else {
						System.out.println("\nPlease enter a proper choice.\n");
						
					}

			}
				
		}
		
	}
	
	//Member Functions to be used
	public static String validatePassword() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String pass = "";
		boolean a = false;
		while(a == false) {
			System.out.print("Enter Password : ");
			pass = reader.readLine();
			a = validate(pass);
			if(a == true) {
				System.out.println("PASSWORD Validated.");
			}
			else {
				System.out.println("Please enter a PASSWORD with:");
				System.out.println("->Atleast 8 characters\r\n" + 
						"->Atleast one number\r\n" + 
						"->Atleast one uppercase\r\n" + 
						"->Atleast one lowercase");
			}
		}
		return(pass);
	}
	public static boolean validate(String password) {
		boolean hasNum=false; boolean hasCap=false; boolean hasLow=false; char c;
		if(password.length()>7) {
			for(int i=0;i<password.length();i++) {
				c = password.charAt(i);
				if(Character.isDigit(c)) {
					hasNum=true;
				}
				else if(Character.isUpperCase(c)) {
					hasCap=true;
				}
				else if(Character.isLowerCase(c)) {
					hasLow=true;
				}
				if(hasNum && hasCap && hasLow) {
					return true;
				}
			}
			return(false);//will return false, even if one of the 3 criterias is not satisfied
		}
		else				//Length less than 8
			return(false);
	}
	
}