package se.mah.k3.goransson.andreas.essemmessexample;

import se.mah.k3.goransson.andreas.essemmesslib.Essemmess;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessHelper;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessListener;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessLoginEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessReadEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessWriteEvent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Make sure to implement the "EssemmessListener" interface!
 */
public class EssemmessExampleLogin extends Activity implements
		EssemmessListener {

	/* Create the Essemmess server obj */
	private Essemmess mServer;

	/* UI */
	private EditText username;
	private EditText password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/* get the server instance */
		mServer = EssemmessHelper.getServer(this);

		/* UI */
		username = (EditText) findViewById(R.id.text_username);
		password = (EditText) findViewById(R.id.text_password);
		Button login = (Button) findViewById(R.id.button_login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mServer.login(username.getText().toString(), password.getText()
						.toString());
			}
		});
	}

	@Override
	protected void onPause() {
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
		/* We won't use the read event in this login-activity */
	}

	@Override
	public void essemmessLogin(EssemmessLoginEvent evt) {
		/* If we successfully log in to the server, start the next activity */
		if (evt.getLoggedin()) {
			/* Create the intent to start the "write" activity */
			Intent nextActivity = new Intent(EssemmessExampleLogin.this,
					EssemmessExampleWrite.class);
			startActivity(nextActivity);

			/* Make sure to kill this activity! It has no use now. */
			finish();
		} else {
			Toast.makeText(this, "Login failed, wrong user credentials?",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void essemmessWrite(EssemmessWriteEvent evt) {
		/* We won't use this write event in the login activity */
	}

}