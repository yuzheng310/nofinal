package com.example.nofinal.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.nofinal.bean.CollectionBean;
import com.example.nofinal.dosql.DB.DBDao;
import com.example.nofinal.webul.MyWebView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;


import com.example.nofinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class webActivity extends AppCompatActivity {
    private String stry_id;
    private String story_title;
    private String story_imag;
    private String story_url;
    private String date;
    private boolean flag=false;
    private MyWebView wView;
    private NestedScrollView nestedScrollView;
    private FloatingActionButton floatingActionButton;
    private long exitTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        stry_id=intent.getStringExtra("story_id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("知乎日报");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_home_24);
        TextView textView = findViewById(R.id.web_title);
        textView.setText(intent.getStringExtra("story_title"));
        story_title=intent.getStringExtra("story_title");
        story_imag=intent.getStringExtra("story_imag");
        story_url=intent.getStringExtra("story_url");
        ImageView imageView = findViewById(R.id.web_image);
        /*
         * 下载图片
         */
        Glide.with(this)
                .load(intent.getStringExtra("story_imag"))
                .into(imageView);
        wView = (MyWebView) findViewById(R.id.web);
        wView.loadUrl(intent.getStringExtra("story_url"));
        wView.setWebViewClient(new WebViewClient() {
            //在webview里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //获取屏幕高度，另外因为网页可能进行缩放了，所以需要乘以缩放比例得出的才是实际的尺寸
                Log.e("HEHE", wView.getContentHeight() * wView.getScale() + "");
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                Log.e("HEHE", "Cookies = " + CookieStr);
                super.onPageFinished(view, url);

            }
        });
        //比如这里做一个简单的判断，当页面发生滚动，显示那个Button
        floatingActionButton=findViewById(R.id.webfb);
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(v -> nestedScrollView.fullScroll(View.FOCUS_UP));
        nestedScrollView=findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY>1650){
                Log.v("23100", "scrollY"+scrollY);
                if (flag){
                floatingActionButton.setVisibility(View.VISIBLE);
                flag=false;
                }
            }
            else {Log.v("23100","scrollY"+scrollY);
            if (!flag){
            floatingActionButton.setVisibility(View.INVISIBLE);
            flag=true;
            }}
        });
/*
 *回到顶部
 */
        /*btn_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wView.setScrollY(0);
                btn_icon.setVisibility(View.GONE);
            }
        });*/
        WebSettings settings = wView.getSettings();
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放
        CookieManager cm = CookieManager.getInstance();




    }

    @Override
    public void onBackPressed() {
        if (wView.canGoBack()) {
            wView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_pinlun:
                Toast.makeText(this,"评论",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent();
                intent1.setClass(getApplicationContext(),pinlunActivity.class);
                intent1.putExtra("story_id",stry_id);
                startActivity(intent1);
                break;
            case android.R.id.home:
                Toast.makeText(this,"首页",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_souchang:
                SimpleDateFormat formatter=new   SimpleDateFormat   ("yyyy年MM月dd日HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                date=formatter.format(curDate);
                CollectionBean collectionBean=new CollectionBean(story_title,story_imag,story_url,date);
                DBDao.getInstance().insert(collectionBean);
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}