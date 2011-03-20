package se.k3.goransson.andreas.essemmesslib;

import java.util.ArrayList;
import java.util.EventObject;

public class EssemmessReadEvent extends EventObject {

	private ArrayList<Post> posts;

	public EssemmessReadEvent(Essemmess source, ArrayList<Post> posts) {
		super(source);

		this.posts = posts;
	}

	/**
	 * Returns all messages on the server, which correspond to the selected
	 * filter.
	 * 
	 * @return
	 */
	public ArrayList<Post> getPosts() {
		return posts;
	}

}
