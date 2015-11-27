/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.think.linxuanxuan.sampleapplication.R;

/**
 * This sample shows the use of the {@link TextSwitcher} View with animations. A
 * {@link TextSwitcher} is a special type of {@link android.widget.ViewSwitcher} that animates
 * the current text out and new text in when
 * {@link TextSwitcher#setText(CharSequence)} is called.
 */
public class TextImageSwitcherActivity extends Activity {
    private TextSwitcher tvSwitcher;
    private ImageSwitcher ivSwitcher;
    private int mCounter = 0;
    private int[] drawable=new int[]{R.drawable.pause,R.drawable.play,R.drawable.replay,R.drawable.stop};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_image_swithcer);
        // Get the TextSwitcher view from the layout
        tvSwitcher = (TextSwitcher) findViewById(R.id.tv_switcher);
        ivSwitcher= (ImageSwitcher) findViewById(R.id.iv_switcher);
        // Set the factory used to create TextViews to switch between.
        tvSwitcher.setFactory(tvFactory);
        ivSwitcher.setFactory(ivFactory);
        /*
         * Set the in and out animations. Using the fade_in/out animations
         * provided by the framework.
         */
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        tvSwitcher.setInAnimation(in);
        tvSwitcher.setOutAnimation(out);
        ivSwitcher.setInAnimation(in);
        ivSwitcher.setOutAnimation(out);
        /*
         * Setup the 'next' button. The counter is incremented when clicked and
         * the new value is displayed in the TextSwitcher. The change of text is
         * automatically animated using the in/out animations set above.
         */
        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter++;
                tvSwitcher.setText(String.valueOf(mCounter));
                ivSwitcher.setImageResource(drawable[mCounter% drawable.length]);
            }
        });
        // Set the initial text without an animation
        tvSwitcher.setCurrentText(String.valueOf(mCounter));
        ivSwitcher.setImageResource(drawable[0]);
    }


    /**
     * The {@link ViewFactory} used to create {@link TextView}s that the
     * {@link TextSwitcher} will switch between.
     */
    private ViewFactory tvFactory = new ViewFactory() {
        @Override
        public View makeView() {
            // Create a new TextView
            TextView t = new TextView(TextImageSwitcherActivity.this);
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(TextImageSwitcherActivity.this, android.R.style.TextAppearance_Large);
            return t;
        }
    };
    private ViewFactory ivFactory = new ViewFactory() {
        @Override
        public View makeView() {
            // Create a new TextView
            ImageView iv = new ImageView(TextImageSwitcherActivity.this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.WRAP_CONTENT, ImageSwitcher.LayoutParams.WRAP_CONTENT));
            return iv;
        }
    };
}
