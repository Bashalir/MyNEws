package com.oc.bashalir.mynews.Models;

public class News {

    private String newsTitle;
    private String newsURL;
    private String newsPhoto;
    private String newsSection;
    private String newsDate;

    public News(String newsTitle, String newsURL, String newsPhoto, String newsSection, String newsDate) {
        this.newsTitle = newsTitle;
        this.newsURL = newsURL;
        this.newsPhoto = newsPhoto;
        this.newsSection = newsSection;
        this.newsDate = newsDate;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public String getNewsPhoto() {
        return newsPhoto;
    }

    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public void setNewsSection(String newsSection) {
        this.newsSection = newsSection;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}
