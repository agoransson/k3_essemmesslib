package se.k3.goransson.andreas.essemmesslib;

public class Post {

	public String tag;
	public String user;
	public String message;

	public Post(String tag, String user, String message) {
		super();
		this.tag = tag;
		this.user = user;
		this.message = message;
	}

	public String toString() {
		return "User: " + user + "  Tag: " + tag + "  Message: " + message;
	}
}
