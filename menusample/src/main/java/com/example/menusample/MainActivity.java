package com.example.menusample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ContextualMenu mContextualMenu = new ContextualMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //创建浮动菜单
        registerForContextMenu(findViewById(R.id.btn_floating));
        //创建上下文操作菜单
        findViewById(R.id.btn_context).setOnClickListener(this);
        //创建Floating上下文操作菜单
        findViewById(R.id.btn_context_floating).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_context_floating)).setCustomSelectionActionModeCallback(new TextFloatingCallback(MainActivity.this));
        //创建popup菜单
        findViewById(R.id.btn_popup).setOnClickListener(this);
    }

    /**
     * 第一种：创建普通菜单。
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return clickMenuItem(item);
    }
//*****************************************************************************************************

    /**
     * 第二种：创建浮动菜单。
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return clickMenuItem(item);
    }


    boolean clickMenuItem(MenuItem item) {
        //noinspection SimplifiableIfStatement
        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_context:
                mContextualMenu.startActionMode(MainActivity.this);
                break;
            case R.id.btn_context_floating:
                if (Build.VERSION.SDK_INT < 23) {
                    return;
                }
                FloatingContextualMenu mFloatingContextualMenu = new FloatingContextualMenu();
                mFloatingContextualMenu.startActionMode(MainActivity.this);
                break;
            case R.id.btn_popup:
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return clickMenuItem(item);
                    }
                });
                popup.show();
                break;
        }
    }

}
