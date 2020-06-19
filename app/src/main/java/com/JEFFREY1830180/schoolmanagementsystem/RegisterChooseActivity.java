package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterChooseActivity extends AppCompatActivity {
    ImageView student,staff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        student = findViewById(R.id.imageViewStudent);
        staff = findViewById(R.id.imageViewStaff);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToStudentRegister = new Intent(RegisterChooseActivity.this,StudentRegisterActivity.class);
                startActivity(intentToStudentRegister);
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToStaffRegister = new Intent(RegisterChooseActivity.this,RegisterActivity.class);
                startActivity(intentToStaffRegister);
            }
        });
    }
}