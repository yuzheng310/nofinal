package com.example.nofinal.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.nofinal.dohttp.API;
import com.example.nofinal.Adapter.newslistAdapter;
import com.example.nofinal.Adapter.newsrcyAdapter;
import com.example.nofinal.Fragment.MainPageFragment;
import com.example.nofinal.R;
import com.example.nofinal.bean.HostProjectBean;
import com.example.nofinal.bean.LastProjectBean;
import com.example.nofinal.bean.forstand;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.Serializable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * 现在的思路是想要用一个Fragment来填充主页面的layout
 * 现在虽然Fragment的各个控件都写好了
 * 但是填充到主页面以后却没有任何视图
 * 但是Fragment的一个控件SwipeRefreshView的各个反馈出现
 * 现在处于没什么思路
 * 但是应该不是写错了可能是某个地方有一些不知道的细节
 * 怀疑原因
 * 1.因为我们使用的是retrofit和rxjava请求数据他是多线程的
 * 所以有可能存在一个问题就是Fragment已经初始化完成了但是线程还没完成
 * 这就导致一个问题就是实际传给Activity的Fragment是一个空的Fragment他的各项数据都是还没有搞定的
 * 所以我进行这样的几个测试
 *  1.将几个控件初始化的函数从线程中拿出来放在外面，编译器报错说数据存在空指针。
 *  2.在布局文件里面加了一个textview控件并且给他预设了初始值，结果显示主页面出现了这个控件显示的是他的初始值
 *  3.进行Debug，分别在Fragment中初始化数据那一步打断点和在将Fragment传入Activity处打断点发现首先触发后一个断点
 * 解决思路
 * 1.将请求数据过程进行处理保证可以让Fragment的初始化完成是在初始完数据之后，又因为SwipeRefreshView存在可以保证listview的刷新
 * (写个接口来实现数据的回调，通俗的就是数据初始化完了)
 * 2.让传入主Activity的Fragment可以动态刷新
 *
 * ************************************************************
 * 现在怀疑是我理解错了oncreatview这个方法是在fragment要显示画面的时候才会调用
 * 有可能不存在先后问题
 *
 * ***********************************************************
 * 现在怀疑处理上述问题代码也存在一定问题
 * 因为fragmen都是在最后要显示的时候才会开始加载视图
 * 当然也有可能开始加载视图了但是子线程还没有完成
 * 但是现在确保子线程完成以后才可以完成加载也有问题存在
 *
 *
 *
 * update 2021.8.16
 * by yuzheng
 *
 * ************************************************************
 *
 * 解决了：因为在fragment中不可以直接使用findViewById你得用fragment的那个view然后用view.findViewById
 * 所以我自己创建了一个view然后让这个view去绑定fragment的view不知道为什么不行，然后我直接使用了onCreateView里面创建的view成功了
 *
 * 小领悟：我为什么会自己去创建一个view列？因为在fragment中去绑定控件时发现不可以绑定，必须以这种形式 控件=控件.findViewById() 绑定完后发现主页面无视图，这时候我有点怀疑是
 * 没绑定起，所以我就去搜索了如何在Fragment中进行绑定，得到的答案是去创建一个view然后以view.findViewById()的方式去绑定，结果是仍然不行。（这时其实只要继续沿着这个方向想下去
 * 应该就会想出来我创建的这个view和最终fragment展示的view不是同一个，简单来说就是我将自己想要展示的数据放在了另一个view里面，但是由于我对Fragment的生命周期理解的不够到位所以我又
 * 像大聪明一样有了新的怀疑）当尝试了这种方法绑定仍然不行以后，我开始觉得我的具体操作肯定是没问题的，问题应该是出在逻辑上，在这个思路的指引下我开始思考了
 * 1.有没有可能存在一个情况就是我在初始化数据时网络请求的数据并没有到位但是数据已经初始化完了（此时我代码初始化数据的函数没有在请求完数据之后执行，因为请求数据是用的retrofit和rxjava
 * 实际上我现在也不知道有没有影响）具体解决在怀疑的方法都在上面非常之大聪明
 *
 * 其实现在结合fragment的生命周期来看其实只要把初始化数据的函数写在请求数据之后即可
 * public void onNext(LastProjectBean value) {
 * 处理请求得到的数据
 * 初始化数据
 * }
 * 因为当回调到onNext里面的时候进行完步骤以后Fragment的页面也会跟着刷新（这是Fragment的动态加载）
 *********************************************************************************************
 * 目前已经实现的功能
 * 1：基本上的框架已经搭出来了
 * 想要实现的功能
 *   1.可以在首页看以往的新闻
 *   2.可以收藏喜欢的新闻（并且每次打开都存在）以一个悬浮的加号为途径在主页面的时候点击这个加号可以收藏指定日期的所有文章，在看文章的界面可以收藏正在看的文章
 *   3.侧滑菜单每一个item都有相应的回应
 * **************************************************************************************
 *
 */
public class MainActivity extends AppCompatActivity implements Serializable {
    private Context context;
    private String []storyidArray;//新闻id
    private String []titleArray;
    private String []topStotruriArray;
    private String []topStoryArray;
    private String []topStoryTitleArray;
    private final forstand forstand=new forstand();
    private newslistAdapter newslistAdapter;
    private newsrcyAdapter newsrcyAdapter;
    private String []imageIdArray;//图片资源的数组
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         *实现toolbar和NavigationView的互动
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        initContentFragment();
        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.group_item_github:
                        Toast.makeText(MainActivity.this,"项目主页",Toast.LENGTH_SHORT).show();
                        AlertDialog alert = null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        alert = null;
                        builder = new AlertDialog.Builder(MainActivity.this);
                        alert = builder.setIcon(R.mipmap.back)
                                .setTitle("GitHub地址：")
                                .setMessage("https://github.com/yuzheng310/nofinal\n点击按键复制")
                                .setNegativeButton("复制", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //获取剪贴板管理器：
                                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);// 创建普通字符型ClipData
                                        ClipData mClipData = ClipData.newPlainText("Label", "https://github.com/yuzheng310/nofinal\n");// 将ClipData内容放到系统剪贴板里。
                                        cm.setPrimaryClip(mClipData);
                                        Toast.makeText(MainActivity.this, "你点击了复制按钮~", Toast.LENGTH_SHORT).show();
                                    }
                                }).create();             //创建AlertDialog对象
                        alert.show();                    //显示对话框
                        break;
                    case R.id.group_item_more:
                        Toast.makeText(MainActivity.this,"更多内容",Toast.LENGTH_SHORT).show();
                        Intent it = new Intent();
                        it.setClass(getApplicationContext(), CollectionActivity.class);
                        startActivity(it);
                        break;
                    case R.id.item_model:
                        Toast.makeText(MainActivity.this,"并没有夜间模式",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_about:
                        Toast.makeText(MainActivity.this,"关于",Toast.LENGTH_SHORT).show();
                        AlertDialog alert2 = null;
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                        alert2 = null;
                        builder2 = new AlertDialog.Builder(MainActivity.this);
                        alert2 = builder2.setIcon(R.mipmap.back)
                                .setTitle("关于：")
                                .setMessage("这是一个仿知乎日报的APP，所有资源来自网络,欢迎点击项目主页下载源码。")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                                    }
                                }).create();             //创建AlertDialog对象
                        alert2.show();                    //显示对话框
                        break;
                }
                item.setCheckable(false);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @SuppressLint("RestrictedApi")
    private void initContentFragment() {
        MainPageFragment fragment = new MainPageFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        context=MainActivity.this;
        //bundle.putSerializable("themeMainInfo", info);
        //bundle.putInt("id", info.getId());
        /*
         * 下面这个判断是用来防止fragment重复添加的
         * 因为add这个给函数存在重复添加的问题
         * 所以我们要判断是否曾经添加过，如果添加过就用以前添加的，如果没有就添加。
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //创建 网络请求接口（api）的实例
        API api = retrofit.create(API.class);
        //发送网络请求
        api.getProject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LastProjectBean>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("MainActivity","开始连接");
                    }
                    @Override
                    public void onNext(LastProjectBean value) {
                        Log.v("MainActivity",value.toString());
                        value.getStories().get(0).setIsno1(true);
                        newslistAdapter=new newslistAdapter(context,value.getStories());
                        newsrcyAdapter=new newsrcyAdapter(value.getStories(),context,"最新消息");
                        forstand.setNewslistAdapter(newslistAdapter);
                        forstand.setNewsrcyAdapter(newsrcyAdapter);
                        bundle.putString("date",value.getData());
                        bundle.putSerializable("forstand",forstand);
                        bundle.putString("haha","hello word");
                        topStoryArray=new String[value.getTop_stories().size()];
                        topStoryTitleArray=new String[value.getTop_stories().size()];
                        topStotruriArray=new String[value.getTop_stories().size()];
                        storyidArray=new String[value.getTop_stories().size()];
                        for (int i = 0; i < value.getTop_stories().size(); i++) {
                            topStoryArray[i]= "";
                            topStoryTitleArray[i]="";
                            topStotruriArray[i]="";
                            storyidArray[i]="";
                            topStoryTitleArray[i]=value.getTop_stories().get(i).getTitle();
                            topStoryArray[i]=value.getTop_stories().get(i).getImage();
                            topStotruriArray[i]=value.getTop_stories().get(i).getUrl();
                            storyidArray[i]=value.getTop_stories().get(i).getId();
                            Log.v("MainActivity",topStoryArray[i]);
                        }
                        bundle.putStringArray("top4kId",storyidArray);
                        bundle.putStringArray("top4k",topStoryArray);
                        bundle.putStringArray("top4kT",topStoryTitleArray);
                        bundle.putStringArray("top4kU",topStotruriArray);
                        count++;
                        if (count==2){
                            fragment.setArguments(bundle);//实例化fragment
                            if ( manager.getFragments().size() == 0) {
                                transaction.add(R.id.frag_ll, fragment);
                                transaction.commit();
                            }
                            count=0;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.v("MainActivity",e.getMessage());
                        Log.v("MainActivity","错误");
                    }
                    @Override
                    public void onComplete() {
                        Log.v("MainActivity","完成");
                    }
                });
        api.getHostProject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HostProjectBean>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("MainActivity","开始连接HOST");
                    }
                    @Override
                    public void onNext(HostProjectBean value) {
                        Log.v("MainActivity",value.toString());
                        //这里面就行想要进行的处理
                        Log.v("32MainActivity",value.getRecent().get(1).getThumbnail());
                        imageIdArray=new String[value.getRecent().size()];
                        titleArray=new String[value.getRecent().size()];
                        for (int i = 0; i < value.getRecent().size(); i++) {
                            imageIdArray[i]= "";
                            titleArray[i]="";
                            titleArray[i]=value.getRecent().get(i).getTitle();
                            imageIdArray[i]=value.getRecent().get(i).getThumbnail();
                            Log.v("MainActivity",imageIdArray[i]);
                            Log.v("MainActivity",titleArray[i]);
                        }
                        bundle.putStringArray("22",titleArray);
                        bundle.putStringArray("23",imageIdArray);
                        count++;
                        if (count==2){
                            fragment.setArguments(bundle);//实例化fragment
                            if ( manager.getFragments().size() == 0) {
                                transaction.add(R.id.frag_ll, fragment);
                                transaction.commit();
                            }
                            count=0;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.v("MainActivity",e.getMessage());
                        Log.v("MainActivity","错误HOST");
                    }
                    @Override
                    public void onComplete() {
                        Log.v("MainActivity","完成HOST");
                    }
                });
    }
}