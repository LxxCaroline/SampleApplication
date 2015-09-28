package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.think.linxuanxuan.sampleapplication.R;
import com.think.linxuanxuan.sampleapplication.other.Product;
import com.think.linxuanxuan.sampleapplication.other.XML2Product;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FileActivity extends Activity {

    @InjectView(R.id.tv_memory)
    TextView tvMemory;
    @InjectView(R.id.iv_sd)
    ImageView ivSD;
    @InjectView(R.id.tv_sax_xml)
    TextView tvSaxXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.inject(this);
    }

    public void accessFile(View view) {
        switch (view.getId()) {
            case R.id.btn_writememory:
                try {
                    OutputStream os = openFileOutput("file.txt", Activity.MODE_PRIVATE);
                    String content = "hello world";
                    os.write(content.getBytes("utf-8"));
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_readmemory:
                try {
                    InputStream is = openFileInput("file.txt");
                    byte[] buffer = new byte[100];
                    int byteCount = is.read(buffer);
                    String result = new String(buffer, 0, byteCount, "utf-8");
                    tvMemory.setText(result);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_writesd:
                try {
                    FileOutputStream fileOS = new FileOutputStream(android.os.Environment.getExternalStorageDirectory
                            () + "/image.png");
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    fileOS.write(baos.toByteArray());
                    baos.close();
                    fileOS.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_readsd:
                try {
                    FileInputStream fileIS = new FileInputStream(android.os.Environment.getExternalStorageDirectory
                            () + "/image.png");
                    Bitmap bitmap = BitmapFactory.decodeStream(fileIS);
                    ivSD.setImageBitmap(bitmap);
                    fileIS.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_sax_xml:
                try {
                    InputStream is = getResources().openRawResource(R.raw.products);
                    XML2Product xml2Product = new XML2Product();
                    //会抛出IOException, SAXException
                    android.util.Xml.parse(is, Xml.Encoding.UTF_8, xml2Product);
                    List<Product> data = xml2Product.getProducts();
                    String result = "";
                    for (Product product : data) {
                        result += product.toString();
                    }
                    tvSaxXml.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
