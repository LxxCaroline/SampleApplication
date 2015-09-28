package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import java.io.IOException;

/**
 * Created by Think on 2015/9/4.
 */
public class MediaActivity extends Activity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
    }

    public void audioClick(View view) {
        switch (view.getId()) {
            case R.id.iv_replay:
                 /*
                 * Resets the MediaPlayer to its uninitialized state. After calling
                 * this method, you will have to initialize it again by setting the
                 * data source and calling prepare().
                 * 将mediaPlayer重置到未被初始化状态
                 */
                if (player != null)
                    player.reset();
                player=null;
            case R.id.iv_play:
                if (player == null) {
                    //通过mediaPlayer类的create方法指定res/raw目录中的音频资源
                    player = MediaPlayer.create(this, R.raw.music);
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setOnCompletionListener(this);
                    try {
                        //播放音频资源前，必须调用prepare方法完成一些准备工作
                        /*
                         * 这句话会抛出exception，网上的回答是ignore该exception
                         */
                        player.prepare();
                    } catch (IllegalStateException e) {
                        Log.d("tag","IllegalStateException");
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        Log.d("tag","IllegalArgumentException");
                        player.prepareAsync();
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        Log.d("tag","SecurityException");
                        player.prepareAsync();
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //开始播放
                player.start();
                break;
            case R.id.iv_pause:
                if (player != null && player.isPlaying()) {
                    player.pause();
                }
                break;
            case R.id.iv_stop:
                if (player != null && player.isPlaying()) {
                    player.stop();
                    player.reset();
                    player = null;
                }
                break;
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
        player=null;
        Toast.makeText(this, "结束播放，已释放资源", Toast.LENGTH_SHORT).show();
    }
}
