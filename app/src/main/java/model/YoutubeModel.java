package model;

public class YoutubeModel {

    String key;

    public YoutubeModel(String key, String movie_name){
        this.key = key;
    }

    public YoutubeModel(){

    }

    public void setKey(String key){
        this.key = key;
    }

    public String  getKey(){
        return key;
    }
}

