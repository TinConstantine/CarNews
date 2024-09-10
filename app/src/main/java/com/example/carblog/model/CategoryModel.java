package com.example.carblog.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class CategoryModel implements Serializable{
    public int id;
    public int count;
    public String description;
    public String link;
    public String name;
    public String slug;
    public String taxonomy;
    public int parent;
    public ArrayList<Object> meta;
    public Links _links;
}

 class About implements Serializable{
    public String href;
}

     class Collection implements Serializable{
    public String href;
}

 class Cury implements Serializable{
    public String name;
    public String href;
    public boolean templated;
}

 class Links  implements Serializable{
    @SerializedName("wp:post_type")
    public ArrayList<Self> self;
    public ArrayList<Collection> collection;
    public ArrayList<About> about;
    public ArrayList<WpPostType> wpPost_type;
    public ArrayList<Cury> curies;
}



 class Self implements Serializable{
    public String href;
}

class WpPostType implements Serializable{
    public String href;
}

