package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class AdminActivity extends AppCompatActivity {
    ImageView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        signUp = findViewById(R.id.imageViewSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(AdminActivity.this,RegisterActivity.class);
                startActivity(intentToRegister);
            }
        });
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_nav, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intentToProfile =new Intent(AdminActivity.this,ProfileActivity.class);
                        startActivity(intentToProfile);
                        return true;
                    case R.id.logout:
                        Intent intentToLogout =new Intent(AdminActivity.this,LoginActivity.class);
                        startActivity(intentToLogout);
                        intentToLogout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }


}