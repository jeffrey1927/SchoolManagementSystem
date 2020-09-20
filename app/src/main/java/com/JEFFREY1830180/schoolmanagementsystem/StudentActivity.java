package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class StudentActivity extends AppCompatActivity {

    ImageView attendance,studentResult,tuitionFee,course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        attendance = findViewById(R.id.imageView2);
        studentResult = findViewById(R.id.imageView6);
        tuitionFee = findViewById(R.id.imageView5);
        course = findViewById(R.id.imageView3);

        studentResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StudentActivity.this,StudentResultActivity.class);
                startActivity(intentToRegister);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StudentActivity.this,StudentTakeAttendanceActivity.class);
                startActivity(intentToRegister);
            }
        });

        tuitionFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StudentActivity.this,StudentTuitionFeeActivity.class);
                startActivity(intentToRegister);
            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StudentActivity.this,StudentCourseActivity.class);
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
                        Intent intentToLogout =new Intent(StudentActivity.this,LoginActivity.class);
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