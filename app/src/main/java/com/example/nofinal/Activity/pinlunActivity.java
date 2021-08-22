package com.example.nofinal.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nofinal.Adapter.pinlunAdapter;
import com.example.nofinal.R;
import com.example.nofinal.bean.comBean;
import com.example.nofinal.bean.newsextraBean;
import com.example.nofinal.dohttp.MySubscriber;
import com.example.nofinal.dohttp.dohttp;
import com.example.nofinal.dohttp.forhttpLisener;
import com.example.nofinal.webul.SuperExpandableListView;

import java.util.Objects;

/*
 *用于显示评论
 */
public class pinlunActivity extends AppCompatActivity {
    private boolean flag=false;
    private comBean longcomBean=new comBean();
    private comBean shortcomBean=new comBean();
    private pinlunAdapter pinlunAdapter;
    private Context context;
    private ExpandableListView expandableListView;
    private TextView longnum;
    private TextView shortnum;
    private String likenum;
    private String story_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinlun);
        Intent intent=getIntent();
        expandableListView=findViewById(R.id.comment);
        context=pinlunActivity.this;
        story_id=new String();
        story_id = intent.getStringExtra("story_id");
        forhttpLisener forhttpLisener = new forhttpLisener<comBean>() {
            @Override
            public void onNext(comBean o) {
                longcomBean=o;
                if (flag==false){
                    flag=true;
                }else {
                    pinlunAdapter=new pinlunAdapter(longcomBean,shortcomBean,context);
                    expandableListView.setAdapter(pinlunAdapter);
                    expandableListView.expandGroup(0);
                    flag=false;
                }
            }
        };
        dohttp.getInstance().getLongCommets(new MySubscriber<comBean>(forhttpLisener, context), story_id);
        forhttpLisener forhttpLisener2 = new forhttpLisener<comBean>() {
            @Override
            public void onNext(comBean o) {
                shortcomBean=o;
                if (flag=false){
                    flag=true;
                }else {
                    pinlunAdapter=new pinlunAdapter(longcomBean,shortcomBean,context);
                    expandableListView.setAdapter(pinlunAdapter);
                    expandableListView.expandGroup(0);
                    flag=false;
                }
            }
        };
        dohttp.getInstance().getShortCommets(new MySubscriber<comBean>(forhttpLisener2, context), story_id);
        forhttpLisener forhttpLisener3=new forhttpLisener<newsextraBean>() {
            @Override
            public void onNext(newsextraBean o) {
                likenum=o.getPopularity();
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("新闻点赞数"+likenum);
                setSupportActionBar(toolbar);
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }
        };
        dohttp.getInstance().getnesestra(new MySubscriber<newsextraBean>(forhttpLisener3, context), story_id);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this,"新闻详情",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }
}
