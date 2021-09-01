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
import com.example.nofinal.bean.CollectionBean;
import java.io.Serializable;
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
                    Log.v("MainActivity","rcDIANJI"+position);
                    CollectionBean story = stories.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("story_title",story.getStory_title());
                    intent.putExtra("story_imag",story.getStory_imag());
                    intent.putExtra("story_url", story.getStory_url());
                    intent.putExtra("story_id",story.getId());
                    intent.setClass(context,webActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity(intent);
                }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}

