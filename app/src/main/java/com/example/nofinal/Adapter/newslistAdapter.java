package com.example.nofinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.nofinal.bean.LastProjectBean;
import com.example.nofinal.R;

import java.io.Serializable;
import java.util.List;


public class newslistAdapter extends BaseAdapter implements Serializable {
    private  Context context;
    private List<LastProjectBean.StoryBean> stories;

    public newslistAdapter(Context context, List<LastProjectBean.StoryBean> stories) {
        this.context = context;
        this.stories = stories;
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LastProjectBean.StoryBean story = stories.get(position);
                Holder holder = null;
                if (convertView == null) {
                    holder = new Holder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
                    holder.title = (TextView) convertView.findViewById(R.id.titleTv);
                    holder.image = (ImageView) convertView.findViewById(R.id.itemImage);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.title.setText(story.getTitle());
                if (story.getImages() != null && story.getImages().size() > 0) {
                    Glide.with(context)
                            .load(story.getImages().get(0))
                            .into(holder.image);
                } else {
                    holder.image.setVisibility(View.GONE);
                }
        return convertView;
    }

    class Holder {
        TextView title;
        ImageView image;
    }
}