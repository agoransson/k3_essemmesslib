package se.k3.goransson.andreas.essemmesslib;

import android.content.Context;

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
