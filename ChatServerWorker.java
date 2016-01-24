

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.CORBA.portable.InputStream;



public class ChatServerWorker implements Runnable {
	public volatile Socket socket = null;
	public volatile ChatData chatData = null;

	public ChatServerWorker(Socket socketIn, ChatData chatDataIn) {
		socket = socketIn;
		chatData = chatDataIn;
	}

	@Override
	public void run() {
		
		while(true){
			
				
				try {

					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					Message message = (Message) ois.readObject();
			
					if(message.header.equals("register")){
						
						if(chatData.chatMap.get(message.from) != null){
							message.header = "registerfail";
							ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
							oos.writeObject(message);
						}else{
							chatData.chatMap.put(message.from, new LinkedBlockingQueue<Message>());
							Thread.sleep(500);
							chatData.clientName = message.from;
						}
						
						
					}else if(message.header.equals("online")){
						List<String> onlinePeople = new ArrayList<String>();
						for (Map.Entry<String, LinkedBlockingQueue<Message>> entry : chatData.chatMap.entrySet()) {
							onlinePeople.add(entry.getKey());
						}
						message.onlinePeople = onlinePeople;
						LinkedBlockingQueue<Message> linkedBlockingQueue = chatData.chatMap.get(message.from);
						linkedBlockingQueue.put(message);
						chatData.chatMap.put(message.from, linkedBlockingQueue);
					}else if(message.header.equals("message")){
						
						if (chatData.chatMap.get(message.to) != null) {
							LinkedBlockingQueue<Message> linkedBlockingQueue = chatData.chatMap.get(message.to);
							linkedBlockingQueue.put(message);
							chatData.chatMap.put(message.to, linkedBlockingQueue);
						}
					}else if(message.header.equals("exit")){
						chatData.chatMap.remove(message.from);
						break;
					}
					
					
					
				} catch (IOException e) {
					System.err.println(e.getMessage());
					System.exit(0);
				} catch (ClassNotFoundException e) {
					System.err.println(e.getMessage());
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			
		
		}
		
		
	}
}
