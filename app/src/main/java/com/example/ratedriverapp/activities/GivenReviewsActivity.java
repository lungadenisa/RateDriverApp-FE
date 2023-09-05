package com.example.ratedriverapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.adapters.ItemAdaptorByGiver;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.network.RetrofitClient;
import com.example.ratedriverapp.store.UserStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class GivenReviewsActivity extends AppCompatActivity {
    private Button backButton;
    private RecyclerView givenReviewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.given_reviews_page);

        givenReviewsRecyclerView = findViewById(R.id.givenReviewsRecyclerView);

        if (UserStore.loginUser != null) {
            setGivenReviewsRecyclerView();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        givenReviewsRecyclerView.setLayoutManager(layoutManager);
        ItemAdaptorByGiver adaptor = new ItemAdaptorByGiver(UserStore.reviewsGiver);
        givenReviewsRecyclerView.setAdapter(adaptor);


        backButton = findViewById(R.id.backButton);
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setGivenReviewsRecyclerView(){
            Call<List<ReviewDTO>> call = RetrofitClient.getInstance().getAPI().getReviewsByGiverId(UserStore.loginUser.id);
            call.enqueue(new Callback<List<ReviewDTO>>() {
                @Override
                public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                    if (response.isSuccessful()) {
                        UserStore.reviewsGiver = response.body();
                        if(givenReviewsRecyclerView != null){
                            ItemAdaptorByGiver adaptor = new ItemAdaptorByGiver(UserStore.reviewsGiver);
                            givenReviewsRecyclerView.setAdapter(adaptor);
                        }
                    } else {
                        Log.e("error", "Error!");
                    }
                }
                @Override
                public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
                }
            });
        }
}
