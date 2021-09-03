package com.example.nofinal.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.nofinal.Activity.webActivity;
import com.example.nofinal.R;
import com.example.nofinal.bean.CollectionBean;
import com.example.nofinal.dosql.DB.DBDao;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

    // ① 创建Adapter
public class collectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements Serializable {
    private Context context;
    private List<CollectionBean> stories;
    public collectionAdapter(List<CollectionBean> stories,Context context) {
        this.stories=stories;
        this.context=context;
    }

    //② 创建ViewHolder
    public static class VH2 extends RecyclerView.ViewHolder{
        public final TextView date;
        public final TextView title;
        public final ImageView imageView;
        public VH2(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.titleTv);
            imageView=(ImageView) v.findViewById(R.id.itemImage);
            date=(TextView) v.findViewById(R.id.title);
        }
    }

    //③ 在Adapter中实现3个方法
    @Override
    public int getItemCount() {
        return stories.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        RecyclerView.ViewHolder holder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collectionitem, parent, false);
        return  holder=new VH2(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectionBean story = stories.get(position);
        ((VH2)holder).date.setText(story.getDate());
        ((VH2)holder).title.setText(story.getStory_title());
        if(story.getStory_imag()!= null){
                Glide.with(context)
                     .load(story.getStory_imag())
                     .into(((VH2)holder).imageView);
        }else {
                ((VH2)holder).imageView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = null;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final String[] lesson = new String[]{"打开这个新闻", "删除这个新闻"};
                    alert = builder.setIcon(R.mipmap.back)
                            .setTitle("请选择您要进行的操作")
                            .setItems(lesson, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "你选择了" + lesson[which], Toast.LENGTH_SHORT).show();
                                    if(lesson[which]=="打开这个新闻"){
                                        Log.v("MainActivity","rcDIANJI"+position);
                                        CollectionBean story = stories.get(position);
                                        Intent intent = new Intent();
                                        intent.putExtra("story_title",story.getStory_title());
                                        intent.putExtra("story_imag",story.getStory_imag());
                                        intent.putExtra("story_url", story.getStory_url());
                                        intent.putExtra("story_id",story.getId());
                                        intent.setClass(context, webActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                        context.startActivity(intent);
                                    }
                                    else {
                                        DBDao.getInstance().delete(story.getStory_title());
                                        stories.clear();
                                        stories=DBDao.getInstance().query();
                                        Collections.reverse(stories);
                                        notifyDataSetChanged();
                                    }
                                }
                            }).create();
                    alert.show();
                }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}

