

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{
	
	public String header = null;
	public String from = null;
	public String to = null;
	public String message = null;
	public List<String> onlinePeople = new ArrayList<String>();
	
	public Message() {
		
	}
	
	public Message(String headerIn, String fromIn, String toIn, String messageIn) {
		header = headerIn;
		from = fromIn;
		to = toIn;
		message = messageIn;
	}
}
