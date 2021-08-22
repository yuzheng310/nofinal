package com.example.nofinal.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

/*
 * 创建这个Fragment的目的是为了规划和规范代码的书写
 * 因为可以在这个Fragment里面将所有要设计到的功能都写一个空函数，然后在具体子类里面重写
 * 目前的具体功能是得到布局id，具体的操作，toast的使用
 *
 * update 2021.8.15
 * by  yuzheng
 */

public abstract class BaseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initData(view);
        return view;
    }

    /**
     * 得到布局id
     */
    protected abstract int getLayoutId();

    /**
     * 逻辑代码
     */
    protected abstract void initData(View view);

    /**
     * 短的吐司
     */
    protected void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * dp转成px
     */
    public int dip2px(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

