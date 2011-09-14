package se.mah.k3.goransson.andreas.essemmessexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EssemmessExampleRegister extends Activity implements
		OnEditorActionListener, OnClickListener {

	private EditText username, password, email;
	private Button avatar, register;

	private Bitmap image;

	public static final int REGISTER_USER = 5678;
	private static final int GET_IMAGE = 1234;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		/* UI */
		username = (EditText) findViewById(R.id.reg_username);
		username.setOnEditorActionListener(this);
		password = (EditText) findViewById(R.id.reg_password);
		password.setOnEditorActionListener(this);
		email = (EditText) findViewById(R.id.reg_email);
		email.setOnEditorActionListener(this);

		avatar = (Button) findViewById(R.id.reg_avatar);
		avatar.setEnabled(false);
		avatar.setOnClickListener(this);

		register = (Button) findViewById(R.id.reg_register);
		register.setEnabled(false);
		register.setOnClickListener(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		/* If all three fields are valid, we'll enable the avatar button! */
		if (username.getText().toString().length() > 0
				&& password.getText().toString().length() > 0
				&& email.getText().toString().length() > 0) {
			avatar.setEnabled(true);
		} else {
			if (avatar.isEnabled())
				avatar.setEnabled(false);
		}

		return false;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.reg_avatar) {
			/* Get an image from the camera */
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, GET_IMAGE);
		} else if (v.getId() == R.id.reg_register) {

			Intent user = new Intent();
			user.putExtra("username", username.getText().toString());
			user.putExtra("password", password.getText().toString());
			user.putExtra("email", email.getText().toString());
			user.putExtra("avatar", image);
			setResult(REGISTER_USER, user);

			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GET_IMAGE) {
			image = (Bitmap) data.getExtras().get("data");			
			register.setEnabled(true);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
