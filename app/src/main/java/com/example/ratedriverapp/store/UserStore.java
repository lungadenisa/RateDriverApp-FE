package com.example.ratedriverapp.store;

import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.dtos.UserDTO;

import java.util.List;

public class UserStore {
    public static UserDTO loginUser;
    public static UserDTO receiverUser;
    public static List<ReviewDTO> reviewsLoginUser;
    public static List<ReviewDTO> reviewsReceiver;
    public static List<ReviewDTO> reviewsGiver;
}
