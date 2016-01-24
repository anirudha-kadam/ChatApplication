

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientSocket {
	public volatile Socket clienSocket = null;
	
	public ChatClientSocket(String hostName, int  portNumber) {
		try {
			clienSocket = new Socket(hostName, portNumber);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createWorker(){
		
		try {
			ChatSendWorker chatSendWorker = new ChatSendWorker(clienSocket);
			Thread sendWorker = new Thread(chatSendWorker);
			ChatReceiveWorker chatReceiveWorker = new ChatReceiveWorker(clienSocket);
			Thread receiveWorker = new Thread(chatReceiveWorker);
			sendWorker.start();
			receiveWorker.start();
			sendWorker.join();
			receiveWorker.join();
		} catch (InterruptedException e) {
			System.err.println("Caught InterruptedException: "+e.getMessage());
		}
		
	}
	
}
