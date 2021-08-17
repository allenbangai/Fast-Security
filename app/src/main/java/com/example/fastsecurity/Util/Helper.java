package com.example.fastsecurity.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.fastsecurity.ControlOptionsActivity;
import com.example.fastsecurity.LogIn;
import com.example.fastsecurity.MainActivity;
import com.example.fastsecurity.SignUp;

public class Helper {
    Intent intent;
    Context context;
    private ProgressDialog progressDialog;

    public Helper(){
    }

    public Helper(Context context){
        this.context = context;
    }

    //progress dialog functions
    public void progressDialogStart(String titleMessage, String detailMessage){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(titleMessage);
        progressDialog.setMessage(detailMessage);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void progressDialogStart(Context context, String titleMessage, String detailMessage){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(titleMessage);
        progressDialog.setMessage(detailMessage);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void progressDialogEnd(){
        progressDialog.dismiss();
    }

    public void toastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void toastMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void logMessage(Class TAGClass, String errorMessage){
        Log.d("com.example.echat." + TAGClass.getName(), errorMessage);
    }

    public void gotoLoginActivity(){
        intent = new Intent(context, LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoControlOption(){
        intent = new Intent(context, ControlOptionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoMainActivity(){
        intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoSignUp(){
        intent = new Intent(context, SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
