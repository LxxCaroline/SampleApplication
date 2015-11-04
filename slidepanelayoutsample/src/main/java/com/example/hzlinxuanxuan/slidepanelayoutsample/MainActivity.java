package com.example.hzlinxuanxuan.slidepanelayoutsample;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.color.transparent;

public class MainActivity extends AppCompatActivity {

    private SlidingPaneLayout layout;
    private ListView lvMenu;
    private TextView tvContent;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (SlidingPaneLayout) findViewById(R.id.slide_layout);
        lvMenu = (ListView) findViewById(R.id.lv);
        tvContent = (TextView) findViewById(R.id.tv_content);
        ivBack= (ImageView) findViewById(R.id.iv_back);
        layout.openPane();
        layout.setSliderFadeColor(getResources().getColor(transparent));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getData());
        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvContent.setText(((TextView) view).getText().toString());
                layout.closePane();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.openPane();
            }
        });
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("" + i);
        }
        return data;
    }

}
