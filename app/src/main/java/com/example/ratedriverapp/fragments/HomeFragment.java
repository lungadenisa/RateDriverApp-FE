package com.example.ratedriverapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratedriverapp.activities.GivenReviewsActivity;
import com.example.ratedriverapp.activities.LogInActivity;
import com.example.ratedriverapp.R;
import com.example.ratedriverapp.adapters.ItemAdaptorByReceiver;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.store.UserStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        Button logout = v.findViewById(R.id.logOutButton);
        TextView fullName = v.findViewById(R.id.textViewFullName);
        TextView email = v.findViewById(R.id.textViewEmail);
        TextView carIdentityNumber = v.findViewById(R.id.textViewCarIdentityNumber);
        TextView starsNumber = v.findViewById(R.id.textViewTotalRatings);

        RatingBar starsBar = v.findViewById(R.id.userRating);
        RecyclerView reviewsRecyclerView = v.findViewById(R.id.reviewsRecyclerView);

        TextView starTextView1 = v.findViewById(R.id.star1TextView);
        TextView starTextView2 = v.findViewById(R.id.stars2TextView);
        TextView starTextView3 = v.findViewById(R.id.stars3TextView);
        TextView starTextView4 = v.findViewById(R.id.stars4TextView);
        TextView starTextView5 = v.findViewById(R.id.stars5TextView);
        TextView totalReviewsText = v.findViewById(R.id.totalReviewsText);

        Button givenReviewsButton = v.findViewById(R.id.givenReviewsButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserStore.loginUser = null;
                UserStore.reviewsLoginUser = new ArrayList<>();

                Toast.makeText(getActivity(), "Logged out successfully!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        fullName.setText(UserStore.loginUser.username);
        email.setText(UserStore.loginUser.email);
        carIdentityNumber.setText(UserStore.loginUser.carIdentityNumber);
        starsNumber.setText(String.valueOf(UserStore.loginUser.averageStars));
        starsBar.setRating(UserStore.loginUser.averageStars);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        reviewsRecyclerView.setLayoutManager(layoutManager);
        ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(UserStore.reviewsLoginUser);
        reviewsRecyclerView.setAdapter(adaptor);

        givenReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GivenReviewsActivity.class);
                startActivity(intent);
            }
        });

        setProgressBar(UserStore.reviewsLoginUser, v);
        starTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ReviewDTO> userReviews = UserStore.reviewsLoginUser;
                userReviews = userReviews.stream().filter((ReviewDTO review) -> review.getStars() == 1).collect(Collectors.toList());
                ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(userReviews);
                reviewsRecyclerView.setAdapter(adaptor);
            }
        });
        starTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ReviewDTO> userReviews = UserStore.reviewsLoginUser;
                userReviews = userReviews.stream().filter((ReviewDTO review) -> review.getStars() == 2).collect(Collectors.toList());
                ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(userReviews);
                reviewsRecyclerView.setAdapter(adaptor);
            }
        });
        starTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ReviewDTO> userReviews = UserStore.reviewsLoginUser;
                userReviews = userReviews.stream().filter((ReviewDTO review) -> review.getStars() == 3).collect(Collectors.toList());
                ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(userReviews);
                reviewsRecyclerView.setAdapter(adaptor);
            }
        });
        starTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ReviewDTO> userReviews = UserStore.reviewsLoginUser;
                userReviews = userReviews.stream().filter((ReviewDTO review) -> review.getStars() == 4).collect(Collectors.toList());
                ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(userReviews);
                reviewsRecyclerView.setAdapter(adaptor);
            }
        });
        starTextView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ReviewDTO> userReviews = UserStore.reviewsLoginUser;
                userReviews = userReviews.stream().filter((ReviewDTO review) -> review.getStars() == 5).collect(Collectors.toList());
                ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(userReviews);
                reviewsRecyclerView.setAdapter(adaptor);
            }
        });
        totalReviewsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ReviewDTO> userReviews = UserStore.reviewsLoginUser;
                ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(userReviews);
                reviewsRecyclerView.setAdapter(adaptor);

            }
        });

        return v;
    }

    private void setProgressBar(List<ReviewDTO> userReviews, View v){
        ProgressBar progressBar1 = v.findViewById(R.id.progressBar1);
        ProgressBar progressBar2 = v.findViewById(R.id.progressBar2);
        ProgressBar progressBar3 = v.findViewById(R.id.progressBar3);
        ProgressBar progressBar4 = v.findViewById(R.id.progressBar4);
        ProgressBar progressBar5 = v.findViewById(R.id.progressBar5);
        int totalReviews = userReviews.size();
        Map<Integer, Long> starRatings = userReviews.stream().collect(Collectors.groupingBy(ReviewDTO::getStars,Collectors.counting()));
        starRatings.forEach((key,value)->{
            Log.d("value", String.valueOf(Math.toIntExact(value)));
            Log.d("key", key.toString());
            Log.d("totalReviews", String.valueOf(totalReviews));
            Log.d("total", String.valueOf((Math.toIntExact(value)/totalReviews)*100));
            if(key == 1){
                progressBar1.setProgress((int) (((double)Math.toIntExact(value)/totalReviews)*100),true);
            }
            if(key == 2){
                progressBar2.setProgress((int) (((double)Math.toIntExact(value)/totalReviews)*100),true);
            }
            if(key == 3){
                progressBar3.setProgress((int) (((double)Math.toIntExact(value)/totalReviews)*100),true);
            }
            if(key == 4){
                progressBar4.setProgress((int) (((double)Math.toIntExact(value)/totalReviews)*100),true);
            }
            if(key == 5){
                progressBar5.setProgress((int) (((double)Math.toIntExact(value)/totalReviews)*100),true);
            }
        });

    }
}