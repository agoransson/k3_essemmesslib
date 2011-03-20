package se.k3.goransson.andreas.essemmesslib;

import java.util.EventListener;

public interface EssemmessListener extends EventListener {

	/**
	 * Executed when a READ event has been dispatched.
	 * 
	 * @param evt
	 */
	public void NewEssemmessPosts(EssemmessReadEvent evt);

	/**
	 * Executed when a LOGIN event has been dispatched.
	 * 
	 * @param evt
	 */
	public void NewEssemmessLogin(EssemmessLoginEvent evt);

	/**
	 * Executed when a PUBLISH event has been dispatched.
	 * 
	 * @param evt
	 */
	public void NewEssemmessPublish(EssemmessPublishEvent evt);

}
