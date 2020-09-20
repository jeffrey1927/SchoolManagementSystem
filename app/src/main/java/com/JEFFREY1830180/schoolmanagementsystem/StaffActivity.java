package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class StaffActivity extends AppCompatActivity {

    ImageView attendance,studentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        attendance = findViewById(R.id.imageView11);
        studentResult = findViewById(R.id.imageView12);

        studentResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StaffActivity.this,WriteStudentResultActivity.class);
                startActivity(intentToRegister);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StaffActivity.this,AttendanceActivity.class);
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

                    case R.id.logout:
                        Intent intentToLogout =new Intent(StaffActivity.this,LoginActivity.class);
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