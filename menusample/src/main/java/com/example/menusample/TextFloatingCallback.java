package com.example.menusample;

import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by hzlinxuanxuan on 2016/5/25.
 */
public class TextFloatingCallback extends ActionMode.Callback2 {

    private Activity actv;

    TextFloatingCallback(Activity actv) {
        this.actv = actv;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Toast.makeText(actv, item.getTitle(), Toast.LENGTH_SHORT).show();
        mode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }
}
