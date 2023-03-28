package bankbone;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

public class ServerJDBC {

	public static void main(String[] args) throws Exception {
		
		ServerSocket s = new ServerSocket(6666);
		Request currRequest = new Request(10);
		
		
		//Class.forName("com.mysql.jdbc.Driver");	
        // Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db","root","root");
		String JdbcURL = "jdbc:mysql://localhost:3306/bank_db?" + "autoReconnect=true&useSSL=false";
	    String Username = "root";
        String password = "root";
        Connection myConn = null;
        try {
        	myConn = DriverManager.getConnection(JdbcURL, Username, password);
        	System.out.println("Your JDBC URL is as follows:" + JdbcURL);
        }
	    catch (Exception exec) {
	    	exec.printStackTrace();
	    }
		
        
		while(currRequest.operationNo != 0) {
			Socket socket = s.accept();
			
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			
			//if(currRequest.operationNo == 0)
			//	break;									//Commented this because server should never stop listening
			
			currRequest = (Request) inStream.readObject();
			System.out.println("Object Received");
			System.out.println("Object Op No : " + currRequest.operationNo);
			
			//currRequest = (Call JDBC Code Function here & return result in Request)
			currRequest = JDBCBank.queryFire(currRequest, myConn);
			
			outStream.writeObject(currRequest);
		}
		
		s.close();		
	}
	
}
