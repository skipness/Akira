package model;

public class SearchModel {

    String adult;
    String backdrop_path;
    long id;
    String org_title;
    String rel_day;
    String poster_path;
    float popularity;
    float vote_arg;
    int vote_count;

    public SearchModel(String adult, String backdrop_path, long id, String org_title, String rel_day, String poster_path, float popularity, float vote_arg, int vote_count){
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.org_title = org_title;
        this.rel_day = rel_day;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.vote_arg = vote_arg;
        this.vote_count = vote_count;
    }

    public SearchModel() {

    }

    public void setAdult(String adult){
        this.adult = adult;
    }

    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path = backdrop_path;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setOrg_title(String org_title){
        this.org_title = org_title;
    }

    public void setRel_day(String rel_day){
        this.rel_day = rel_day;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }

    public void setPopularity(float popularity){
        this.popularity = popularity;
    }

    public void setVote_arg(float vote_arg){
        this.vote_arg = vote_arg;
    }

    public void setVote_count(int vote_count){
        this.vote_count = vote_count;
    }

    public String getAdult(){
        return adult;
    }

    public String getBackdrop_path(){
        return backdrop_path;
    }

    public long getId(){
        return id;
    }

    public String getOrg_title(){
        return org_title;
    }

    public String getRel_day(){
        return rel_day;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public float getPopularity(){
        return popularity;
    }

    public float getVote_arg(){
        return vote_arg;
    }

    public int getVote_count(){
        return vote_count;
    }

}
