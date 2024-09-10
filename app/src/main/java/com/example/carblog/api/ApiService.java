package com.example.carblog.api;

import com.example.carblog.model.CategoryModel;
import com.example.carblog.model.PostModel;
import com.example.carblog.model.RegisterMessage;
import com.example.carblog.model.user.UserJwt;
import com.example.carblog.model.user.UserInfoModel;
import com.example.carblog.model.user.UserRole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    ApiService API_SERVICE = new Retrofit.Builder()
            .baseUrl(CarBlogUrl.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("wp-json/wp/v2/categories")
    Call<ArrayList<CategoryModel>> getAllCategory();

    @GET("wp-json/wp/v2/posts?_embed")
    Call<ArrayList<PostModel>> getAllPost(@Query("page") int pageIndex);

    @GET("wp-json/wp/v2/posts?_embed")
    Call<ArrayList<PostModel>> getPostByCategory(@Query("categories") int idTerm, @Query("page") int pageIndex);

    @FormUrlEncoded
    @POST("wp-json/jwt-auth/v1/token")
    Call<UserJwt> getJwtToken(@Field("username") String username, @Field("password") String password);

    @GET("wp-json/userrole/v1/get-role/{id}")
    Call<UserRole> getRoleUser(@Path("id") int userId);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("wp-json/wp/v2/users/me")
    Call<UserInfoModel>  getInfoUser(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("wp-json/wp/v2/posts?_embed&status=pending")
    Call<ArrayList<PostModel>> getPendingPost(@Header("Authorization") String token, @Query("page") int pageIndex);

    @FormUrlEncoded
    @POST("wp-json/custom/v1/register")
    Call<RegisterMessage> registerAccount(@Field("email") String email, @Field("fullName") String fullName, @Field("password") String password);

    @FormUrlEncoded
    @PUT("wp-json/wp/v2/posts/{id}")
    Call<Objects> updateStatusPost(@Path("id") int idPost, @Header("Authorization") String token ,@Field("status") String status);

    @GET("wp-json/wp/v2/posts/{id}")
    Call<PostModel> getDetailPost(@Path("id") int id, @Header("Authorization") String token);

    @DELETE("wp-json/wp/v2/posts/{id}")
    Call<Objects> deletePost(@Path("id") int id, @Header("Authorization") String token);

    @GET("wp-json/wp/v2/posts?_embed")
    Call<ArrayList<PostModel>> getPostBySearch(@Query("page") int pageIndex, @Query("search")String search);

}
