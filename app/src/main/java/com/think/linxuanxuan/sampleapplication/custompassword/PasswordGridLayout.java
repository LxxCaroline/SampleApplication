package com.think.linxuanxuan.sampleapplication.custompassword;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * Created by Think on 2015/8/16.
 */
public class PasswordGridLayout extends LinearLayout implements MyPasswordEditText.OnMyDelKeyEventListener, TextWatcher {

    private static final int DEFAULT_PASSWORDLENGTH = 6;
    private static final int DEFAULT_TEXTSIZE = 16;
    private static final String DEFAULT_TRANSFORMATION = "●";
    private static final int DEFAULT_LINECOLOR = 0xaa888888;
    private static final int DEFAULT_GRIDCOLOR = 0xffffffff;

    private int textSize = DEFAULT_TEXTSIZE;
    private int lineWidth;
    private int lineColor;
    private int gridColor;
    private Drawable lineDrawable;
    private Drawable outerLineDrawable;

    private int passwordLength;
    //set the password char, such as * or ●
    private String passwordTransformationString;
    private int passwordType;
    private String[] passwordArr;
    private TextView[] textViews;
    //widget
    private MyPasswordEditText myPasswordEditText;

    //listener
    private OnMyPasswordChangedListener onMyPasswordChangedListener;

    private PasswordTransformationMethod transformationMethod;

    public PasswordGridLayout(Context context) {
        super(context, null);
    }

    public PasswordGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initViews(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.gridPasswordView, 0, 0);

        int textSize = ta.getDimensionPixelSize(R.styleable.gridPasswordView_textSize, -1);
        if (textSize != -1) {
            this.textSize = UiUtil.px2sp(context, textSize);
        }

        lineWidth = (int) ta.getDimension(R.styleable.gridPasswordView_lineWidth, UiUtil.dp2px(getContext(), 1));
        lineColor = ta.getColor(R.styleable.gridPasswordView_lineColor, DEFAULT_LINECOLOR);
        gridColor = ta.getColor(R.styleable.gridPasswordView_gridColor, DEFAULT_GRIDCOLOR);
        lineDrawable = ta.getDrawable(R.styleable.gridPasswordView_lineColor);
        if (lineDrawable == null)
            lineDrawable = new ColorDrawable(lineColor);
        outerLineDrawable = generateBackgroundDrawable();

        passwordLength = ta.getInt(R.styleable.gridPasswordView_passwordLength, DEFAULT_PASSWORDLENGTH);
        passwordTransformationString = ta.getString(R.styleable.gridPasswordView_passwordTransformation);
        if (TextUtils.isEmpty(passwordTransformationString))
            passwordTransformationString = DEFAULT_TRANSFORMATION;

        passwordType = ta.getInt(R.styleable.gridPasswordView_passwordType, 0);

        //recycle the useless object once we have used it.
        ta.recycle();

        passwordArr = new String[passwordLength];
        textViews = new TextView[passwordLength];
    }

    private void initViews(Context context) {
        super.setBackgroundDrawable(outerLineDrawable);
        setOrientation(HORIZONTAL);

        transformationMethod = new MyPasswordTransformationMethod(passwordTransformationString);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.epaysdk_view_gpv_gpv, this);
        myPasswordEditText = (MyPasswordEditText) findViewById(R.id.inputView);

        //set the max length of edit text
        myPasswordEditText.setMaxEms(passwordLength);
        myPasswordEditText.addTextChangedListener(this);
        myPasswordEditText.setDelKeyEventListener(this);
        setCustomAttr(myPasswordEditText);

        textViews[0] = myPasswordEditText;

        int index = 1;
        while (index < passwordLength) {
            View dividerView = inflater.inflate(R.layout.epaysdk_view_gpv_divider, null);
            LayoutParams dividerParams = new LayoutParams(lineWidth, LayoutParams.MATCH_PARENT);
            dividerView.setBackgroundDrawable(lineDrawable);
            addView(dividerView, dividerParams);

            TextView textView = (TextView) inflater.inflate(R.layout.epaysdk_view_gpv_textview, null);
            setCustomAttr(textView);
            LayoutParams textViewParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
            addView(textView, textViewParams);

            textViews[index] = textView;
            index++;
        }
    }

    private void setCustomAttr(TextView view) {
        view.setTextColor(Color.BLACK);
        view.setTextSize(textSize);

        int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        switch (passwordType) {

            case 1:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;

            case 2:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                break;

            case 3:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
                break;
        }
        view.setInputType(inputType);
        view.setTransformationMethod(transformationMethod);
    }

    private GradientDrawable generateBackgroundDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(gridColor);
        drawable.setStroke(lineWidth, lineColor);
        drawable.setCornerRadius(10f);
        return drawable;
    }

    @Override
    public void onDeleteClick() {
        for (int i = passwordArr.length - 1; i >= 0; i--) {
            if (passwordArr[i] != null) {
                passwordArr[i] = null;
                textViews[i].setText("");
                break;
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("tag", "char sequence is ;" + s);
        if (s == null || s.length() == 0)
            return;
        String temp = s.toString();
        if (temp.length() == 1) {
            passwordArr[0] = temp;
            /**
             * because origin text in edit text is "", if we set text for edit text here
             * it will invoke onTextChanged, so that it will cause stack overflow.
             */
//            myPasswordEditText.setText(temp);
        } else if (temp.length() == 2) {
            String newNum = temp.substring(1);
            for (int i = 0; i < passwordArr.length; i++) {
                if (passwordArr[i] == null) {
                    passwordArr[i] = newNum;
                    textViews[i].setText(newNum);
                    break;
                }
            }
            myPasswordEditText.setText(passwordArr[0]);
            myPasswordEditText.setSelection(1);
            notifyTextChanged();
        }

    }

    private void notifyTextChanged() {
        if (onMyPasswordChangedListener == null)
            return;

        String currentPsw = getPassWord();
        onMyPasswordChangedListener.onChanged(currentPsw);

        if (currentPsw.length() == passwordLength)
            onMyPasswordChangedListener.onMaxLength(currentPsw);

    }

    public String getPassWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordArr.length; i++) {
            if (passwordArr[i] != null)
                sb.append(passwordArr[i]);
        }
        return sb.toString();
    }

    /**
     * Register a callback to be invoked when password changed.
     */
    public void setOnPasswordChangedListener(OnMyPasswordChangedListener listener) {
        this.onMyPasswordChangedListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when the password changed or is at the maximum length.
     */
    public interface OnMyPasswordChangedListener {

        /**
         * Invoked when the password changed.
         */
        public void onChanged(String psw);

        /**
         * Invoked when the password is at the maximum length.
         */
        public void onMaxLength(String psw);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
