package com.example.nofinal.bean;

import java.io.Serializable;
import java.util.List;
/*
   用来接收最新消息
   数据的类型是：一个String date带的的数据是日期，一个stories列表包含的是image_hue，title文章的标题，
              url文章的具体地址，hint文章的作者名，ga_prefix，images的图片地址，type id。一个top_stories
              包含的是image_hue，hint，top文章作者的名字,url,top文章具体的地址，image top文章图片的具体位置
              title top文章的名字，ga_prefix，typr id
 */

public class LastProjectBean implements Serializable {
    private String date;
    private List<StoryBean> stories;
    private List<TopStoryBean> top_stories;
    @Override
    public String toString() {
        return "LastProjectBean{" +
                "data=" + date +"   story="+ stories +"   topStory="+ top_stories +
                '}';
    }
    public String getData() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public List<TopStoryBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoryBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoryBean implements Serializable {
        private String image_hue;
        private String title;
        private String url;
        private String hint;
        private String ga_prefix;
        private List<String> images;
        private String type;
        private String id;
        private boolean isno1=false;

        @Override
        public String toString() {
            return "LastProjectBean{" +
                    "image_hue=" + image_hue +"   title="+ title +"   url="+ url +"     hint="+hint+"      ga_prefix="+ga_prefix+"      images="+images+"   type="+type+"       id="+id+
                    '}';
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public boolean isIsno1() {
            return isno1;
        }

        public void setIsno1(boolean isno1) {
            this.isno1 = isno1;
        }
    }

    public static class TopStoryBean implements Serializable{
        private String image_hue;
        private String hint;
        private String url;
        private String image;
        private String title;
        private String ga_prefix;
        private String type;
        private String id;

        @Override
        public String toString() {
            return "topstory{" +
                    "image_hue=" + image_hue +"   hint="+ hint +"   url="+ url +"     images="+ image +"      title="+title+"        ga_prefix"+ga_prefix+"         type="+type+"       id="+id+
                    '}';
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }
    }
}
