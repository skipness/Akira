package model;

public class ContentModel {

    String collection;
    int budget;
    String genres;
    String homepath;
    int id;
    String imdb_id;
    String org_title;
    String overview;
    float popularity;
    String backdrop_path;
    String poster_path;
    String rel_date;
    int runtime;
    String status;
    String tag_line;
    float vote_arg;
    int vote_count;
    String author;
    String review;

    public ContentModel(String collection, int budget, String genres, String homepath, int id, String imdb_id, String org_title, String overview, float popularity, String poster_path
    , String rel_date, int runtime, String status, String tag_line, float vote_arg, int vote_count, String author, String review){
        this.collection = collection;
        this. budget = budget;
        this.genres = genres;
        this.homepath = homepath;
        this.id = id;
        this.imdb_id = imdb_id;
        this.org_title = org_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.rel_date = rel_date;
        this.runtime = runtime;
        this.status = status;
        this.tag_line = tag_line;
        this.vote_arg = vote_arg;
        this.vote_count = vote_count;
        this.author = author;
        this.review = review;
    }

    public ContentModel(){

    }

    public void setCollection(String collection){
        this.collection = collection;
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public void setGenres(String genres){
        this.genres = genres;
    }

    public void setHomepath(String homepath){
        this.homepath = homepath;
    }

    public void setId (int id){
        this.id = id;
    }

    public void setImdb_id(String  imdb_id){
        this.imdb_id = imdb_id;
    }

    public void setOrg_title(String org_title){
        this.org_title = org_title;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public void setPopularity(float popularity){
        this.popularity = popularity;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }

    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path = backdrop_path;
    }

    public void setRel_date(String rel_date){
        this.rel_date = rel_date;
    }

    public void setRuntime(int runtime){
        this.runtime = runtime;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setTag_line(String tag_line){
        this.tag_line = tag_line;
    }

    public void setVote_arg(float vote_arg){
        this.vote_arg = vote_arg;
    }

    public void setVote_count(int vote_count){
        this.vote_count = vote_count;
    }

    public String getCollection(){
        return collection;
    }

    public int getBudget(){
       return budget;
   }

    public String getGenres(){
        return genres;
    }

    public String getHomepath(){
        return homepath;
    }

    public int getId(){
        return id;
    }

    public String getImdb_id(){
        return imdb_id;
    }

    public String getOrg_title(){
        return org_title;
    }

    public String getOverview(){
        return overview;
    }

    public float getPopularity(){
        return popularity;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public String getBackdrop_path(){
        return backdrop_path;
    }

    public String getRel_date(){
        return  rel_date;
    }

    public int getRuntime(){
        return runtime;
    }

    public String getStatus(){
        return status;
    }

    public String getTag_line(){
        return tag_line;
    }

    public float getVote_arg(){
        return vote_arg;
    }

    public int getVote_count(){
        return vote_count;
    }

}

