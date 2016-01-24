

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.CORBA.portable.InputStream;



public class ChatReceiveWorker implements Runnable {
	Socket clienSocket = null;

	public ChatReceiveWorker(Socket clientSocketIN) {
		clienSocket = clientSocketIN;
	}

	@Override
	public void run() {

		while (true) {
			
			try {
				
				ObjectInputStream ois = new ObjectInputStream(clienSocket.getInputStream());
				Message message = (Message) ois.readObject();
				
				if(message.header.equals("message")){
					System.out.println("__________Message Received__________");
					System.out.println(message.from+": "+message.message);
				}else if(message.header.equals("online")){
					System.out.println("__________Online People__________");
					System.out.println(message.onlinePeople);
				}else if(message.header.equals("registerfail")){
					System.out.println("__________Registration Failed________-");
					System.out.println("Name selected by you has already been used by someone else. Please select different name!!!");
				}
				
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(0);
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}
			


		}

	}

}
