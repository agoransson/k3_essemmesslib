package se.k3.goransson.andreas.essemmesslib;

import android.content.Context;

public class EssemmessHelper {

	private static Essemmess server;

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
