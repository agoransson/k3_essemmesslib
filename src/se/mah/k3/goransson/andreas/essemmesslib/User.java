package se.mah.k3.goransson.andreas.essemmesslib;

import android.graphics.Bitmap;

public class User {

	private String name;
	private String email;
	private Bitmap avatar;

	public User(String name, String email, Bitmap avatar) {
		this.name = name;
		this.email = email;
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public Bitmap getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}

	public String toString() {
		return name;
	}
}
