package se.mah.k3.goransson.andreas.essemmesslib;

import java.util.EventObject;

import android.util.Log;

public class EssemmessEvent extends EventObject {

	public static final int LOGIN = 101;
	public static final int WRITE = 102;
	public static final int READ = 103;
	public static final int REGISTER = 104;
	public static final int LOGOUT = 105;

	private int type;
	private String message;

	/**
	 * Constructor for successful interactions with the webservice.
	 * 
	 * @param source
	 * @param event_type
	 * @param message
	 */
	public EssemmessEvent(Object source, int event_type, String message) {
		super(source);
		this.type = event_type;
		this.message = message;
	}

	/**
	 * 
	 * @return
	 */
	public int getEventType() {
		return type;
	}

	/**
	 * Get the message, if any.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}
}
