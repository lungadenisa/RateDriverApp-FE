package com.example.ratedriverapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.activities.UserProfileActivity;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.dtos.UserDTO;
import com.example.ratedriverapp.network.RetrofitClient;
import com.example.ratedriverapp.store.UserStore;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        EditText carIdentityNumber = v.findViewById(R.id.searchEditText);


        Button searchButton = v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByCarIdentityNumber(carIdentityNumber);
            }
        });

        return v;
    }

    private void searchByCarIdentityNumber(EditText carIdentityNumber) {
        if(carIdentityNumber.getText().toString().equals(UserStore.loginUser.carIdentityNumber)){
            Toast.makeText(getActivity(), "You can't search your own car number!", Toast.LENGTH_LONG).show();
            return;
        }
        Call<UserDTO> call = RetrofitClient.getInstance().getAPI().searchUserByCarIdentityNumber(carIdentityNumber.getText().toString());
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    UserStore.receiverUser = response.body();
                    setSearchedUserReviews();
                } else {
                    try {
                        JsonObject errorJson = new JsonParser().parse(response.errorBody().string()).getAsJsonObject();
                        Toast.makeText(getActivity(), errorJson.get("message").getAsString(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("SearchFragment", "Failure: " + t.getMessage());
            }
        });
    }
    private void setSearchedUserReviews(){
        Call<List<ReviewDTO>> call = RetrofitClient.getInstance().getAPI().getReviewsByReceiverId(UserStore.receiverUser.id);
        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if (response.isSuccessful()) {
                    UserStore.reviewsReceiver = response.body();
                    Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        JsonObject errorJson = new JsonParser().parse(response.errorBody().string()).getAsJsonObject();
                        Toast.makeText(getActivity(), errorJson.get("message").getAsString(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
            }
        });
    }
    }