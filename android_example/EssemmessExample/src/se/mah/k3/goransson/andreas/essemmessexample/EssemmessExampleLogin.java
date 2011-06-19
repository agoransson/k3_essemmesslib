package se.mah.k3.goransson.andreas.essemmessexample;

import se.mah.k3.goransson.andreas.essemmesslib.Essemmess;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessHelper;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessListener;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessLoginEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessReadEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessRegisterEvent;
import se.mah.k3.goransson.andreas.essemmesslib.EssemmessWriteEvent;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Make sure to implement the "EssemmessListener" interface!
 */
public class EssemmessExampleLogin extends Activity implements
		EssemmessListener, OnClickListener {

	private static final String TAG = "EssemmessExampleLogin";

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
		login.setOnClickListener(this);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		/* Create a new menu from the xml file "mymenu.xml" */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.register_user:
			/* Dialog to register user, will return something */
			Intent new_user = new Intent(EssemmessExampleLogin.this,
					EssemmessExampleRegister.class);
			startActivityForResult(new_user,
					EssemmessExampleRegister.REGISTER_USER);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void essemmessRead(EssemmessReadEvent evt) {
	}

	@Override
	public void essemmessLogin(EssemmessLoginEvent evt) {
		/* If we successfully log in to the server, start the next activity */
		if (evt.getLoggedIn()) {
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
	}

	@Override
	public void essemmessRegister(EssemmessRegisterEvent evt) {
		if (evt.getResult())
			Toast.makeText(this, "User registered successfully",
					Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(
					this,
					"User registration failed with message " + evt.getMessage(),
					Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button_login) {
			mServer.login(username.getText().toString(), password.getText()
					.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case EssemmessExampleRegister.REGISTER_USER:
			String username = data.getStringExtra("username");
			String password = data.getStringExtra("password");
			String email = data.getStringExtra("email");
			Bitmap avatar = data.getParcelableExtra("avatar");
			mServer.register(username, password, email, avatar);
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}