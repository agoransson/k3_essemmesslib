package se.mah.k3.goransson.andreas.essemmessexample;

import java.util.ArrayList;

import se.mah.k3.goransson.andreas.essemmesslib.Essemmess;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessHelper;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessListener;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessLoginEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessReadEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessRegisterEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessWriteEvent;
import se.mah.k3.goransson.andreas.essemmesslib.Post;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EssemmessExampleRead extends Activity implements EssemmessListener {

	private static final String TAG = "EssemmessExampleRead";
	/* UI */
	/* Header (filter) */
	private EditText filtertext;
	private Button filterbutton;
	/* Content (list) */
	private ListView list;
	private ArrayList<Post> messages;
	private CustomListAdapter adapter;

	private Essemmess mServer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read);

		/* Connect the server to this activity */
		mServer = EssemmessHelper.getServer(this);

		/* UI */
		/* Header (filter UI ) */
		filtertext = (EditText) findViewById(R.id.read_filter);
		filterbutton = (Button) findViewById(R.id.read_update);
		filterbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mServer.read(filtertext.getText().toString());
			}
		});
		/* Content (list) */
		list = (ListView) findViewById(R.id.read_messages);
		messages = new ArrayList<Post>();
		adapter = new CustomListAdapter(this, R.layout.messageitem, messages);
		list.setAdapter(adapter);
	}

	@Override
	protected void onStop() {
		/* Remove this activity from the list of listeners */
		mServer.removeEssemmessEventListener(this);

		super.onPause();
	}

	@Override
	protected void onResume() {
		/* Add this activity to the list listeners */
		mServer.addEssemmessEventListener(this);

		super.onResume();
	}

	@Override
	public void essemmessRead(EssemmessReadEvent evt) {
		/* If server returns messages, it'll be in here */
		// Log.i("test", "getting messages..." + evt.getPosts().size());

		/* Clear the messages */
		messages.clear();

		/* Get all new messages */
		ArrayList<Post> posts = evt.getPosts();
		for (Post p : posts) {
			messages.add(p);
		}

		Log.i("test2", "antal msg: " + posts.size());

		/* Update the listview */
		adapter.notifyDataSetChanged();
	}

	@Override
	public void essemmessLogin(EssemmessLoginEvent evt) {
	}

	@Override
	public void essemmessWrite(EssemmessWriteEvent evt) {
	}

	@Override
	public void essemmessRegister(EssemmessRegisterEvent evt) {
	}
}
