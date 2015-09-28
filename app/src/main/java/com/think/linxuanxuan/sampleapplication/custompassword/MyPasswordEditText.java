package com.think.linxuanxuan.sampleapplication.custompassword;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Created by Think on 2015/8/16.
 */
public class MyPasswordEditText extends EditText {

    private OnMyDelKeyEventListener onMyDelKeyEventListener;

    public MyPasswordEditText(Context context) {
        super(context);
    }

    public MyPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * to intercept the input event拦截键盘输入事件，将得到的输入字符显示在其他地方
     *
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(super.onCreateInputConnection(outAttrs), true);
    }

    public void setDelKeyEventListener(OnMyDelKeyEventListener delKeyEventListener) {
        this.onMyDelKeyEventListener = delKeyEventListener;
    }

    public interface OnMyDelKeyEventListener {
        void onDeleteClick();
    }

    private class MyInputConnection extends InputConnectionWrapper {

        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                if (onMyDelKeyEventListener != null) {
                    onMyDelKeyEventListener.onDeleteClick();
                    return true;
                }
            }
            return super.sendKeyEvent(event);
        }

        /**
         * TODO:感觉有问题，其实不需要sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL)
         *
         * @param beforeLength
         * @param afterLength
         * @return
         */
        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength == 1 && afterLength == 0) {
//                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }
}
