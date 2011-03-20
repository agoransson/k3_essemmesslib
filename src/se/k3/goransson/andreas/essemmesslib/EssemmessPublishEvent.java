package se.k3.goransson.andreas.essemmesslib;

import java.util.EventObject;

public class EssemmessPublishEvent extends EventObject {

	private String returnmessage;

	public EssemmessPublishEvent(Essemmess source, String returnmessage) {
		super(source);
		this.returnmessage = returnmessage;
	}

	/**
	 * Get the status of the PUBLISH attempt.
	 * 
	 * @return
	 */
	public String getMessage() {
		return returnmessage;
	}
}