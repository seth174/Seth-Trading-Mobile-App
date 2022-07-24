package com.sethfagen.sethstradingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sethfagen.sethstradingapplication.databinding.ActivityLoginBinding;
import com.sethfagen.sethstradingapplication.remote_database.models.User;
import com.sethfagen.sethstradingapplication.remote_database.RetrofitClient;
import com.sethfagen.sethstradingapplication.remote_database.WebInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    public static final String BUNDLE_USER_ID = "com.sethfagen.sethstradingapplication.BUNDLE_USER_ID";
    public static final String BUNDLE_NAME = "com.sethfagen.sethstradingapplication.BUNDLE_NAME";
    public static final String BUNDLE_EMAIL = "com.sethfagen.sethstradingapplication.BUNDLE_EMAIL";
    public static final String BUNDLE_PASSWORD_DIGEST = "com.sethfagen.sethstradingapplication.BUNDLE_PASSWORD_DIGEST";
    public static final String BUNDLE_ADMIN = "com.sethfagen.sethstradingapplication.BUNDLE_ADMIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonLogin.setOnClickListener((View v) -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            WebInterface webInterface = RetrofitClient.getInstance().getWebInterface();
            Call<User> call = webInterface.getUser(email, password);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        User user = response.body();
                        login(user);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "BAD EMAIL OR PASSWORD", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("CSC", t.fillInStackTrace().toString());
                }
            });

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    public void login(User user){

        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
        intent.putExtra(BUNDLE_USER_ID, user.getId());
        intent.putExtra(BUNDLE_NAME, user.getName());
        intent.putExtra(BUNDLE_EMAIL, user.getEmail());
        intent.putExtra(BUNDLE_PASSWORD_DIGEST, user.getPassword_digest());
        intent.putExtra(BUNDLE_ADMIN, user.isAdmin());
        startActivity(intent);
    }

}