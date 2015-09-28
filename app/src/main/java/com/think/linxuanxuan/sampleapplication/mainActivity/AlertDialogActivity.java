package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.think.linxuanxuan.sampleapplication.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;


public class AlertDialogActivity extends Activity {

    private String[] items = new String[]{"浙江", "江苏", "福建", "天津", "河北"};
    boolean[] flags = new boolean[]{false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);
    }

    public void showDialog(View view) {
        AlertDialog dialog;
        AlertDialog.Builder builder;
        switch (view.getId()) {
            case R.id.btn_common:
                builder = new AlertDialog.Builder(this).setTitle("是否覆盖该文件？");
                builder.setPositiveButton("覆盖", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(AlertDialogActivity.this).setMessage("已覆盖").show();
                    }
                });
                builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(AlertDialogActivity.this).setMessage("已忽略").show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(AlertDialogActivity.this).setMessage("已取消").show();
                    }
                });
                //该函数是异步执行的，执行完该句，并不会等dialog被dismiss掉，会直接执行之后的语句
                dialog = builder.show();
                break;
            case R.id.btn_list:
                builder = new AlertDialog.Builder(this).setTitle("请选择省份");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "选择" + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = builder.show();
                break;
            case R.id.btn_singlelist:
                builder = new AlertDialog.Builder(this).setTitle("请选择省份");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "选择" + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = builder.show();
                break;
            case R.id.btn_multilist:
                builder = new AlertDialog.Builder(this).setTitle("请选择省份");
                builder.setMultiChoiceItems(items, flags, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        flags[which] = isChecked;
                    }
                });
                builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = "你选择了";
                        for (int i = 0; i < flags.length; i++) {
                            result += flags[i] ? items[i] : "";
                        }
                        Toast.makeText(AlertDialogActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog = builder.show();
                break;
            case R.id.btn_progressdialog:
                pDialog = new ProgressDialog(AlertDialogActivity.this);
                pDialog.setTitle("正在加载");
                //除了水平显示，还有ProgressDialog.STYLE_SPINNER圆形滚动
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setProgress(progress);
                pDialog.setButton("stop", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeMessages(1);
                        progress = 0;
                        pDialog.setProgress(progress);
                    }
                });
                pDialog.show();
                handler.sendEmptyMessage(1);
                break;
            case R.id.btn_custom:
                View item = LayoutInflater.from(this).inflate(R.layout.view_login, null);
                final EditText etAccount = (EditText) item.findViewById(R.id.et_account);
                final EditText etPsw = (EditText) item.findViewById(R.id.et_psw);
                builder = new AlertDialog.Builder(this).setTitle("请先登录");
                builder.setView(item);
                builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "account:" + etAccount.getText().toString() + "," +
                                "psw:" + etPsw.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
            case R.id.btn_custom2:
                builder = new AlertDialog.Builder(this).setTitle("请先登录");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                /*
                 * 对话框按钮单击事件的处理过程，alertDialog中有一个alertController，它在处理对话框按钮时会为每一个按钮添加一个onClick事件
                 * 而这个事件类的对象就是上面的mButtonHandler，在这个单击事件中首先会通过发送消息的方式调用为按钮设置的单击事件
                 * 也就是setPositiveButton传进来的onClickListener,在触发完按钮的单击事件后会通过发送消息的方式调用dismiss方法来关闭对话框
                 * 而在alertController类中定义了一个全局的ButtonHandler类型的mHandler变量，因此只要使用我们自己handler对象替换buttonHandler
                 * 就可以阻止调用dismiss方法
                 * 接下来在show方法之前修改mHandler变量的值，使用java反射机制
                 * 具体请看Android开发权威指南 page233
                 */
                try {
                    dialog = builder.create();
                    Field field = dialog.getClass().getDeclaredField("mAlert");
                    //允许访问private变量
                    field.setAccessible(true);
                    //获得mAlert变量的值
                    Object obj = field.get(dialog);
                    field = obj.getClass().getDeclaredField("mHandler");
                    field.setAccessible(true);
                    field.set(obj, new ButtonHandler(dialog));
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_location:
                builder = new AlertDialog.Builder(this).setTitle("请先登录");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog = builder.create();
                Window window = dialog.getWindow();
                /*
                 * 也可以通过定位X和Y的位置来显示
                 */
                window.setGravity(Gravity.TOP | Gravity.LEFT);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 0.3f;
                dialog.show();
                break;
        }
    }

    class ButtonHandler extends Handler {
        private WeakReference<DialogInterface> mdialog;

        public ButtonHandler(DialogInterface dialog) {
            mdialog = new WeakReference<DialogInterface>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DialogInterface.BUTTON_POSITIVE:
                case DialogInterface.BUTTON_NEGATIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                    ((DialogInterface.OnClickListener) msg.obj).onClick(mdialog.get(), msg.what);
                    break;
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progress >= 100) {
                progress = 0;
                pDialog.dismiss();
            } else {
                progress++;
                pDialog.incrementProgressBy(1);
                handler.sendEmptyMessageDelayed(1, 50);
            }

        }
    };
    ProgressDialog pDialog;
    private int progress = 0;
}
