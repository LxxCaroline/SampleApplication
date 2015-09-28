package com.think.linxuanxuan.sampleapplication.mainActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.think.linxuanxuan.sampleapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String CATEGORY="com.think.linxuanxuan.category.SAMPLE_CODE";
    public static final String INTENT_EXTRA = "com.think.linxuanxuan.Path";
    private ListView lv_sample;
    private boolean mIsRoot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_sample = (ListView) findViewById(R.id.lv_sample);
        Intent intent = getIntent();
        String path = intent.getStringExtra(INTENT_EXTRA);

        if (path == null) {
            path = "";
            mIsRoot = true;/* 顶层activity列表 */
        } else {
            setTitle(path);
        }

        lv_sample.setAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
        lv_sample.setTextFilterEnabled(true);
        lv_sample.setOnItemClickListener(mOnItemClickListener);
    }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CATEGORY);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefix.length() == 0 || label.startsWith(prefix)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0]
                        : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData,
                            nextLabel,
                            activityIntent(
                                    info.activityInfo.applicationInfo.packageName,
                                    info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel,
                                browseIntent(prefix.equals("") ? nextLabel
                                        : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

//        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    /**
     * 根据activity类生成示例activity intent
     *
     * @param pkg
     * @param componentName
     * @return
     */
    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    /**
     * 根据path生成该list activity
     *
     * @param path
     * @return
     */
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra(INTENT_EXTRA, path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener(){


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map map = (Map) parent.getItemAtPosition(position);

            Intent intent = (Intent) map.get("intent");
            startActivity(intent);
        }
    };

}
