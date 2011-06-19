package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * EssemmessListener.java
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

import java.util.EventListener;

/**
 * Listener interface, this is the answer mechanism used to get replies from the
 * server.
 * 
 * @author Andreas Göransson, andreas.goransson@mah.se
 * 
 */
public interface EssemmessListener extends EventListener {

	/**
	 * Executed when a READ event has been dispatched.
	 * 
	 * @param evt
	 */
	public void essemmessRead(EssemmessReadEvent evt);

	/**
	 * Executed when a LOGIN event has been dispatched.
	 * 
	 * @param evt
	 */
	public void essemmessLogin(EssemmessLoginEvent evt);

	/**
	 * Executed when a PUBLISH event has been dispatched.
	 * 
	 * @param evt
	 */
	public void essemmessWrite(EssemmessWriteEvent evt);

	/**
	 * Executed when a REGISTER event has been dispatched.
	 * 
	 * @param evt
	 */
	public void essemmessRegister(EssemmessRegisterEvent evt);
}
