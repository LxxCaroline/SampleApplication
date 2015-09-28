package com.think.linxuanxuan.sampleapplication.mainActivity;


import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import java.util.ArrayList;


public class GestureSampleActivity extends Activity {
    private GestureLibrary library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        library = GestureLibraries.fromRawResource(this, R.raw.gesture);
        library.load();
        GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                /* �������ƿ������û��������ƿ���Щ�����Ե����Ƽ���
                 * �����������Զȸߵ����������û�����ͼ�������Ƶ����ƣ����ڼ��ϵ�һ��λ��
                 * */
                ArrayList<Prediction> predictions = library.recognize(gesture);
                Prediction prediction;
                for (int i = 0; i < predictions.size(); i++) {
                    prediction = predictions.get(i);
                    // ƥ�������
                    if (prediction.score > 1.0) {
                        Log.d("test", "prediction.name:" + prediction.name);
                        Toast.makeText(GestureSampleActivity.this,
                                prediction.name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}
