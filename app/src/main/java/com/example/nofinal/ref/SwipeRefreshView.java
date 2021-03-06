package com.example.nofinal.ref;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.nofinal.R;
import com.example.nofinal.ref.OnLoadListener;


/**
 * 自定义View继承SwipeRefreshLayout，添加上拉加载更多的布局属性
 */

public class SwipeRefreshView extends SwipeRefreshLayout {

    private final int mScaledTouchSlop;
    private final View mFooterView;
    private RecyclerView mListView;
    private LinearLayoutManager layoutManager ;
    private OnLoadListener mOnLoadListener;

    /**
     * 正在加载状态
     */
    private boolean isLoading;

    public SwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 填充底部加载布局
        mFooterView = View.inflate(context, R.layout.load_animation, null);

        // 表示控件移动的最小距离，手移动的距离大于这个距离才能拖动控件
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        System.out.println("====" + mScaledTouchSlop);
    }

    /*
     *这一段是对Swipe的重写
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 获取ListView,设置ListView的布局位置
        if (mListView == null) {
            // 判断容器有多少个孩子
            if (getChildCount() > 0) {
                // 判断第一个孩子是不是ListView
                if (getChildAt(0) instanceof RecyclerView) {
                    // 创建ListView对象
                    mListView = (RecyclerView) getChildAt(0);
                    layoutManager = (LinearLayoutManager) mListView.getLayoutManager();
                    // 设置ListView的滑动监听
                    setListViewOnScroll();
                }
            }
        }
    }

    /**
     * 在分发事件的时候处理子控件的触摸事件
     *
     * @param ev
     * @return
     */
    private float mDownY, mUpY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 移动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动过程中判断时候能下拉加载更多
                if (canLoadMore()) {
                    // 加载数据
                    loadData();
                }

                break;
            case MotionEvent.ACTION_UP:
                // 移动的终点
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否满足加载更多条件
     *
     * @return
     */
    private boolean canLoadMore() {
        // 1. 是上拉状态
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
        if (condition1) {
            System.out.println("是上拉状态");
        }

        // 2. 当前页面可见的item是最后一个条目
        boolean condition2 = false;
        if (mListView != null && mListView.getAdapter() != null) {
            condition2 = layoutManager.findLastCompletelyVisibleItemPosition() == (layoutManager.getItemCount() - 1);
        }

        if (condition2) {
            System.out.println("是最后一个条目");
        }
        // 3. 正在加载状态
        boolean condition3 = !isLoading;
        if (condition3) {
            System.out.println("不是正在加载状态");
        }
        return condition1 && condition2 && condition3;
    }

    /**
     * 处理加载数据的逻辑
     */
    private void loadData() {
        System.out.println("加载数据...");
        if (mOnLoadListener != null) {
            // 设置加载状态，让布局显示出来
            setLoading(true);
            mOnLoadListener.onLoad();
        }

    }

    /**
     * 设置加载状态，是否加载传入boolean值进行判断
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        // 修改当前的状态
        isLoading = loading;
        if (isLoading) {
            // 显示布局
            Log.v("MainActivity","显示布局");
            //mListView.addFooterView(mFooterView);
        } else {
            // 隐藏布局
            Log.v("MainActivity","隐藏布局");

            // 重置滑动的坐标
            mDownY = 0;
            mUpY = 0;
        }
    }


    /**
     * 设置ListView的滑动监听
     */
    private void setListViewOnScroll() {
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState) {
                if (canLoadMore()) {
                    // 加载数据
                    loadData();
                }
            }

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }
}


