package com.example.nofinal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.nofinal.Activity.webActivity;
import com.example.nofinal.R;
import com.example.nofinal.bean.LastProjectBean;

import java.io.Serializable;
import java.util.List;

// ① 创建Adapter
public class newsrcyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements Serializable {
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;
    private Context context;
    private List<LastProjectBean.StoryBean> stories;
    private String date;
    public newsrcyAdapter(List<LastProjectBean.StoryBean> stories,Context context,String date) {
        this.stories=stories;
        this.context=context;
        this.date=date;
    }

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public final ImageView imageView;
        public VH(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.titleTv);
            imageView=(ImageView) v.findViewById(R.id.itemImage);
        }
    }
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
        if (viewType==ONE_ITEM){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return holder=new VH(v);}
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
            return  holder=new VH2(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LastProjectBean.StoryBean story = stories.get(position);
        if (holder instanceof VH){
            ((VH)holder).title.setText(story.getTitle());
            if (story.getImages() != null && story.getImages().size() > 0) {
                Glide.with(context)
                        .load(story.getImages().get(0))
                        .into(((VH)holder).imageView);
            } else {
                ((VH)holder).imageView.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("MainActivity","rcDIANJI"+position);
                    LastProjectBean.StoryBean story = stories.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("story_title",story.getTitle());
                    intent.putExtra("story_imag",story.getImages().get(0));
                    intent.putExtra("story_url", story.getUrl());
                    intent.putExtra("story_id",story.getId());
                    intent.setClass(context,webActivity.class);
                    ((Activity) context).startActivityForResult(intent,222);

                }
            });
        }
        else {
            if(date=="最新消息"){
                ((VH2)holder).date.setText(date);
            }
            else ((VH2)holder).date.setText("最早浏览过"+date);
            ((VH2)holder).title.setText(story.getTitle());
            if (story.getImages() != null && story.getImages().size() > 0) {
                Glide.with(context)
                        .load(story.getImages().get(0))
                        .into(((VH2)holder).imageView);
            } else {
                ((VH2)holder).imageView.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("MainActivity","rcDIANJI"+position);
                    LastProjectBean.StoryBean story = stories.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("story_title",story.getTitle());
                    intent.putExtra("story_imag",story.getImages().get(0));
                    intent.putExtra("story_url", story.getUrl());
                    intent.putExtra("story_id",story.getId());
                    intent.setClass(context,webActivity.class);
                    ((Activity) context).startActivityForResult(intent,222);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (stories.get(position).isIsno1()){
            return TWO_ITEM;
        }else return ONE_ITEM;
    }

    public List<LastProjectBean.StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<LastProjectBean.StoryBean> stories) {
        this.stories = stories;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context=context;
    }
}
