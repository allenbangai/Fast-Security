package com.example.fastsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fastsecurity.Auth.AuthViewModel;
import com.example.fastsecurity.Util.Helper;

public class SignUp extends AppCompatActivity {
    private Button signIn, signUp;
    private Helper helper;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        helper = new Helper(getApplicationContext());
        authViewModel = new AuthViewModel();

        signUp = this.findViewById(R.id.register_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.createAccount(v);
            }
        });

        signIn = this.findViewById(R.id.signInR);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.gotoLoginActivity();
            }
        });
    }
}