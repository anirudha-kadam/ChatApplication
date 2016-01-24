

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.omg.CORBA.portable.OutputStream;


public class ChatSendWorker implements Runnable{
	
	Socket clientSocket = null;
	
	public String clientName = null;
	public ChatSendWorker(Socket clientSocketIn) {
		clientSocket = clientSocketIn;
	}

	@Override
	public void run() {
		
		while(true){
			
			System.out.println("****MENU****");
			System.out.println("1. Register");
			System.out.println("2. Who is Online?");
			System.out.println("3. Send Message");
			System.out.println("4. Exit");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch(choice){
			
			case 1:
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter your name: ");
				try {
					clientName = bufferedReader.readLine();
					Message registrationMessage = new Message("register", clientName, "", "");
					ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
					oos.writeObject(registrationMessage);
					
				} catch (IOException e1) {
					System.err.println(e1.getMessage());
				}
				
				break;
				
			case 2:
				if(clientName != null){
					Message onlineRequest = new Message("online", clientName, "", "");
					//OutputStream os = (OutputStream) clientSocket.getOutputStream();
					ObjectOutputStream oos1;
					try {
						oos1 = new ObjectOutputStream(clientSocket.getOutputStream());
						oos1.writeObject(onlineRequest);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
						System.exit(0);
					}
				}else{
					System.out.println("You need to register first to see who is online");
				}
				
				
				break;
				
				
			case 3:
				
				if(clientName != null){
					BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
					
					try {
						System.out.println("Enter recipient name: ");
						String recipientName = bufferedReader1.readLine();
						System.out.println("Enter message: ");
						String text = bufferedReader1.readLine();
						Message message = new Message("message", clientName, recipientName, text);
						//OutputStream os = (OutputStream) clientSocket.getOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
						oos.writeObject(message);
						//oos.close();
						//os.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.err.println(e1.getMessage());
						System.exit(0);
					}
				}else{
					System.out.println("You need to register first to send message to anyone");
				}
				
				
				break;
				
			case 4:
				Message message = new Message("exit", clientName, "", "");
				
				ObjectOutputStream oos;
				try {
					oos = new ObjectOutputStream(clientSocket.getOutputStream());
					oos.writeObject(message);
					clientSocket.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
					System.exit(0);
				}
				System.exit(0);
				break;
				
				default:
					System.err.println("Enter Correct Choice");
					break;
			}
			
			
		}
		
	}

}
