package com.example.carblog.model;

import com.example.carblog.api.CarBlogUrl;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
public class PostModel implements Serializable {
    public int id;
    public Date date;
    public Date date_gmt;
    public Date modified;
    public Date modified_gmt;
    public String slug;
    public String status;
    public String type;
    public String link;
    public Title title;
    public Content content;
    public Excerpt excerpt;
    public int author;
    public int featured_media;
    public String comment_status;
    public String ping_status;
    public boolean sticky;
    public String template;
    public String format;

    public ArrayList<Integer> categories;
    public ArrayList<Integer> tags;
    public ArrayList<String> class_list;

    @SerializedName("_embedded")
    public Embedded embedded;



    public String getNameAuthor(){
        return embedded.author.get(0).name;
    }

    public String getImage(){
        String urlImage = CarBlogUrl.BASEURL + "wp-content/uploads/";
        return urlImage + embedded.wpFeaturedmedia.get(0).mediaDetails.file;
//        return embedded.wpFeaturedmedia.get(0).source_url;
    }

    public String getTitle(){
        return title.rendered;
    }

    public String getSubTitle(){
        return excerpt.rendered;
    }


    public String getContentPost(){
        return content.rendered.replaceAll("src=\"http://localhost/otofun/", "src=\"http://192.168.138.1/otofun/");}
//        return content.rendered;
}


 class Content implements Serializable{
    public String rendered;
    public boolean myprotected;
}



 class Excerpt implements Serializable{
    public String rendered;
    public boolean myprotected;
}

 class Title implements Serializable{
    public String rendered;
}


class Embedded implements Serializable{
    @SerializedName("author")
    public ArrayList<AuthorPost> author;
    @SerializedName("wp:featuredmedia")
    public ArrayList<WpFeaturedmedium> wpFeaturedmedia;
}

 class WpFeaturedmedium implements Serializable{
    public String source_url;
    @SerializedName("media_details")
    MediaDetails mediaDetails;

}

 class AuthorPost implements Serializable{
     public boolean embeddable;
     public String href;
     public int id;
     public String name;
     public String url;
     public String description;
     public String link;
     public String slug;
     public AvatarUrls avatar_urls;

 }

 class AvatarUrls implements Serializable{
    @SerializedName("24")
    public String _24;
    @SerializedName("48")
    public String _48;
    @SerializedName("96")
    public String _96;
}

class MediaDetails implements Serializable{
    String file;
}
