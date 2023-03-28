package bankbone;

//import java.util.*;

public class Request implements java.io.Serializable {
	
	private static final long serialVersionUID = -398046076928234686L;
	//serialVersionUID for Serialization
	
	//Data Members
	int operationNo;				//Type of Operation from List below
	int success;					//If request fulfilled or not
	String message;					//For getting error messages from Server
	
	Employee employee;
	Account account;
	Transaction transaction;
	
/*
	//Admin Operations 
	-1 : Details of one Employee
	-2 : Add new Employee
	-3 : Delete Employee
	-4 : Change Employee Details
				
	//Normal Operations of Employee
	1  : Open a new Account
	2  : Details of one Account
	3  : Change Details of Account
	4  : Deposit Money in Account
	5  : Withdraw Money from Account
	6  : Transfer Money to Account
	7  : Deactivate Account
	0 : Logout
 */
	
	//Constructors
	public Request() {
		
	}
	public Request(int opNo) {		//Should only use for choice 0 of logout
		operationNo = opNo;
	}
	public Request(int opNo, Employee emp) {
		operationNo = opNo;
		employee = emp;
	}
	public Request(int opNo, Account acc) {
		operationNo = opNo;
		account = acc;
	}
	public Request(int opNo, Transaction trans) {
		operationNo = opNo;
		transaction = trans;
	}
	public Request(int opNo, String m) {
		operationNo = opNo;
		message = m;
	}

	//Setter Methods
	public void setEmployee(int opNo, Employee emp) {
		operationNo = opNo;
		employee = emp;
		account = null;
		transaction = null;
	}
	public void setAccount(int opNo, Account acc) {
		operationNo = opNo;
		account = acc;
		employee = null;
		transaction = null;
	}
	public void setTransaction(int opNo, Transaction trans) {
		operationNo = opNo;
		transaction = trans;
		account = null;
		employee = null;
	}
	
	//Getters
	public Employee getEmployee() {
		account = null;
		transaction = null;
		return(employee);
	}
	public Account getAccount() {
		employee = null;
		transaction = null;
		return(account);
	}
	public Transaction getTransaction() {
		account = null;
		employee = null;
		return(transaction);
	}
	
	//Printer
	public void printRequest() {
		System.out.println("\nOperation No. : " + operationNo);
		if(employee != null) {							//Employee
			employee.printEmployee();
		}
		if(account != null) {							//Account
			if(operationNo == 3) {
				account.printAccount();
			}
			account.printAccountRequest();
		}
		if(transaction != null) {						//Transaction
			transaction.printTransactionResult();
		}
		
//			System.out.println(message);
		
	}
}
