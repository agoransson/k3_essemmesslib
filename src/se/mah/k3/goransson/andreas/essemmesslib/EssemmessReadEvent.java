package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * Connects to, and interacts with, the messaging-system set up at 
 * Malm� University. This library is part of the Android specific mobile 
 * design courses at Arts and Communication.
 * 
 * Copyright (C) 2011  Andreas G�ransson
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
import java.util.EventObject;

/**
 * 
 * @author Andreas G�ransson, andreas.goransson@mah.se
 * 
 */
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