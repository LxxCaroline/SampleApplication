package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.QuickContactBadge;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class QuickContactBadgeActivity extends Activity {

    @InjectView(R.id.badge1)
    QuickContactBadge badge1;
    @InjectView(R.id.badge2)
    QuickContactBadge badge2;

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{ContactsContract.Contacts._ID, ContactsContract
            .Contacts.DISPLAY_NAME, ContactsContract.Contacts.STARRED, ContactsContract.Contacts.TIMES_CONTACTED,
            ContactsContract.Contacts.CONTACT_PRESENCE, ContactsContract.Contacts.PHOTO_ID, ContactsContract.Contacts
            .LOOKUP_KEY, ContactsContract.Contacts.HAS_PHONE_NUMBER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickcontact);
        ButterKnife.inject(this);
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND (" + ContactsContract.Contacts
                .HAS_PHONE_NUMBER + "=1) AND (" + ContactsContract.Contacts.DISPLAY_NAME + " != ''))";
        //查询所有联系人
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                CONTACTS_SUMMARY_PROJECTION, select, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE " +
                        "LOCALIZED ASC");
        //将记录指针移动到第一位
        cursor.moveToFirst();
        //获得联系人的id
        long contact_id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        //获得联系人的lookup_id
        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        //将当前联系人通过assignContactUri与quickContactBadge控件关联
        //还可以使用如下两个方法与联系人关联
        //QuickContactBadge.assignContactFromEmail和QuickContactBadge.assignContactFromPhone
        badge1.assignContactUri(ContactsContract.Contacts.getLookupUri(contact_id, lookupKey));
        //将记录指针移动到第儿位
        cursor.moveToFirst();
        //获得联系人的id
        contact_id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        //获得联系人的lookup_id
        lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        //将当前联系人与quickContactBadge控件关联
        badge2.assignContactUri(ContactsContract.Contacts.getLookupUri(contact_id, lookupKey));
    }
}
