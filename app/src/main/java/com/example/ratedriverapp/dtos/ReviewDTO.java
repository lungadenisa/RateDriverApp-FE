package com.example.ratedriverapp.dtos;

public class ReviewDTO {
    private int id;

    private int stars;

    private String comment;

    private UserDTO giver;

    private UserDTO receiver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserDTO getGiver() {
        return giver;
    }

    public void setGiver(UserDTO giver) {
        this.giver = giver;
    }

    public UserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDTO receiver) {
        this.receiver = receiver;
    }
}
