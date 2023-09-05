package com.example.ratedriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.dtos.CreateReviewDTO;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.network.RetrofitClient;
import com.example.ratedriverapp.store.UserStore;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateDriverActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText commentEditText;
    private Button rateDriverButton;
    private TextView carNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_driver_page);

        rateDriverButton = findViewById(R.id.rateDriverButton);
        carNumberEditText = findViewById(R.id.carNumberEditText);
        carNumberEditText.setText(UserStore.receiverUser.carIdentityNumber);
        rateDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingBar = findViewById(R.id.ratingBar);
                EditText commentEditText = findViewById(R.id.commentEditText);
                CreateReviewDTO review = new CreateReviewDTO((int)ratingBar.getRating(),commentEditText.getText().toString(),UserStore.loginUser.id, UserStore.receiverUser.id);
                Log.d("comment", commentEditText.getText().toString());
                createReview(review);
            }
        });
    }

    private void createReview(CreateReviewDTO review){
            Call<ReviewDTO> call = RetrofitClient.getInstance().getAPI().createReview(review);
            call.enqueue(new Callback<ReviewDTO>() {
                @Override
                public void onResponse(Call<ReviewDTO> call, Response<ReviewDTO> response) {
                    if (response.isSuccessful()) {
                        UserStore.reviewsReceiver.add(response.body());
                        DecimalFormat df = new DecimalFormat("#.00");
                        UserStore.receiverUser.averageStars = Float.parseFloat(df.format((float) (UserStore.reviewsReceiver.stream().mapToDouble(ReviewDTO::getStars).sum()/UserStore.reviewsReceiver.size())));
                        Intent intent = new Intent(RateDriverActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            JsonObject errorJson = new JsonParser().parse(response.errorBody().string()).getAsJsonObject();
                            Toast.makeText(RateDriverActivity.this, errorJson.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewDTO> call, Throwable t) {
                    Log.e("SearchFragment", "Failure: " + t.getMessage());
                }
            });
        }
  }
