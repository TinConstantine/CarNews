package com.example.carblog.model;

import com.example.carblog.R;
import com.example.carblog.api.CarBlogUrl;

public class RadioModel {
    public static final RadioModel[] listRadio = {new RadioModel("VOV 1", CarBlogUrl.VOV1, R.drawable.img_vov1, false)
            , new RadioModel("V0V 2", CarBlogUrl.VOV2, R.drawable.img_vov2, false)
            , new RadioModel("VOV 3", CarBlogUrl.VOV3, R.drawable.img_vov3, false)
//            , new RadioModel("VOV 4", CarBlogUrl.VOV4, R.drawable.img_vov4, false)
            , new RadioModel("VOV 5", CarBlogUrl.VOV5, R.drawable.img_vov5, false)
            , new RadioModel("VOV 6", CarBlogUrl.VOV6, R.drawable.img_vov6, false)
            , new RadioModel("VOV Giao thông Hà Nội", CarBlogUrl.VOV_GIAO_THONG_HN, R.drawable.img_vovgthn,false)
            , new RadioModel("VOV Giao thông HCM", CarBlogUrl.VOV_GIAO_THONG_HCM, R.drawable.img_vovgthcm, false), };
    private final String name;
    private final String url;
    private final int idImg;


    public RadioModel(String name, String url, int idImg, boolean isEnable ) {
        this.name = name;
        this.url = url;
        this.idImg = idImg;

    }


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getIdImg() {
        return idImg;
    }


}
