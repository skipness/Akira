package model;

public class ReviewModel {

    String author;
    String review;
    String url;

    public ReviewModel(String author, String review, String url){
        this.author = author;
        this.review = review;
        this.url = url;
    }

    public ReviewModel(){

    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setReview(String review){
        this.review = review;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getAuthor(){
        return author;
    }

    public String getReview(){
        return review;
    }

    public String getUrl(){
        return url;
    }

}
