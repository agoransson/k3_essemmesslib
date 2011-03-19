package se.k3.goransson.andreas.essemmesslib;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class Essemmess {

	/* Application context */
	private Context ctx;

	/* Stored access_token, acquired from server */
	private String access_token;

	public Essemmess(Context ctx) {
		this.ctx = ctx;
	}

	public void login(String username, String password) {
		/* Execute the HttpWorker as LOGIN with the parameters */
		new HttpWorker(this.ctx, HttpWorker.LOGIN).execute(username, password);
	}

	public void post(String message, String tag) {
		/* Execute the HttpWorker as POST with the parameters */
		new HttpWorker(this.ctx, HttpWorker.POST).execute(access_token, message, tag);
	}

	public void logout() {
		/* Execute the HttpWorker as LOGOUT with the parameters */
		new HttpWorker(this.ctx, HttpWorker.LOGOUT).execute(access_token);
	}

	public void read(String filter_tag) {
		/* Execute the HttpWorker as READ with the parameters */
		new HttpWorker(this.ctx, HttpWorker.READ).execute(access_token, filter_tag);
	}

	/**
	 * Generic Worker, used to connect, and read responses from the server.
	 * 
	 * @author andreas
	 * 
	 */
	private class HttpWorker extends AsyncTask<String, Integer, Integer> {

		/* TAG */
		private static final String TAG = "HttpWorker";

		/* Location of the server scripts */
		private static final String SERVER = "http://195.178.228.96/pfi3/";

		/* Action constants */
		public static final int LOGIN = 10;
		public static final int POST = 11;
		public static final int READ = 12;
		public static final int LOGOUT = 13;

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
		protected Integer doInBackground(String... params) {

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
			case POST:
				/* Set the url */
				url = SERVER + "post.php";

				/* Parameters... */
				arguments.add(new BasicNameValuePair("access_token", params[0]));
				arguments.add(new BasicNameValuePair("message", params[1]));
				arguments.add(new BasicNameValuePair("tag", params[2]));
				break;
			case READ:
				/* Set url */
				url = SERVER + "read.php";

				/* Parameters */
				arguments.add(new BasicNameValuePair("access_token", params[0]));
				arguments.add(new BasicNameValuePair("filter_tag", params[1]));
				break;
			case LOGOUT:
				/* Set the url */
				url = SERVER + "logout.php";

				/* Parameters... */
				arguments.add(new BasicNameValuePair("access_token", params[0]));
				break;
			}

			/* Fire the http post and store the response */
			response = httppost(url, arguments);
			
			//Log.i( "test", response );
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			/* Parse the JSON msg */
			try {
				JSONObject json_data = new JSONObject(response);

				switch (action) {
				case LOGIN:
					/* Store the access_token for future use */
					access_token = json_data.getString("access_token");
					break;
				case POST:
					/**/
					break;
				case READ:
					/* Read all posts from response into arraylist */
					ArrayList<Post> posts = new ArrayList<Post>();

					JSONArray json_array = json_data.getJSONArray("posts");

					for (int i = 0; i < json_array.length(); ++i) {
						JSONObject json_post = json_array.getJSONObject(i);

						posts.add(new Post(json_post.getString("tag"), json_post
								.getString("user"), json_post.getString("message")));
					}

					/* Dispatch the event with arraylist */
					dispatchEvent(new EssemmessEvent(Essemmess.this, posts));
					break;
				}

			} catch (JSONException e) {
				Log.e(TAG, "Error parsing data " + e.toString());
			}

			/* We're done, shut the progress dialog off */
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

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

				/* Add the login information "POST" variables in the php */
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),
						8);

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
	protected EventListenerList listenerList = new EventListenerList();

	// This methods allows classes to register for MyEvents
	public void addEssemmessEventListener(EssemmessListener listener) {
		listenerList.add(EssemmessListener.class, listener);
	}

	// This methods allows classes to unregister for MyEvents
	public void removeEssemmessEventListener(EssemmessListener listener) {
		listenerList.remove(EssemmessListener.class, listener);
	}

	// This private class is used to fire MyEvents
	void dispatchEvent(EssemmessEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == EssemmessListener.class) {
				((EssemmessListener) listeners[i + 1]).NewEssemmessPosts(evt);
			}
		}
	}

}
