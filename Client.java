package bankbone;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	public static Request clientSend(Request currRequest) throws Exception {
		Socket socket= new Socket("localhost",6666);
		
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
		
		outStream.writeObject(currRequest);
		
		currRequest = (Request)inStream.readObject();
		System.out.println("\nStatus : " + currRequest.success);
		socket.close();
		
		return(currRequest);
	}
		
}
