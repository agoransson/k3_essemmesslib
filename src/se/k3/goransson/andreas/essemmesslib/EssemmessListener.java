package se.k3.goransson.andreas.essemmesslib;

import java.util.EventListener;

public interface EssemmessListener extends EventListener {

	public void NewEssemmessPosts(EssemmessEvent evt);

}
