package com.example.nofinal.dohttp;

import com.example.nofinal.bean.LastProjectBean;
import com.example.nofinal.bean.comBean;
import com.example.nofinal.bean.newsextraBean;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * 这个类是写来封装所有网络操作的
 * 目的是为了让网络操作显的不繁琐
 */
public class dohttp {
    private API api;
    Retrofit retrofit;
    private final static int TIME_OUT = 20;
    private static dohttp dohttp;
    private dohttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://news-at.zhihu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }
    public static dohttp getInstance() {
        if (dohttp == null)
            dohttp = new dohttp();
        return dohttp;
    }
    /**
     * 获取最新数据
     *
     * @param subscriber
     * @param date
     */
    public void getHomeData(Observer<LastProjectBean> subscriber, String date) {
        Observable observable = api.getdateProject(date);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取新闻长评论
     *
     * @param subscriber
     * @param id
     */

    public void getLongCommets(Observer<comBean> subscriber, String id){
        Observable observable = api.getlongcoProject(id);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取新闻短评论
     *
     * @param subscriber
     * @param id
     */
    public void getShortCommets(Observer<comBean> subscriber, String id){
        Observable observable = api.getshortcoProject(id);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取新闻额外消息
     *
     * @param subscriber
     * @param id
     */
    public void getnesestra(Observer<newsextraBean> subscriber, String id){
        Observable observable = api.getnewextraoProject(id);
        toSubscribe(observable, subscriber);
    }


    private void toSubscribe(Observable o, Observer s) {
                o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer) s);
    }
}
