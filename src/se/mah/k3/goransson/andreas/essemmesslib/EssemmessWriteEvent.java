package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * EssemmessWriteEvent.java
 * 
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


/**
 * 
 * @author Andreas G�ransson, andreas.goransson@mah.se
 * 
 */
public class EssemmessWriteEvent extends EssemmessEvent {

	/* The status of the write-attempt */
	private boolean result = false;

	public EssemmessWriteEvent(Essemmess source, String json_message,
			boolean result) {
		super(source, EssemmessEvent.WRITE, json_message);
		this.result = result;
	}

	public boolean getResult() {
		return result;
	}
}