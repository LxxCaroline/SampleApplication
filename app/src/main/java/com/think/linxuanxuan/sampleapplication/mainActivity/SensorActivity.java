package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.other.Compass;

/**
 * 利用z轴的方向来写个罗盘，利用x和y轴的方向写了平衡小球的滚动
 * 这里需要注意的是有些手机的加速度传感器并没有用，测试的时候发现1+手机加速度传感器没用
 */
public class SensorActivity extends Activity implements SensorEventListener {

    private TextView tvSensor;
    private TextView tvRotate;
    private Compass compass;

    private SensorManager sm;
    //加速度传感器
    private Sensor aSensor;
    //磁场传感器
    private Sensor mSensor;
    //陀螺仪传感器
    private Sensor gSensor;

    //加速度传感器的values数组值
    private float[] accelerometerValues = new float[3];
    //磁场传感器的values数组值
    private float[] magneticFieldValues = new float[3];

    //获取xyz三个方向的values数组值
    private float[] values = new float[3];
    //根据加速度和磁场传感器的value计算出来的values值
    private float[] R = new float[9];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.think.linxuanxuan.sampleapplication.R.layout.activity_sensor);
        tvSensor = (TextView) findViewById(com.think.linxuanxuan.sampleapplication.R.id.tv_sensor);
        tvRotate = (TextView) findViewById(com.think.linxuanxuan.sampleapplication.R.id.tv_rotate);
        compass = (Compass) findViewById(com.think.linxuanxuan.sampleapplication.R.id.compass);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    private void updateCompass() {
        sm.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);
        // 要经过一次数据格式的转换，转换为度
        values[0] = (float) Math.toDegrees(values[0]);

        compass.setCurrentDegree((int) values[0]);
        //values[1] = (float) Math.toDegrees(values[1]);
        //values[2] = (float) Math.toDegrees(values[2]);

        tvSensor.setText(values[0] + "");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = sensorEvent.values;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = sensorEvent.values;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //当value很小的时候，说明手机几乎为静止状态，不改变原有的旋转方向
            if (Math.abs(sensorEvent.values[0]) > 0.003f) {
                //当sensorEvent.values[2]的值小于0时为顺时针旋转
                tvRotate.setText(sensorEvent.values[2] < 0 ? "顺时针  " : "逆时针   ");
                tvRotate.append(sensorEvent.values[0] + "");
                compass.setClockwise(sensorEvent.values[2] < 0);
                return;
            }
        }
        updateCompass();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onPause() {
        /*
         * 很关键的部分：注意，说明文档中提到，即使activity不可见的时候，感应器依然会继续的工作，测试的时候可以发现，没有正常的刷新频率
    	 * 也会非常高，所以一定要在onPause方法中关闭触发器，否则讲耗费用户大量电量，很不负责。
    	 * */
        sm.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sm.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
