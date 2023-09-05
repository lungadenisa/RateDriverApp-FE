package com.example.ratedriverapp.network;

import com.example.ratedriverapp.dtos.CreateReviewDTO;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.dtos.UserDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @POST("login")
    Call<UserDTO> login(@Body UserDTO userDTO);

    @POST("register")
    Call<ResponseBody> register(@Body UserDTO userDTO);

    @GET("review/get-reviews-by-receiver/{receiverId}")
    Call<List<ReviewDTO>> getReviewsByReceiverId(@Path("receiverId") int receiverId);

    @GET("review/get-reviews-by-giver/{giverId}")
    Call<List<ReviewDTO>> getReviewsByGiverId(@Path("giverId") int giverId);

    @GET("user/get-by-car-number")
    Call<UserDTO> searchUserByCarIdentityNumber(@Query("carIdentityNumber") String carIdentityNumber);

    @POST("review/create")
    Call<ReviewDTO> createReview(@Body CreateReviewDTO review);
}
