package se.k3.goransson.andreas.essemmesslib;

/**
 * A representation of a message published on the Essemmess server.
 * 
 * @author andreas
 * 
 */
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

	public String getTag() {
		return tag;
	}

	public String getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * Returns a string representation of the published message with all
	 * information. User, Tag, and Message.
	 */
	public String toString() {
		return "User: " + user + "  Tag: " + tag + "  Message: " + message;
	}
}
