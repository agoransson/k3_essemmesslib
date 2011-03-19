package se.k3.goransson.andreas.essemmesslib;

import java.util.EventObject;

public class EssemmessLoginEvent extends EventObject {
	
	private Boolean loggedin;
	
	public EssemmessLoginEvent(Essemmess source, Boolean logedin) {
		super(source);
		this.loggedin = logedin;
	}

	public Boolean getLoggedin() {
		return loggedin;
	}

}
