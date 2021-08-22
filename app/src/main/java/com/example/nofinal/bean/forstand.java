package com.example.nofinal.bean;

import com.example.nofinal.Adapter.newsrcyAdapter;

import java.io.Serializable;

public class forstand implements Serializable {
    private int stand;
    private com.example.nofinal.Adapter.newslistAdapter newslistAdapter;
    private newsrcyAdapter newsrcyAdapter;

    public int getStand() {
        return stand;
    }

    public void setStand(int stand) {
        this.stand = stand;
    }

    public com.example.nofinal.Adapter.newslistAdapter getNewslistAdapter() {
        return newslistAdapter;
    }

    public void setNewslistAdapter(com.example.nofinal.Adapter.newslistAdapter newslistAdapter) {
        this.newslistAdapter = newslistAdapter;
    }

    public com.example.nofinal.Adapter.newsrcyAdapter getNewsrcyAdapter() {
        return newsrcyAdapter;
    }

    public void setNewsrcyAdapter(com.example.nofinal.Adapter.newsrcyAdapter newsrcyAdapter) {
        this.newsrcyAdapter = newsrcyAdapter;
    }
}
