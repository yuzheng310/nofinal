package com.example.nofinal.bean;

import androidx.annotation.NonNull;

public class starPageBean {
    private String text;
    private String img;

    @Override
    public String toString() {
        return "text="+text+"   img="+img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
