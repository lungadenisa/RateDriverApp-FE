package com.example.ratedriverapp.dtos;

public class UserDTO {
    public int id;

    public String email;
    public String password;
    public String carIdentityNumber;
    public String username;
    public float averageStars;
    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO(String carIdentityNumber, String email, String password, String username) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.carIdentityNumber = carIdentityNumber;
    }

}
