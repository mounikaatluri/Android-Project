package com.cs155.evilapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowMessagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        String contactNumber = getIntent().getStringExtra("contact");
        String messagesFromNumber = Utils.getMessagesFromNumber(this, contactNumber);
        TextView tvShowMessages = (TextView) findViewById(R.id.tv_show_messages);
        tvShowMessages.setText(messagesFromNumber);
    }
}
