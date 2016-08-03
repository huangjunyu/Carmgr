package com.yiwucheguanjia.carmgr.welcome;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.UrlString;

public class Guide extends Activity implements OnViewChangeListener {

    private static final String TAG = "Guide";
    private ScrollLayout mScrollLayout;
    private ImageView[] imgs;
    private int count;
    private int currentItem;
    private Button startBtn;
    private RelativeLayout mainRLayout;
    private LinearLayout pointLLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingGuide();
    }

    protected void loadingGuide() {
        if (sharedPreferences.getString("VERSION", null) == null) {
            setContentView(R.layout.guide);
            init();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("VERSION", UrlString.APP_VERSION);
            editor.commit();
            Log.e("vier","vier");
        }else {
            Intent intent = new Intent(
                    Guide.this,
                    MainActivity.class);
            Log.e("version","version");
            Guide.this.startActivity(intent);
            Guide.this.finish();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayout);
        pointLLayout = (LinearLayout) findViewById(R.id.llayout);
        mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(onClick);
        count = mScrollLayout.getChildCount();
        imgs = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = (ImageView) pointLLayout.getChildAt(i);
            imgs[i].setEnabled(true);
            imgs[i].setTag(i);
        }
        currentItem = 0;
        imgs[currentItem].setEnabled(false);
        mScrollLayout.SetOnViewChangeListener(this);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startBtn:
                    Intent intent = new Intent(
                            Guide.this,
                            MainActivity.class);
                    Guide.this.startActivity(intent);
                    Guide.this.finish();
            }
        }
    };

    @Override
    public void OnViewChange(int position) {
        setcurrentPoint(position);
    }

    private void setcurrentPoint(int position) {
        if (position < 0 || position > count - 1 || currentItem == position) {
            return;
        }
        imgs[currentItem].setEnabled(true);
        imgs[position].setEnabled(false);
        currentItem = position;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
