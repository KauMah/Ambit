package com.example.Ambit;

/**
 * Created by Kaushik on 9/5/2015.
 */
public class Messages {
    private String imageUrl;
    private String sender;
    private String text;
    private String id;

    @SuppressWarnings("unused")
    private Messages(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    Messages(String url, String sndr, String txt, String id){
        this.id = id;
        this.imageUrl = url;
        this.sender = sndr;
        this.text = txt;
    }
}
