package bankbone;

public class Account implements java.io.Serializable {
	
	private static final long serialVersionUID = -1431307555446447499L;
	//serialVersionUID for Serialization

	//Data Members
	public boolean active;			//Account active/deactivated
	public int accNo, balance;			//ID,Balance in account
	public String name, password;	//Name of customer, Password for e-Bank portal login
	
	//Constructors and Member Functions
	public Account() {	//Default
		active = false;
		accNo = 0;
		balance = 0;
	}
	public Account(int accNo) {
		this.accNo = accNo;
		this.balance = 0;
		this.name = "";
		this.active = true;
		this.password = "";
	}
	public Account(int accNo, boolean active) {
		this.accNo = accNo;
		this.balance = -1;
		this.name = "";
		this.active = active;
		this.password = "";
	}
	public Account(int bal, String name, String pass) {	//Parameterized
		this.accNo = -1;		//Set to know ID not assigned/not known
		this.balance = bal;
		this.name = name;
		this.active = true;
		this.password = pass;
	}
	public Account(Account acc) {	//Copy Constructor
		this.accNo = acc.accNo;		
		this.balance = acc.balance;
		this.name = acc.name;
		this.active = acc.active;
		this.password = acc.password;
	}
	
	//Setter
	public void setAccount(int accNo, int bal, String name, String pass) {	//For updation
		this.accNo = accNo;
		this.balance = bal;
		this.name = name;
		this.active = true;
		this.password = pass;
	}
	
	//Getters
	int getNo() {
		return(accNo);
	}
	
	int getBal() {
		return(balance);
	}
	
	String getName() {
		return(name);
	}
	
	String getPassword() {
		return(password);
	}
	
	boolean getStatus() {
		return(active);
	}
	
	//Printer
	public void printAccount() {
		System.out.println("\nAcc No. : " + this.accNo + "\nName : " + this.name + "\nBalance : " + this.balance + "\n");
	}
	public void printAccountRequest() {
		System.out.println("\nName : " + this.name);
	}
}
