package com.example.carblog.model.user;

import com.google.gson.annotations.SerializedName;

public class UserInfoModel {
    public int id;
    public String name;
    public String url;
    public String description;
    public String link;
    public String slug;
    public AvatarUrls avatar_urls;
    public class AvatarUrls{
        @SerializedName("24")
        public String _24;
        @SerializedName("48")
        public String _48;
        @SerializedName("96")
        public String _96;
    }


}
