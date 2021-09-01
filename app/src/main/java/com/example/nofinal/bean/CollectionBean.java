package com.example.nofinal.bean;

public class CollectionBean {
    private String story_title;
    private String story_imag;
    private String story_url;
    private String date;//收藏时间
    public CollectionBean(String story_title,String story_imag, String story_url,String date) {
        this.story_title = story_title;
        this.story_imag = story_imag;
        this.story_url = story_url;
        this.date = date;
    }
    public CollectionBean() {
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStory_imag() {
        return story_imag;
    }

    public void setStory_imag(String story_imag) {
        this.story_imag = story_imag;
    }

    public String getStory_url() {
        return story_url;
    }

    public void setStory_url(String story_url) {
        this.story_url = story_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
