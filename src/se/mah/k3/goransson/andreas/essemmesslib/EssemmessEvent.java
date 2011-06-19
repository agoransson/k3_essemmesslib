package se.mah.k3.goransson.andreas.essemmesslib;

import java.util.EventObject;

public class EssemmessEvent extends EventObject {

	public static final int LOGIN = 101;
	public static final int WRITE = 102;
	public static final int READ = 103;
	public static final int REGISTER = 104;

	private int type;

	public EssemmessEvent(Object source, int event_type) {
		super(source);
		// TODO Auto-generated constructor stub

		this.type = event_type;
	}

	/**
	 * 
	 * @return
	 */
	public int getEventType() {
		return type;
	}
}
