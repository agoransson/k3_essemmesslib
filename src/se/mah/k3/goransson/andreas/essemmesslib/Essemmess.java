package se.mah.k3.goransson.andreas.essemmesslib;

/*
 * Essemmess.java
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.MultihomePlainSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

/**
 * Essemmess is a library used for communicating and interacting with the
 * message service developed at Malmö University, and is used for educational
 * purposes at the programming courses at Arts and Communication.
 * 
 * @author Andreas Göransson, andreas.goransson@mah.se
 * 
 */
public class Essemmess {

	/* Application context */
	private Context ctx;

	/* Stored access_token, acquired from server */
	private String access_token;

	/* Connectivity manager - Internet connected? */
	private ConnectivityManager mConnectivityManager;

	/**
	 * Create a new Essemmess instance, pass the current foreground context
	 * otherwise the app might crash. Do not use "getApplicationContext()" if
	 * you can avoid it.
	 * 
	 * @param ctx
	 */
	public Essemmess(Context ctx) {
		this.ctx = ctx;

		mConnectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * Sets the current context of the Essemmess server, should be used if you
	 * use the same server instance in multiple activies. Otherwise the
	 * application will probably crash.
	 * 
	 * @param ctx
	 */
	public void setContext(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Returns the md5 access_token stored inside the server.
	 * 
	 * @return
	 */
	public String getToken() {
		return access_token;
	}

	/**
	 * Registers a new user in the database.
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param avatar
	 */
	public void register(String username, String password, String email,
			Bitmap avatar) {
		if (mConnectivityManager.getActiveNetworkInfo().isConnected()) {
			/* Make sure the avatar exists */
			if (avatar != null) {
				/* Get the image as string */
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				/* We want to keep a standard size for all avatars, 72x72px */
				getScaledBitmap(avatar, 72).compress(
						Bitmap.CompressFormat.JPEG, 75, stream);
				byte[] byte_arr = stream.toByteArray();
				String image_str = Base64.encodeToString(byte_arr,
						Base64.DEFAULT);

				/* Execute the HttpWorker as REGISTER with the parameters */
				new HttpWorker(this.ctx, HttpWorker.REGISTER).execute(username,
						password, email, image_str);
			} else {
				Toast.makeText(this.ctx, "Image capture failed, try again",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Attempts to perform a login to the Essemmess server.
	 * 
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) {
		if (mConnectivityManager.getActiveNetworkInfo()
				.isConnectedOrConnecting()) {
			/* Execute the HttpWorker as LOGIN with the parameters */
			new HttpWorker(this.ctx, HttpWorker.LOGIN).execute(username,
					password);
		} else {
			/* Toast "not connected" */
			Toast.makeText(ctx,
					"No internet connection found when logging in to server.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Attemtps to perform a message publish to the Essemmess server.
	 * 
	 * @param message
	 * @param tag
	 */
	public void write(String message, String tag) {
		if (mConnectivityManager.getActiveNetworkInfo()
				.isConnectedOrConnecting()) {
			/* Execute the HttpWorker as POST with the parameters */
			new HttpWorker(this.ctx, HttpWorker.WRITE).execute(access_token,
					message, tag);
		} else {
			/* Toast "not connected" */
			Toast.makeText(ctx,
					"No internet connection found when posting to server.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Unregisters the Essemmess server, this should be called when the
	 * application is destroyed.
	 */
	public void logout() {
		if (mConnectivityManager.getActiveNetworkInfo()
				.isConnectedOrConnecting()) {
			/* Execute the HttpWorker as LOGOUT with the parameters */
			new HttpWorker(this.ctx, HttpWorker.LOGOUT).execute(access_token);
		} else {
			/* Toast "not connected" */
			Toast.makeText(ctx,
					"No internet connection found when logging out of server.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Attempts to read messages published on the Essemmess server.
	 * 
	 * @param filter_tag
	 */
	public void read(String filter_tag) {
		if (mConnectivityManager.getActiveNetworkInfo()
				.isConnectedOrConnecting()) {
			/* Execute the HttpWorker as READ with the parameters */
			new HttpWorker(this.ctx, HttpWorker.READ).execute(access_token,
					filter_tag);
		} else {
			/* Toast "not connected" */
			Toast.makeText(
					ctx,
					"No internet connection found when reading messages on server.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Resizes the bitmap to a smaller, squared, version.
	 * 
	 * @param bmp
	 * @return
	 */
	private Bitmap getScaledBitmap(Bitmap bmp, int size) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		/* Get the scale */
		float scale_w = ((float) size) / width;
		float scale_h = ((float) size) / height;

		/* Transformation matrix */
		Matrix matrix = new Matrix();
		matrix.postScale(scale_w, scale_h);

		/* Get the new bitmap */
		Bitmap resized = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,
				true);

		return resized;
	}

	/**
	 * Generic Worker, used to connect to, and read responses from, the
	 * Essemmess server.
	 * 
	 * @author Andreas Göransson, andreas.goransson@mah.se
	 */
	private class HttpWorker extends AsyncTask<String, Void, Void> {

		/* TAG */
		private static final String TAG = "HttpWorker";

		/* Location of the server scripts */
		private static final String SERVER = "http://195.178.232.26:8080/pfi3_2011/";

		/* Action constants */
		public static final int LOGIN = 10;
		public static final int WRITE = 11;
		public static final int READ = 12;
		public static final int LOGOUT = 13;
		public static final int REGISTER = 14;

		/* Selected action */
		private int action;

		/* The JSON response */
		private String response;

		/* The progress dialog, used while waiting for http POST */
		private ProgressDialog mProgressDialog;

		public HttpWorker(Context ctx, int action) {
			this.action = action;
		}

		@Override
		protected void onPreExecute() {
			if (action != LOGOUT) {
				/* Create the dialog... */
				mProgressDialog = new ProgressDialog(Essemmess.this.ctx);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mProgressDialog.setMessage("Working...");
				mProgressDialog.setCancelable(false);

				/* Show the dialog */
				mProgressDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {

			/* Server url */
			String url = null;

			/* Http POST arguments */
			ArrayList<NameValuePair> arguments = new ArrayList<NameValuePair>();

			/* Construct the POST and arguments */
			switch (action) {
			case LOGIN:
				/* Set the server url */
				url = SERVER + "login.php";

				/* Add the arguments */
				arguments.add(new BasicNameValuePair("username", params[0]));
				arguments.add(new BasicNameValuePair("password", params[1]));
				break;
			case WRITE:
				/* Set the url */
				url = SERVER + "post.php";

				/* Parameters... */
				arguments
						.add(new BasicNameValuePair("access_token", params[0]));
				arguments.add(new BasicNameValuePair("message", params[1]));
				arguments.add(new BasicNameValuePair("tag", params[2]));
				break;
			case READ:
				/* Set url */
				url = SERVER + "read.php";

				/* Parameters */
				arguments
						.add(new BasicNameValuePair("access_token", params[0]));
				arguments.add(new BasicNameValuePair("filter_tag", params[1]));
				break;
			case LOGOUT:
				/* Set the url */
				url = SERVER + "logout.php";

				/* Parameters... */
				arguments
						.add(new BasicNameValuePair("access_token", params[0]));
				break;
			case REGISTER:
				/* Set the url */
				url = SERVER + "register.php";

				/* Parameters... */
				arguments.add(new BasicNameValuePair("username", params[0]));
				arguments.add(new BasicNameValuePair("password", params[1]));
				arguments.add(new BasicNameValuePair("email", params[2]));
				arguments.add(new BasicNameValuePair("avatar", params[3]));
				break;
			}

			/* Fire the http post and store the response */
			response = httppost(url, arguments);

			Log.i("test", response);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			/* Make sure the http response has some content */
			if (response != null || response.length() > 0) {
				/* Parse the JSON msg */
				try {
					JSONObject json_data = new JSONObject(response);
					String message = null;

					switch (action) {
					case LOGIN:
						/* Store the access_token for future use */
						access_token = json_data.getString("access_token");

						if (access_token.length() == 32)
							dispatchEssemmessEvent(new EssemmessLoginEvent(
									Essemmess.this, true));
						else
							dispatchEssemmessEvent(new EssemmessLoginEvent(
									Essemmess.this, false));

						break;
					case WRITE:
						/* See if we managed to post something... */
						message = json_data.getString("message");

						// This is deprecated, but will leave it in for now...
						dispatchEssemmessEvent(new EssemmessWriteEvent(
								Essemmess.this, message));
						break;
					case READ:
						/* Read all posts from response into arraylist */
						ArrayList<Post> posts = new ArrayList<Post>();

						JSONArray json_array = json_data.getJSONArray("posts");

						for (int i = 0; i < json_array.length(); ++i) {
							JSONObject json_post = json_array.getJSONObject(i);

							JSONObject json_user = json_post
									.getJSONObject("user");
							/* Decode the bitmap */
							byte[] raw_image = Base64.decode(
									json_user.getString("avatar"),
									Base64.DEFAULT);
							Bitmap avatar = BitmapFactory.decodeByteArray(
									raw_image, 0, raw_image.length);

							/* Create the user */
							User u = new User(json_user.getString("username"),
									json_user.getString("email"), avatar);

							/* Add the post to the list */
							posts.add(new Post(json_post.getString("tag"), u,
									json_post.getString("message")));
						}

						/* Dispatch the event with arraylist */
						dispatchEssemmessEvent(new EssemmessReadEvent(
								Essemmess.this, posts));
						break;
					case REGISTER:
						/* See if we managed to register... */
						message = json_data.getString("message");

						// This is deprecated, but will leave it in for now...
						dispatchEssemmessEvent(new EssemmessRegisterEvent(
								Essemmess.this, message));
						break;
					}

				} catch (JSONException e) {
					Log.e(TAG, "Error parsing data " + e.toString());
				}

				/* We're done, shut the progress dialog off */
				if (mProgressDialog != null)
					mProgressDialog.dismiss();

			} else {
				/* HTTP Post failed... */
				Toast.makeText(ctx,
						"Connection to server failed, please try again.",
						Toast.LENGTH_SHORT).show();

			}
			super.onPostExecute(result);
		}

		/**
		 * Executes a httppost to a server instance with the given POST
		 * arguments and returns a String response from the server.
		 */
		private String httppost(String url, ArrayList<NameValuePair> args) {
			InputStream is = null;
			/* Send to server */
			try {
				/* Create the POST */
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);

				/* Add the "POST" variables in the php */
				httppost.setEntity(new UrlEncodedFormEntity(args));

				/* Execute the http POST and get the response */
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			} catch (Exception e) {
				Log.e(TAG, "Error in http connection " + e.toString());
			}

			/* Read from server */
			try {
				/* Read the response stream */
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);

				/* Copy the response to String */
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();

				/* Return the response as string */
				return sb.toString();
			} catch (Exception e) {
				Log.e(TAG, "Error converting result " + e.toString());
			}

			return null;
		}
	}

	/* ===== EVENT OBJECTS ===== */
	/**
	 * List of event listeners.
	 */
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * Add a new EssemmessListener to the list.
	 * 
	 * @param listener
	 */
	public void addEssemmessEventListener(EssemmessListener listener) {
		listenerList.add(EssemmessListener.class, listener);
	}

	/**
	 * Remove the selected EssemmessListener from the list.
	 * 
	 * @param listener
	 */
	public void removeEssemmessEventListener(EssemmessListener listener) {
		listenerList.remove(EssemmessListener.class, listener);
	}

	/**
	 * Dispatches a new PUBLISH event, this is dispatched when the HttpWorker
	 * has executed a PUBLISH action on the Essemmess server.
	 * 
	 * @param evt
	 */
	private void dispatchEssemmessEvent(EssemmessEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == EssemmessListener.class) {
				/* Fire the correct event type */
				if (evt.getEventType() == EssemmessEvent.LOGIN)
					((EssemmessListener) listeners[i + 1])
							.essemmessLogin((EssemmessLoginEvent) evt);
				else if (evt.getEventType() == EssemmessEvent.READ)
					((EssemmessListener) listeners[i + 1])
							.essemmessRead((EssemmessReadEvent) evt);
				else if (evt.getEventType() == EssemmessEvent.WRITE)
					((EssemmessListener) listeners[i + 1])
							.essemmessWrite((EssemmessWriteEvent) evt);
				else if (evt.getEventType() == EssemmessEvent.REGISTER)
					((EssemmessListener) listeners[i + 1])
							.essemmessRegister((EssemmessRegisterEvent) evt);

			}
		}
	}
}
