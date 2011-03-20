package se.k3.goransson.andreas.essemmesslib;

import java.util.EventObject;

public class EssemmessLoginEvent extends EventObject {

	private Boolean loggedin;

	public EssemmessLoginEvent(Essemmess source, Boolean logedin) {
		super(source);
		this.loggedin = logedin;
	}

	/**
	 * Get the status of the login attempt.
	 * 
	 * @return
	 */
	public Boolean getLoggedin() {
		return loggedin;
	}

}
