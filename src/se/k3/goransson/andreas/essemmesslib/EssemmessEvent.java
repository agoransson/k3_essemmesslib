package se.k3.goransson.andreas.essemmesslib;

import java.util.ArrayList;
import java.util.EventObject;

public class EssemmessEvent extends EventObject {

	private ArrayList<Post> posts;

	public EssemmessEvent(Essemmess source, ArrayList<Post> posts) {
		super(source);

		this.posts = posts;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

}
