package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * Post.java
 * 
 * Connects to, and interacts with, the messaging-system set up at 
 * Malmö University. This library is part of the Android specific mobile 
 * design courses at Arts and Communication.
 * 
 * Copyright (C) 2011  Andreas Göransson
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A representation of a message-object.
 * 
 * @author Andreas Göransson
 */
public class Post {

	public User user;
	public String tag;
	public String message;
	public String time;

	public Post(String tag, User user, String message, String time) {
		super();
		this.tag = tag;
		this.user = user;
		this.message = message;
		this.time = time;
	}

	public String getTag() {
		return tag;
	}

	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}
	
	public String getTime(){
		return time;
	}

	/**
	 * Returns a string representation of the published message with all
	 * information. User, Tag, and Message.
	 */
	public String toString() {
		return "User: " + user.toString() + "  Tag: " + tag + "  Message: " + message;
	}
}
