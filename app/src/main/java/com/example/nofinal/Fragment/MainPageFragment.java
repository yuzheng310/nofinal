package com.example.nofinal.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.view.ViewConfigurationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.nofinal.Activity.webActivity;
import com.example.nofinal.Adapter.GuidePageAdapter;
import com.example.nofinal.Adapter.newsrcyAdapter;
import com.example.nofinal.bean.LastProjectBean;
import com.example.nofinal.dohttp.MySubscriber;
import com.example.nofinal.dohttp.dohttp;
import com.example.nofinal.dohttp.forhttpLisener;
import com.example.nofinal.ref.OnLoadListener;
import com.example.nofinal.R;
import com.example.nofinal.ref.SwipeRefreshView;
import com.example.nofinal.bean.forstand;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
  * 创建这个Fragment是为了让他来填充主页面
  * 小领悟：表面上我们使用一个Fragment去填充主页面好像就会导致主页面不变，
  * 但是因为我们在Fragment的组件中加了一个上拉刷新的组件，所有在上拉时Fragment发生变化
  * 进而主页面变化
  *
  * 现在这个页面具有的功能是一个由ViewPager和layout组成的头部轮播视窗和一个由SwipeRefreshView和listview组成的可刷新的新闻条
  * 代码基本完工但是现在存在一些漏洞详见MainActivity
  * update 2021.8.16
  * by     yuzheng
  *
  * *****************************************
  * 解决了头部视图在轮播完一圈以后点击头部视图的新闻会导致闪退
  * 原因：导致的原因是实现头部视窗无限自动轮播时itemid在不断向上加（这是自己设置的模式即无限item）但是又因为新闻的数量是有限的所以当第二轮访问
  * 到新闻的各项数组时就会发生数组的下标越界的情况
  * 解决方法：下标改为currentItem%storyidArray.length头部视图的各项数据都要进行类似的改动
  *
  * update 2021.8.31
  *
  * by yuzheng
  */
public class MainPageFragment extends BaseFragment{
    private forstand forstand=new forstand();
    private String date;
    SwipeRefreshView swipeRefreshLayout = null;
    private Activity mActivity;
    private ViewPager vp;
    private TextView textView;
    private String []storyidArray;//新闻id
    private String []StotruriArray;//新闻url数组
    private String []titleArray;//新闻标题数组
    private String []imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private boolean isLooper;
    private ListView listView;
    private LinearLayout headLinear;
    private int headPrePosition = 0;
    private int mTouchSlop;//viewpager滑动距离临界值
    private int count = 0;
    @Override
    public void onAttach(Context context) {
        mActivity=(Activity)context;
        super.onAttach(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    protected void initData(View view) {
        assert this.getArguments() != null;
        date=this.getArguments().getString("date");
        storyidArray=this.getArguments().getStringArray("top4kId");
        imageIdArray=this.getArguments().getStringArray("top4k");
        titleArray=this.getArguments().getStringArray("top4kT");
        StotruriArray=this.getArguments().getStringArray("top4kU");
        ViewConfiguration configuration = ViewConfiguration.get(mActivity);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        swipeRefreshLayout=view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1, R.color.swipe_color_1, R.color.swipe_color_1, R.color.swipe_color_1);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("MainActivity","DIANJIyemian");
                /*
                 *这句话可以通知下拉刷新
                 */
                swipeRefreshLayout.setRefreshing(false);
                //上拉加载
            }
        });
        swipeRefreshLayout.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                //下拉加载
                forhttpLisener listener = new forhttpLisener<LastProjectBean>() {
                    @Override
                    public void onNext(LastProjectBean LastProjectBean) {
                        //forstand.getNewsrcyAdapter().getStories().clear();
                        LastProjectBean.getStories().get(0).setIsno1(true);
                        forstand.getNewsrcyAdapter().getStories().addAll(LastProjectBean.getStories());
                        forstand.getNewsrcyAdapter().setDate(date);
                        forstand.getNewsrcyAdapter().notifyDataSetChanged();
                        date=LastProjectBean.getData();
                        swipeRefreshLayout.setLoading(false);
                    }
                };
                dohttp.getInstance().getHomeData(new MySubscriber<LastProjectBean>(listener, getActivity()), date);
            }
        });
        //listView=view.findViewById(R.id.contentList);
        forstand= (com.example.nofinal.bean.forstand) this.getArguments().getSerializable("forstand");
        TextView tile=new TextView(mActivity);
        /*
        弃用
        tile.setText("最新消息");
        tile.setPadding(15,5,15,5);*/
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        forstand.getNewsrcyAdapter().setContext(getActivity());
        rv.setAdapter(forstand.getNewsrcyAdapter());
        //listView.addHeaderView(tile);
        //listView.setAdapter(forstand.getNewslistAdapter());
        initViewPager(view);
        /*listView.setOnItemClickListener((parent, view1, position, id) -> {
            try {
                Log.v("MainActivity","DIANJI");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        headLinear= view.findViewById(R.id.yuandian);
        initPoint();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLooper = true;
                while (isLooper){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //这里是设置当前页的下一页
                            vp.setCurrentItem(vp.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
    }
     @SuppressLint("ClickableViewAccessibility")
     private void initViewPager(View view) {
         vp = view.findViewById(R.id.guide_vp);
         //实例化图片资源
         viewList = new ArrayList<>();
         //获取一个Layout参数，设置为全屏
         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                 LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
         //循环创建View并加入到集合中
         for (String s : imageIdArray) {
             //new ImageView并设置全屏和图片资源
             NiceImageView imageView = new NiceImageView(Objects.requireNonNull(mActivity));
             Glide.with(MainPageFragment.this)
                     .load(s)
                     .into(imageView);
             imageView.setLayoutParams(params);
             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
             viewList.add(imageView);
         }
         //View集合初始化好后，设置Adapter
         vp.setAdapter(new GuidePageAdapter(viewList));
         //设置滑动监听
         textView=view.findViewById(R.id.decri);
         textView.setText(titleArray[0]);
         vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float v, int i1) {
             }
             @Override
             public void onPageSelected(int position) { ;
                 position=position%imageIdArray.length;
                 ImageView image = (ImageView) headLinear.getChildAt(position);
                 ImageView pImage = (ImageView) headLinear.getChildAt(headPrePosition);
                 image.setImageResource(R.drawable.page_now);
                 pImage.setImageResource(R.drawable.page);
                 textView.setText(titleArray[position]);
                 headPrePosition = position;
             }

             @Override
             public void onPageScrollStateChanged(int position) {
             }
         });
         vp.setOnTouchListener(new View.OnTouchListener() {
             int touchFlag = 0;
             float x = 0, y = 0;
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 switch (event.getAction()) {
                     case MotionEvent.ACTION_DOWN:
                         touchFlag = 0;
                         x = event.getX();
                         y = event.getY();
                         break;
                     case MotionEvent.ACTION_MOVE:
                         float xDiff = Math.abs(event.getX() - x);
                         float yDiff = Math.abs(event.getY() - y);
                         if (xDiff < mTouchSlop && xDiff >= yDiff)
                             touchFlag = 0;
                         else
                             touchFlag = -1;
                         break;
                     case MotionEvent.ACTION_UP:
                         if (touchFlag == 0) {
                             try {
                             int currentItem = vp.getCurrentItem();
                             Intent it = new Intent();
                             it.setClass(getActivity(), webActivity.class);
                             it.putExtra("story_id",storyidArray[currentItem%storyidArray.length]);
                             it.putExtra("story_url",StotruriArray[currentItem% StotruriArray.length]);
                             it.putExtra("story_title",titleArray[currentItem%titleArray.length]);
                             it.putExtra("story_imag",imageIdArray[currentItem%imageIdArray.length]);
                             startActivity(it);}
                             catch (Exception e){
                                 e.printStackTrace();
                             }
                         }
                         break;
                 }
                 return false;
             }
         });
     }
     private void initPoint() {
         for (int i = 0; i < imageIdArray.length; i++) {
             ImageView image = new ImageView(mActivity);
             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(6), dip2px(6));
             params.leftMargin = dip2px(8);
             image.setLayoutParams(params);
             image.setImageResource(R.drawable.page);
             if (i==0)
                 image.setImageResource(R.drawable.page_now);
             headLinear.addView(image);
         }
     }
}
