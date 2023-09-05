package com.example.ratedriverapp.dtos;

public class CreateReviewDTO {
    private int stars;

    private String comment;

    private int giverId;

    private int receiverId;

    public CreateReviewDTO(int stars, String comment, int giverId, int receiverId) {
        this.stars = stars;
        this.comment = comment;
        this.giverId = giverId;
        this.receiverId = receiverId;
    }
}
