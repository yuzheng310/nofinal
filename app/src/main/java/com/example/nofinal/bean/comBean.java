package com.example.nofinal.bean;

import java.util.List;

public class comBean {
    private List<Comments> comments;

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public static class Comments{
        private String author;//用户名
        private String content;//评论内容
        private String avatar;//头像
        private int time;//评论时间
        private String id;//用户id
        private int likes;//获赞数

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLike() {
            return likes;
        }

        public void setLike(int likes) {
            this.likes = likes;
        }
    }
}
