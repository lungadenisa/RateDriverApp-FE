package com.example.ratedriverapp.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ratedriverapp.R;
import com.example.ratedriverapp.dtos.UserDTO;
import com.example.ratedriverapp.network.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private EditText carNumberText;
    private TextView goToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_page);

        usernameText = findViewById(R.id.usernameEditText);
        emailText = findViewById(R.id.emailEditText);
        passwordText = findViewById(R.id.passwordEditText);
        confirmPasswordText= findViewById(R.id.confirmPasswordEditText);
        carNumberText = findViewById(R.id.carNumberEditText);

        goToLogIn = findViewById(R.id.goToLogIn);

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        goToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);

            }
        });
    }

    private void signUp() {
        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        String carNumberIdentity = carNumberText.getText().toString();

        if (!validateCarNumber(carNumberIdentity)) {
            Toast.makeText(RegisterActivity.this, "Invalid car number", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }


        UserDTO userDTO = new UserDTO(carNumberIdentity,email,password,username);

        Call<ResponseBody> call = RetrofitClient.getInstance().getAPI().register(userDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateCarNumber(String carNumber) {
        String regex = "^(B\\d{2,3}[A-Z]{3}|[A-Z]{2}\\d{2}[A-Z]{3})$";
        return carNumber.matches(regex);
    }

}
