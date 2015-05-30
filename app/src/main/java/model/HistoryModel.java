package model;

public class HistoryModel {

    long id;
    String movie_name;

    public HistoryModel(long id, String movie_name){
        this.id = id;
        this.movie_name = movie_name;
    }

    public HistoryModel() {

    }

    public void setId(long id){
        this.id = id;
    }

    public void setMovie_name(String movie_name){
        this.movie_name = movie_name;
    }

    public long getId(){
        return  id;
    }

    public String getMovie_name(){
        return movie_name;
    }
}
