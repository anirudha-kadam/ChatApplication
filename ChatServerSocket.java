

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;



public class ChatServerSocket {
	public volatile ServerSocket serverSocket = null;
	public volatile Socket socket = null;
	Map<String, LinkedBlockingQueue<Message>> chatMap = null;
	public ChatServerSocket(int portNumber) {
		chatMap = new ConcurrentHashMap<String, LinkedBlockingQueue<Message>>();
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createWorker(){
		
		while(true){
			try {
				socket = serverSocket.accept();
				ChatData chatData = new ChatData(this.chatMap);
				ChatServerWorker chatServerWorker = new ChatServerWorker(socket, chatData);
				ChatQueueWorker chatQueueWorker = new ChatQueueWorker(socket, chatData);
				Thread newWorker1 = new Thread(chatServerWorker);
				Thread newWorker2 = new Thread(chatQueueWorker);
				newWorker1.start();
				newWorker2.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
