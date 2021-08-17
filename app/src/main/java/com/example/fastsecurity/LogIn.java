package com.example.fastsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fastsecurity.Auth.AuthViewModel;
import com.example.fastsecurity.Util.Helper;

public class LogIn extends AppCompatActivity {
    private Button signIn, signUp;
    private Helper helper;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        helper = new Helper(getApplicationContext());
        authViewModel = new AuthViewModel();

        signIn = this.findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.logIn(v);
            }
        });

        signUp = this.findViewById(R.id.register_button_1);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.gotoSignUp();
            }
        });
    }
}