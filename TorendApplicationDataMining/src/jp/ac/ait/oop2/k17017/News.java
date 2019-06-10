/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k17017;

import java.time.LocalDate;

/**
 *
 * @author edasan0308
 */
public class News {

    private LocalDate issueDate;
    private long contentId;
    private int genreId;
    private String title;
    private String body;
    private String linkUrl;
    private String imageUrl;
    private String imageHeight;
    private String imageWidth;
    private LocalDate createdDate;
    private String sourceDomain;
    private String sourceName;
    private int rank;

    public News() {

    }

    public News(LocalDate issueDate, long contentId, int genreId, String title, String body, String linkUrl, String imageUrl, String imageHeight, String imageWidth, LocalDate createdDate, String sourceDomain, String sourceName,int rank) {
        this.issueDate = issueDate;
        this.contentId = contentId;
        this.genreId = genreId;
        this.title = title;
        this.body = body;
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.createdDate = createdDate;
        this.sourceDomain = sourceDomain;
        this.sourceName = sourceName;
        this.rank = rank;
    }
    
    public LocalDate getIssueDate(){
        return this.issueDate;
    }
    
    public long getContentId(){
        return this.contentId;
    }
    
    public int getGenreId(){
        return this.genreId;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getBody(){
        return this.body;
    }
    
    public String getLinkUrl(){
        return this.linkUrl;
    }
    
    public String getImageUrl(){
        return this.imageUrl;
    }
    
    public String getImageHeight(){
        return this.imageHeight;
    }
    
    public String getImageWidth(){
        return this.imageWidth;
    }
    
    public LocalDate getCreatedDate(){
        return this.createdDate;
    }
    
    public String getSourceDomain(){
        return this.sourceDomain;
    }
    
    public String getSourceName(){
        return this.sourceName;
    }
    
    public int getrank() {
        return this.rank;
    }
}
