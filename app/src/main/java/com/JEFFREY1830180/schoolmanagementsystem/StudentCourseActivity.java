package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StudentCourseActivity extends AppCompatActivity {

    ImageView takeNewSubject, subjectTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course);

        takeNewSubject = findViewById(R.id.imageView8);
        subjectTake = findViewById(R.id.imageView7);

        takeNewSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StudentCourseActivity.this,StudentTakeNewSubjectActivity.class);
                startActivity(intentToRegister);
            }
        });

        subjectTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRegister = new Intent(StudentCourseActivity.this,StudentOwnSubjectActivity.class);
                startActivity(intentToRegister);
            }
        });
    }
}