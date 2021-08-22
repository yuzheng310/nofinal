package com.example.nofinal.Adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;


public class GuidePageAdapter extends PagerAdapter {

    private List<View> viewList;

    public GuidePageAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if (viewList != null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % viewList.size();
        container.addView(viewList.get(newPosition));
        return viewList.get(newPosition);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int newPosition = position % viewList.size();
        container.removeView(viewList.get(newPosition));
    }
}

