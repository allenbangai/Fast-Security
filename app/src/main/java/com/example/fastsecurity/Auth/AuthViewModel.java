package com.example.fastsecurity.Auth;


import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.fastsecurity.R;
import com.example.fastsecurity.Util.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AuthViewModel {
    private Helper helper;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //reference to realtime database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference().child("Users");

    private EditText email, password, confirmPassword, userName, phoneNumber;
    private String emailStr, passwordStr, confirmPasswordStr, userNameStr, phoneNumberStr;
    private static final String TAG = "com.example.ublog.Auth.AuthViewModel.allen";

    public AuthViewModel(){

    }

    public void createAccount(@NonNull final View view){
        helper = new Helper(view.getContext());

        email = view.getRootView().findViewById(R.id.emailR);
        password = view.getRootView().findViewById(R.id.passwordR);
        confirmPassword = view.getRootView().findViewById(R.id.passwordConfirmR);
        userName = view.getRootView().findViewById(R.id.userName);
        phoneNumber = view.getRootView().findViewById(R.id.number);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        confirmPasswordStr = confirmPassword.getText().toString();
        userNameStr = userName.getText().toString();
        phoneNumberStr = phoneNumber.getText().toString();

        if(emailStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter Email Address");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            helper.toastMessage(view.getContext(), "Valid Email Required");
        }else if(userNameStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter username");
        }else if(passwordStr.trim().isEmpty() || confirmPasswordStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Fill Password field");
        }else if(passwordStr.length() <6 || confirmPasswordStr.length() < 6){
            helper.toastMessage(view.getContext(), "Fill Password field");
        }else if(!confirmPasswordStr.equals(passwordStr)){
            helper.toastMessage(view.getContext(), "Passwords do not Match");
        }else if(phoneNumberStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter a Phone Number");
        }else if(!Patterns.PHONE.matcher(phoneNumberStr).matches()){
            helper.toastMessage(view.getContext(), "Enter a valid phone number");
        }else{
            helper.progressDialogStart("Please Wait", "Registering User Account");
            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail: success");
                        helper.toastMessage(view.getContext(), "You are logged in successfully");

                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        assert firebaseUser != null;
                        String userID = firebaseUser.getUid();
                        String fullName = firebaseUser.getDisplayName();
                        DatabaseReference reference = databaseReference.child(userID);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userID);
                        hashMap.put("fullname", fullName);
                        hashMap.put("username", userNameStr);
                        hashMap.put("email", emailStr);
                        hashMap.put("number", phoneNumberStr);
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    helper.progressDialogEnd();
                                    helper.gotoControlOption();
                                    Log.d(TAG, "Save Account Info: success");
                                }
                            }
                        });
                    }else{
                        helper.progressDialogEnd();
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        helper.toastMessage(view.getContext(), "Failed to Register \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void logIn(@NonNull final View view){
        helper = new Helper(view.getContext());

        email = view.getRootView().findViewById(R.id.emailL);
        password = view.getRootView().findViewById(R.id.passwordL);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        if(emailStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter Email Address");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            helper.toastMessage(view.getContext(), "Valid Email Required");
        }else if(passwordStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Fill Password field");
        }else if(passwordStr.length() <6){
            helper.toastMessage(view.getContext(), "Fill Password field");
        }else{
            helper.progressDialogStart("Please Wait", "Signing Into Your Account");
            mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        helper.progressDialogEnd();
                        Log.d(TAG, "signInWithEmail: Success");
                        helper.gotoControlOption();
                        helper.toastMessage(view.getContext(), "You are logged in successfully");
                    }
                    else{
                        helper.progressDialogEnd();
                        Log.d(TAG, "signInWithEmail: Failure", task.getException());
                        helper.toastMessage(view.getContext(), "Failed to login \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }
}
