package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * EssemmessLoginEvent.java
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

import java.util.EventObject;

/**
 * This is the login event object.
 * 
 * @author Andreas Göransson, andreas.goransson@mah.se
 */
public class EssemmessLoginEvent extends EventObject {
	private Boolean loggedin;

	public EssemmessLoginEvent(Essemmess source, Boolean logedin) {
		super(source);
		this.loggedin = logedin;
	}

	/**
	 * Get the status of the login attempt.
	 * 
	 * @return
	 */
	public Boolean getLoggedin() {
		return loggedin;
	}

}
