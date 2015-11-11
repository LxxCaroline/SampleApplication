package com.example.hzlinxuanxuan.suuportsample;

import android.os.AsyncTask;

/**
 * Created by hzlinxuanxuan on 2015/11/9.
 */
public class DelayedTask extends AsyncTask<Void,Void,Void> {

    private final IDelayedListener listener;
    private final int secondInMillis;
    public static boolean isFinished = false;

    public DelayedTask(int secondsInMillis, IDelayedListener listener){
        secondInMillis = secondsInMillis;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        isFinished = false;
        try {
            Thread.sleep(secondInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        isFinished = true;
        if(listener != null){
            listener.onDelayed();
        }
    }

    public interface IDelayedListener {
        void onDelayed();
    }
}
