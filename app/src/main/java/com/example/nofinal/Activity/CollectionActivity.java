package com.example.nofinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nofinal.Adapter.collectionAdapter;
import com.example.nofinal.Adapter.newsrcyAdapter;
import com.example.nofinal.R;
import com.example.nofinal.bean.CollectionBean;
import com.example.nofinal.dosql.DB.DBDao;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CollectionActivity extends AppCompatActivity {
    private Context context;
    private List<CollectionBean> stort;
    private collectionAdapter newsrcyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_bean);
        context=CollectionActivity.this;
        stort=DBDao.getInstance().query();
        Collections.reverse(stort);
        newsrcyAdapter=new collectionAdapter(stort,context);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(newsrcyAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_home_24);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this,"首页",Toast.LENGTH_SHORT).show();
                finish();
                break; }
        return true;
    }
}