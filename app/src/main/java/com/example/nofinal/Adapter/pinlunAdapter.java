package com.example.nofinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.nofinal.R;
import com.example.nofinal.bean.comBean;
import com.shehuan.niv.NiceImageView;

/*
 * 为评论的ExpandableListView写的Adapter
 * 分为两个大类
 * 1 长评论
 * 2 短评lun
 * 小类有
 * 1 用户图像
 * 2 用户名字
 * 3 用户获赞数
 * 4 评论内容
 */
public class pinlunAdapter extends BaseExpandableListAdapter {
    private comBean longcomBean;
    private comBean shortcomBean;
    private Context mContext;


    public pinlunAdapter(comBean longcomBean,comBean shortcomBean,Context mContext){
        this.longcomBean=longcomBean;
        this.shortcomBean=shortcomBean;
        this.mContext=mContext;
    }
    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition==0){
            if (longcomBean.getComments()!=null){
            return longcomBean.getComments().size();}
            else {
                return 0;}
        }
        else{
            if (shortcomBean.getComments()!=null){
            return shortcomBean.getComments().size();
            }else{
                return 0;}
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition==0){
            return "长评论";
        }
        else return "短评论";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition==0){
            return longcomBean.getComments().get(childPosition);
        }
        else return shortcomBean.getComments().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holderGroup;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.pinlun_group, parent, false);
            holderGroup=new ViewHolderGroup();
            holderGroup.group=(TextView)convertView.findViewById(R.id.group);
            holderGroup.groupnum=(TextView)convertView.findViewById(R.id.itemnum);
            holderGroup.view=convertView.findViewById(R.id.nodata);
            convertView.setTag(holderGroup);
        }else {
            holderGroup=(ViewHolderGroup)convertView.getTag();
        }
        if (groupPosition==0){
            holderGroup.group.setText("长评论");
            if (longcomBean.getComments()==null||longcomBean.getComments().size()==0){
                holderGroup.groupnum.setText("0");
                holderGroup.view.setVisibility(View.VISIBLE);
            }
            else {
                holderGroup.groupnum.setText(longcomBean.getComments().size()+"");
                holderGroup.view.setVisibility(View.GONE);
            }
        }else {
            holderGroup.group.setText("短评论");
            holderGroup.groupnum.setText(shortcomBean.getComments().size()+"");
            holderGroup.view.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.pinlun_item, parent, false);
            holder=new ViewHolder();
            holder.personImage=(NiceImageView)convertView.findViewById(R.id.pesonImage);
            holder.personName=(TextView)convertView.findViewById(R.id.pesonname);
            holder.like=(TextView)convertView.findViewById(R.id.like);
            holder.com=(TextView)convertView.findViewById(R.id.com);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (groupPosition==0){
            if (longcomBean.getComments()!=null){
            Glide.with(mContext)
                    .load(longcomBean.getComments().get(childPosition).getAvatar())
                    .into(holder.personImage);
            holder.personName.setText(longcomBean.getComments().get(childPosition).getAuthor());
            holder.like.setText(longcomBean.getComments().get(childPosition).getLike()+"");
            holder.com.setText(longcomBean.getComments().get(childPosition).getContent());
            }
        }else {
            if (shortcomBean.getComments()!=null){
            Glide.with(mContext)
                    .load(shortcomBean.getComments().get(childPosition).getAvatar())
                    .into(holder.personImage);
            holder.personName.setText(shortcomBean.getComments().get(childPosition).getAuthor());
            holder.like.setText(shortcomBean.getComments().get(childPosition).getLike()+"");
            holder.com.setText(shortcomBean.getComments().get(childPosition).getContent());
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class ViewHolder{
        private NiceImageView personImage;//头像
        private TextView personName;//用户名
        private TextView like;//点赞数
        private TextView com;//评论内容
    }
    private static class ViewHolderGroup{
        private TextView group;//评论类型
        private TextView groupnum;
        private ImageView view;
    }

}
