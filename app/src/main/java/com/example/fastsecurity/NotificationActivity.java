package com.example.fastsecurity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.fastsecurity.Adapter.NotificationAdapter;
import com.example.fastsecurity.Model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    List<Notification> notificationList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = this.findViewById(R.id.recycler_nots);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        notificationList = new ArrayList<>();

        loadNotifications();
    }

    public void loadNotifications(){
        intent = getIntent();
        notificationList.clear();
        Notification notification = new Notification(intent.getStringExtra("name"), intent.getStringExtra("value"));
        notificationList.add(notification);

        notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationList);
        recyclerView.setAdapter(notificationAdapter);
    }
}