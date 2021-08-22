package com.example.nofinal.dohttp;

import com.example.nofinal.bean.HostProjectBean;
import com.example.nofinal.bean.LastProjectBean;
import com.example.nofinal.bean.comBean;
import com.example.nofinal.bean.newsextraBean;
import com.example.nofinal.bean.starPageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
/*
    总接口
    目前具有：
        1：最新消息
        2：最热消息
        3：啓動節目圖片（API已經失效）
...
 */

public interface API {
    //获取最新消息
    @GET("api/4/news/latest")
    Observable<LastProjectBean> getProject();
    //获取往日消息如果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
    @GET("api/4/news/before/{date}")
    Observable<LastProjectBean> getdateProject(@Path("date") String date);
    //获取最热消息
    @GET("api/3/news/hot")
    Observable<HostProjectBean> getHostProject();
    //獲取啓動界面
    //API已经失效
    @GET("api/4/start-image/1080*1776")
    Observable<starPageBean> getstarpageProject();
    //获取新闻长评论
    @GET("api/4/story/{id}/long-comments")
    Observable<comBean> getlongcoProject(@Path("id") String id);
    //获取新闻短评论
    @GET("api/4/story/{id}/short-comments")
    Observable<comBean> getshortcoProject(@Path("id") String id);
    //获取新闻的额外消息
    @GET("api/4/story-extra/{id}")
    Observable<newsextraBean> getnewextraoProject(@Path("id") String id);
}



