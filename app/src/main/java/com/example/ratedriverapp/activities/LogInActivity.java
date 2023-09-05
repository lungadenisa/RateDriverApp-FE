package com.example.ratedriverapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.dtos.ReviewDTO;
import com.example.ratedriverapp.dtos.UserDTO;
import com.example.ratedriverapp.network.RetrofitClient;
import com.example.ratedriverapp.store.UserStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;

    private TextView textViewToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        email = findViewById(R.id.editTextTextEmailAddress3);
        password = findViewById(R.id.editTextTextPassword);

        textViewToSignUp = findViewById(R.id.textviewToSignUp);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

        textViewToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 3;
    }

    private void logIn(){
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        if (!isValidEmail(email)) {
            Toast.makeText(LogInActivity.this, "Invalid email address", Toast.LENGTH_LONG).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(LogInActivity.this, "Password must be at least 3 characters long", Toast.LENGTH_LONG).show();
            return;
        }

        UserDTO userDTO = new UserDTO(email,password);

        Call<UserDTO> call = RetrofitClient.getInstance().getAPI().login(userDTO);

        call.enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    UserStore.loginUser = response.body();
                    setReviews();
                    Toast.makeText(LogInActivity.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LogInActivity.this, "Login failed. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            public void onFailure(Call<UserDTO> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
                String errorMessage = t.getMessage();
                if (errorMessage != null) {
                    Log.e("LoginActivity", errorMessage);
                }
            }
        });
    }

    private void setReviews(){
        Call<List<ReviewDTO>> call = RetrofitClient.getInstance().getAPI().getReviewsByReceiverId(UserStore.loginUser.id);
        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {

                if (response.isSuccessful()) {
                    UserStore.reviewsLoginUser = response.body();
                } else {
                    Log.e("error", "Error!");
                }
                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {
            }
        });
    }
}