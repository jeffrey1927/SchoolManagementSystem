package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CourseActivity extends AppCompatActivity {
    ImageView addCourse,availableCourse,addSubjectToStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        addCourse = findViewById(R.id.imageViewAddCoure);
        addSubjectToStudent = findViewById(R.id.imageViewAddSubjectStudent);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToAddCourse = new Intent(CourseActivity.this,AddCourseActivity.class);
                startActivity(intentToAddCourse);
            }
        });


        addSubjectToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToAddCourse = new Intent(CourseActivity.this,AddSubjectToStudent.class);
                startActivity(intentToAddCourse);
            }
        });
    }
}