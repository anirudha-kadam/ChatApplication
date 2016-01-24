

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;



public class ChatData {
	
	public volatile Map<String, LinkedBlockingQueue<Message>> chatMap = null;
	public volatile String clientName = null;
	public ChatData(Map<String, LinkedBlockingQueue<Message>> chatMapIn) {
		clientName = new String();
		chatMap = chatMapIn;
	}
}
