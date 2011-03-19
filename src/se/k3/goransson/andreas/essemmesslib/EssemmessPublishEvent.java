package se.k3.goransson.andreas.essemmesslib;

import java.util.EventObject;

public class EssemmessPublishEvent extends EventObject {

	private String returnmessage;
	
	public EssemmessPublishEvent(Essemmess source, String returnmessage ) {
		super(source);
		this.returnmessage = returnmessage;
	}

	public String getMessage(){
		return returnmessage;
	}
}