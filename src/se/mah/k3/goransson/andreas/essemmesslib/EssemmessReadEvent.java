package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * EssemmessReadEvent.java
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

import java.util.ArrayList;

/**
 * 
 * @author Andreas Göransson, andreas.goransson@mah.se
 * 
 */
public class EssemmessReadEvent extends EssemmessEvent {

	private ArrayList<Post> posts;
	private boolean result = true;

	/**
	 * Failed to read the posts for some reason.
	 * 
	 * @param source
	 * @param json_message
	 * @param result
	 */
	public EssemmessReadEvent(Essemmess source, String json_message,
			boolean result) {
		this(source, json_message, null);

		this.result = result;
	}

	/**
	 * Standard constructor, makes sure to have a non-null list. Even if the
	 * list is contains zero items.
	 * 
	 * @param source
	 * @param json_message
	 * @param posts
	 */
	public EssemmessReadEvent(Essemmess source, String json_message,
			ArrayList<Post> posts) {
		super(source, EssemmessEvent.READ, json_message);

		if (posts == null)
			posts = new ArrayList<Post>();
		else
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
