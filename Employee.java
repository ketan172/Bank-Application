package bankbone;

public class Employee implements java.io.Serializable {
	
	private static final long serialVersionUID = -5943136937209598841L;
	//serialVersionUID for Serialization
	
	//Data Members
	int empID;			//Stores Employee ID
	String name;		//Name of Employee
	String password;	//Password of Employee
	
	
	//Constructors and Member Functions
	public Employee() {	//Default Constructor
		empID = 0;
	}
	public Employee(int empID) {	//Default Constructor
		this.empID = empID;
	}
	public Employee(int id, String n, String pass) {	//Parameterized
		empID = id;
		name = n;
		password = pass;
	}
	public Employee(Employee emp) {	//Copy Cons
		empID = emp.empID;
		name = emp.name;
		password = emp.password;
	}
	
	void setEmployee(int id, String n, String pass) {	//Setter
		empID = id;
		name = n;
		password = pass;
	}
	void setEmployee(Employee emp) {	//Setter
		empID = emp.empID;
		name = emp.name;
		password = emp.password;
	}
	
	int getID() {
		return(empID);
	}
	
	String getName() {
		return(name);
	}
	
	String getPassword() {
		return(password);
	}
	
	public void printEmployee() {
		System.out.println("\nEmployee ID : " + empID + "\nName : " + name);
	}
	
}
