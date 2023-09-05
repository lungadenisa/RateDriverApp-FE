package com.example.ratedriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.adapters.ItemAdaptorByReceiver;
import com.example.ratedriverapp.store.UserStore;

public class UserProfileActivity extends AppCompatActivity {
    private TextView textViewDriverFullName;
    private TextView textViewDriverEmail;
    private TextView textViewDriverCarIdentityNumber;
    private TextView textViewDriverTotalRatings;
    private RatingBar driverRatingBar;
    private Button rateDriverButton;
    private Button goBackButton;
    private RecyclerView reviewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        textViewDriverFullName = findViewById(R.id.textViewDriverFullName);
        textViewDriverEmail = findViewById(R.id.textViewDriverEmail);
        textViewDriverCarIdentityNumber = findViewById(R.id.textViewDriverCarIdentityNumber);
        textViewDriverTotalRatings = findViewById(R.id.textViewDriverTotalRatings);
        driverRatingBar = findViewById(R.id.driverRatingBar);
        rateDriverButton = findViewById(R.id.rateDriverButton);
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        goBackButton = findViewById(R.id.goBackButton);

        textViewDriverFullName.setText(UserStore.receiverUser.username);
        textViewDriverEmail.setText(UserStore.receiverUser.email);
        textViewDriverCarIdentityNumber.setText(UserStore.receiverUser.carIdentityNumber);
        textViewDriverTotalRatings.setText(String.valueOf(UserStore.receiverUser.averageStars));
        driverRatingBar.setRating(UserStore.receiverUser.averageStars);
        findViewById(R.id.rateDriverButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, RateDriverActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.goBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        ItemAdaptorByReceiver adaptor = new ItemAdaptorByReceiver(UserStore.reviewsReceiver);
        reviewsRecyclerView.setAdapter(adaptor);

    }
}
