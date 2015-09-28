package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SocketActivity extends Activity {

    @InjectView(R.id.et_port)
    TextView tvPort;
    @InjectView(R.id.tv_amsg)
    TextView tvAMsg;
    @InjectView(R.id.tv_bmsg)
    TextView tvBMsg;
    @InjectView(R.id.tv_cmsg)
    TextView tvCMsg;

    private Handler handler;

    private MyChat achat, bchat, cchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.inject(this);
        achat = new MyChat(tvAMsg);
        bchat = new MyChat(tvBMsg);
        cchat = new MyChat(tvCMsg);
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    tvPort.append(msg.obj + "  ");
                } else if (msg.what == 2) {
                    tvAMsg.append(msg.obj + "");
                }
            }
        };
    }

    public void startServer(View view) {
        Thread serverThread = new Thread("server") {
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket();
                    //绑定ip地址和端口
                    server.bind(new InetSocketAddress("192.168.31.197", 1234));
                    int count = 0;
                    while (count < 3) {
                        //用来接受客户端消息，如果接收不到，则一直阻塞
                        Socket s = server.accept();
                        final TelephonyManager manager = (TelephonyManager) getBaseContext().getSystemService((Context.TELEPHONY_SERVICE));
                        //向客户端输出手机设备号的hashcode
                        s.getOutputStream().write(("mobile device id hashcode:" + String.valueOf(manager.getDeviceId()).hashCode()).getBytes());
                        s.close();
                        count++;
                    }
                    server.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        serverThread.start();
    }

    public void scanPort(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 200; i++) {
                            try {
                                Socket s = new Socket();
                                SocketAddress sa = new InetSocketAddress("10.240.35.18", i);
                                s.connect(sa, 50);
                                Message m = handler.obtainMessage();
                                m.what = 1;
                                m.obj = i;
                                handler.sendMessage(m);
                                s.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SocketActivity.this, "扫描完成", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                thread.start();
                Toast.makeText(this, "开始扫描", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void join(View view) {
        switch (view.getId()) {
            case R.id.btn_ajoin:
                achat.startChat();
                ((Button) findViewById(R.id.btn_ajoin)).setEnabled(false);
                ((Button) findViewById(R.id.btn_asay)).setEnabled(true);
                break;
            case R.id.btn_bjoin:
                bchat.startChat();
                ((Button) findViewById(R.id.btn_bjoin)).setEnabled(false);
                ((Button) findViewById(R.id.btn_bsay)).setEnabled(true);
                break;
            case R.id.btn_cjoin:
                cchat.startChat();
                ((Button) findViewById(R.id.btn_cjoin)).setEnabled(false);
                ((Button) findViewById(R.id.btn_csay)).setEnabled(true);
                break;
        }
    }

    public void send(View view) {
        switch (view.getId()) {
            case R.id.btn_asay:
                achat.send("i am a\n");
                break;
            case R.id.btn_bsay:
                bchat.send("i am b\n");
                break;
            case R.id.btn_csay:
                cchat.send("i am c\n");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (achat != null)
            achat.finishChat();
        if (bchat != null)
            bchat.finishChat();
        if (cchat != null)
            cchat.finishChat();
        super.onDestroy();
    }

    class MyChat {
        //一个专门用来读记录
        ReadThread readThread;
        //一个专门用来发消息
        HandlerThread writeThread;
        private TextView tvMsg;
        private Socket s;
        private Handler writeHandler;

        MyChat(TextView tv) {
            this.tvMsg = tv;
        }

        public void startChat() {
            if (readThread == null) {
                readThread = new ReadThread();
                readThread.start();
            }
        }

        public void send(String content) {
            if (writeThread == null) {
                writeThread = new HandlerThread("write");
                writeThread.start();
                writeHandler = new MyHandler(writeThread.getLooper());
            }
            writeHandler.sendEmptyMessage(1);
        }

        public void finishChat() {
            try {
                if (s != null)
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        class MyHandler extends Handler {

            MyHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    if (s != null) {
                        OutputStream os = s.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter bw = new BufferedWriter(osw);
                        bw.write("大家好");
                        bw.flush();
                    } else {
                        Toast.makeText(SocketActivity.this, "你还未进入该房间", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        class ReadThread extends Thread {

            private String result;

            @Override
            public void run() {
                try {
                    s = new Socket("10.240.35.18", 9000);
                    InputStream is = s.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    while ((result = br.readLine()) != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.append(result);
                            }
                        });
                    }
                } catch (IOException e) {
                }
            }
        }
    }

}
