package com.example.menusample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by hzlinxuanxuan on 2016/5/25.
 */
@TargetApi(23)
public class FloatingContextualMenu extends ActionMode.Callback2 {

    private static ActionMode mFloatingActionMode;
    private Activity actv;

    void startActionMode(Activity actv) {
        this.actv = actv;
        if (mFloatingActionMode != null) {
            return;
        }
        mFloatingActionMode = actv.startActionMode(this, ActionMode.TYPE_FLOATING);
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
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mFloatingActionMode = null;
    }

    //控制floating contextual menu显示的位置，默认显示在tool bar的位置
    @Override
    public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
        outRect.set(200, 500, 200 + view.getWidth(), 500 + view.getHeight());
    }
}
