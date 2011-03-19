package se.k3.goransson.andreas.essemmesslib;

import java.util.EventListener;

public interface EssemmessListener extends EventListener {

	public void NewEssemmessPosts(EssemmessReadEvent evt);
	public void NewEssemmessLogin(EssemmessLoginEvent evt);
	public void NewEssemmessPublish(EssemmessPublishEvent evt);

}
