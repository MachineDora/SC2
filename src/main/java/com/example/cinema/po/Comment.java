package com.example.cinema.po;

import java.sql.Date;
import java.sql.Timestamp;

public class Comment {
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论者名称
     */
    private String name;
    /**
     * 评论时间
     */
    private Date time;
    /**
     * 电影ID
     */
    private int movieId;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

}
