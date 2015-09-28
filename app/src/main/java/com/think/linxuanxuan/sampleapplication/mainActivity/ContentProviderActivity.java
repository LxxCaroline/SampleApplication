package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.think.linxuanxuan.sampleapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ContentProviderActivity extends Activity {

    @InjectView(R.id.lv_contacts)
    ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        ButterKnife.inject(this);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_contactlist:
                //查询系统中所有的联系人
                Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                //根据cursor创建simpleCursorAdapter
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c,
                        new String[]{ContactsContract.Contacts.DISPLAY_NAME}, new int[]{android.R.id.text1},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                lvContact.setAdapter(adapter);
                break;
        }
    }
}
