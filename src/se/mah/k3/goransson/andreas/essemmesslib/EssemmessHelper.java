package se.mah.k3.goransson.andreas.essemmesslib;

/*
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

import android.content.Context;

/**
 * Helper class that contains a static reference to the server object, this
 * helps the app developer in the sense that s/he doesn't need to keep track of
 * the state of the server instance.
 * 
 * Add the following line to the onCreate method in your activity:
 * 
 * "Essemmess mServer = EssemmessHelper.getServer( this );"
 * 
 * @author Andreas Göransson, andreas.goransson@mah.se
 * 
 */
public class EssemmessHelper {

	/* Essemmess server object */
	private static Essemmess server;

	/**
	 * Returns the Essemmess server with the specified context, and if the
	 * server hasn't been created (is null) it creates a new Essemmess instance
	 * in the specified context.
	 * 
	 * @param ctx
	 * @return
	 */
	public static Essemmess getServer(Context ctx) {
		if (!(server != null)) {
			/* Not created yet */
			server = new Essemmess(ctx);
		} else {
			/* Created already, switch context */
			server.setContext(ctx);
		}
		return server;
	}
}
