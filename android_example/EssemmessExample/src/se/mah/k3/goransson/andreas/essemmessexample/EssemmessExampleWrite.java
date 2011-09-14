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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EssemmessExampleWrite extends Activity implements
		EssemmessListener {

	private static final String TAG = "EssemmessExampleWrite";

	/* Essemmess instance */
	private Essemmess mServer;

	/* UI */
	private EditText tag;
	private EditText message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);

		/* get the server instance */
		mServer = EssemmessHelper.getServer(this);

		/* UI */
		tag = (EditText) findViewById(R.id.text_tag);
		message = (EditText) findViewById(R.id.text_message);

		Button write = (Button) findViewById(R.id.button_write);
		write.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mServer.write(message.getText().toString(), tag.getText()
						.toString());
			}
		});
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
	}

	@Override
	public void essemmessLogin(EssemmessLoginEvent evt) {
	}

	@Override
	public void essemmessWrite(EssemmessWriteEvent evt) {
		if (evt.getResult()) {

			Toast.makeText(this, "Write success!", Toast.LENGTH_SHORT).show();

			/* This, however, is a good thing to check in this activity */
			Intent i = new Intent(EssemmessExampleWrite.this,
					EssemmessExampleRead.class);
			startActivity(i);
		} else {

			Toast.makeText(this, "Write didn't work...", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void essemmessRegister(EssemmessRegisterEvent evt) {
	}

}
