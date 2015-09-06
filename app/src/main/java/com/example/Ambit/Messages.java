package com.example.Ambit;

/**
 * Created by Kaushik on 9/5/2015.
 */
public class Messages {
    private String imageUrl;
    private String sender;
    private String text;

    @SuppressWarnings("unused")
    private Messages(){}

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

    Messages(String url, String sndr, String txt){
        this.imageUrl = url;
        this.sender = sndr;
        this.text = txt;
    }
}
