

public class ServerDriver {

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("One argument is expected");
			System.exit(0);
		}
		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Port Number should be an Integer");
			System.exit(0);
		}
		ChatServerSocket chatServerSocket = new ChatServerSocket(portNumber);
		chatServerSocket.createWorker();
	}

}
