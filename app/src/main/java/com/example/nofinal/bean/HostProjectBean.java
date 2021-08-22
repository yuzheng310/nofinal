package com.example.nofinal.bean;

import java.io.Serializable;
import java.util.List;
/*
   用来接收热门信息
   数据的类型是：一个recent列表，列表中每个元素具有如下数据：news_id(int) url热门消息的具体内容
             thumbnail图片的地址 title热门消息的名字
 */
public class HostProjectBean implements Serializable {
    private List<HostStories> recent;
    @Override
    public String toString() {
        return "recent{"+recent+"}";
    }
    public List<HostStories> getRecent() {
        return recent;
    }

    public void setRecent(List<HostStories> recent) {
        this.recent = recent;
    }

    public static class HostStories implements Serializable{
        private String news_id;
        private String url;
        private String thumbnail;
        private String title;
        @Override
        public String toString() {
            return "news_id="+news_id+"     url="+url+"     thumbnail="+thumbnail+"       title="+title;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNews_id() {
            return news_id;
        }

        public String getUrl() {
            return url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getTitle() {
            return title;
        }
    }

}
