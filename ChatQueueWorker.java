

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.CORBA.portable.OutputStream;



public class ChatQueueWorker implements Runnable {

	public volatile ChatData chatData = null;
	public volatile Socket socket = null;

	public ChatQueueWorker(Socket socketIn, ChatData chatDataIn) {
		chatData = chatDataIn;
		socket = socketIn;
	}

	@Override
	public void run() {
		while (true) {

			if (chatData.clientName.length() > 0) {
	
				while (!chatData.chatMap.get(chatData.clientName).isEmpty()) {
					
					Message message = chatData.chatMap.get(chatData.clientName).remove();
					
			
					try {
						
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(message);
						
					} catch (IOException e) {
						System.err.println(e.getMessage());
						System.exit(0);
					}

				}
			}

		}

	}

}
