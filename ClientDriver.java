

public class ClientDriver {

	public static void main(String[] args) {

		if(args.length != 2){
			System.err.println("Two arguments are expected");
			System.exit(0);
		}
		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Port Number should be an integer! "+ e.getMessage());
			System.exit(0);
		}
		ChatClientSocket chatClientSocket = new ChatClientSocket(args[0], portNumber);
		chatClientSocket.createWorker();
	}

}
