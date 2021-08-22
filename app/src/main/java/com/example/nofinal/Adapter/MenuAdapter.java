package com.example.nofinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nofinal.R;

/*
 *侧滑菜单的adapter
 *目前实现功能是
 *1.正常显示侧滑
 * 待实现功能是
 *1.可以将主界面中的某个日期的行为收藏下来排列在首页之后
 *2.点击后可以跳转到对应界面
 *
 * update 2021.8.15
 * by yuzheng
 */

public class MenuAdapter extends BaseAdapter {
    private Context context;

    public MenuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        /*
         * 返回1是因为不返回1的话侧面菜单中的首页无法显示
         * 具体原因未知
         *
         * update 2021.8.15
         * by yuzheng
         */
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        /*
         *此处是对getView的优化使用
         * 加上判断是为了避免重复创建优化使用
         */
        if (convertView==null){
            holder=new Holder();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.menu_item, null);
            convertView= LayoutInflater.from(context).inflate(R.layout.menu_item,null);
            holder.view_top=convertView.findViewById(R.id.black_line_top);
            holder.view_bottom=convertView.findViewById(R.id.black_line_bottom);
            holder.home_icon= (ImageView) convertView.findViewById(R.id.menu_home_icon);
            holder.home_add = (ImageView) convertView.findViewById(R.id.menu_item_add);
            holder.home_tv= (TextView) convertView.findViewById(R.id.menu_item_tv);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        /*
         *注释部分为预留代码
         * 预留的是可以让侧面的菜单发生变化，即可以随着主界面显示的不同而显示的不同
         * 现在代码不可行
         * 原因未知
         *
         * update 2020.8.15
         * by yuzheng
         */
       // if (position==0){//首页
            holder.view_top.setVisibility(View.VISIBLE);
            holder.view_bottom.setVisibility(View.VISIBLE);
            holder.home_icon.setVisibility(View.VISIBLE);
            holder.home_add.setImageResource(R.drawable.menu_next);
       /* }else{
            holder.view_top.setVisibility(View.GONE);
            holder.view_bottom.setVisibility(View.GONE);
            holder.home_icon.setVisibility(View.GONE);
            holder.home_add.setImageResource(R.drawable.menu_add);
        }
        holder.home_tv.setText(info.getName());*/
        holder.home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.tvClick(info);
            }
        });
        holder.home_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.addClick(info);
            }
        });
        return convertView;
    }
    class Holder{
        View view_top,view_bottom;
        ImageView home_icon,home_add;
        TextView home_tv;
    }
}
