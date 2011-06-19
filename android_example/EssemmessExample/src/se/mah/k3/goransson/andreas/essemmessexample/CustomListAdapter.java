/*
 *  GPSSuit - An application which let its users attach information to 
 *  geographical data and connect that data to a Bluetooth enabled device
 *  that allows for physical feedback through a large set of vibrators.
 *  
 *  Copyright (C) 2010  1scale1 Handelsbolag, Stahl Stenslie
 *
 *  This file is part of GPSSuit.
 *
 *  GPSSuit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GPSSuit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GPSSuit.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.mah.k3.goransson.andreas.essemmessexample;

import java.util.ArrayList;

import se.mah.k3.goransson.andreas.essemmesslib.Post;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<Post> {

	private ArrayList<Post> messages;
	private Context context;

	public CustomListAdapter(Context _context, int textViewResourceId,
			ArrayList<Post> messages) {
		super(_context, textViewResourceId, messages);
		this.messages = messages;
		context = _context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.messageitem, null);
		}
		Post p = messages.get(position);
		if (p != null) {
			/* Avatar */
			ImageView avatar = (ImageView) v
					.findViewById(R.id.messageitem_avatar);
			if (avatar != null) {
				avatar.setImageBitmap(p.getUser().getAvatar());
			}

			/* Author */
			TextView author = (TextView) v
					.findViewById(R.id.messageitem_author);
			if (author != null) {
				author.setText(p.getUser() + " wrote:");
			}

			/* Message */
			TextView message = (TextView) v
					.findViewById(R.id.messageitem_message);
			if (message != null) {
				message.setText(p.getMessage());
			}

			/* Tag */
			TextView tag = (TextView) v.findViewById(R.id.messageitem_tag);
			if (tag != null) {
				tag.setText("about " + p.getTag());
			}
		}
		return v;
	}
}