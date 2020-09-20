package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {
    EditText editTextIntake,editTextCourse,editTextSubjectCode,editTextSubjectName;
    String intakeData,courseData,subjectCodeData,subjectNameData = "";
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> intakeAdapter,courseAdapter,subjectAdapter;
    ArrayList<String> intakeDataList,courseDataList,subjectDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_course);
        editTextCourse = findViewById(R.id.editTextTextCourseName);
        editTextIntake = findViewById(R.id.editTextTextIntake);
        editTextSubjectCode = findViewById(R.id.editTextSubjectCode);
        editTextSubjectName = findViewById(R.id.editTextSubjectName);
        databaseReference = FirebaseDatabase.getInstance().getReference("Course");

        intakeDataList = new ArrayList<>();
        intakeAdapter = new ArrayAdapter<String>(AddCourseActivity.this,android.R.layout.simple_spinner_dropdown_item,intakeDataList);
        courseDataList = new ArrayList<>();
        courseAdapter = new ArrayAdapter<String>(AddCourseActivity.this,android.R.layout.simple_spinner_dropdown_item,subjectDataList);
        subjectDataList = new ArrayList<>();
        subjectAdapter = new ArrayAdapter<String>(AddCourseActivity.this,android.R.layout.simple_spinner_dropdown_item,subjectDataList);




    }
    public void btnAddIntake(View view) {
        intakeData = editTextIntake.getText().toString().trim();
        courseData = editTextCourse.getText().toString().trim();
        subjectCodeData = editTextSubjectCode.getText().toString().trim();
        subjectNameData = editTextSubjectName.getText().toString().trim();
        if (intakeData.isEmpty()){
            Toast.makeText(AddCourseActivity.this,"Please Key In Data",Toast.LENGTH_SHORT).show();
        }else if (courseData.isEmpty()){
            Toast.makeText(AddCourseActivity.this,"Please Key In Data",Toast.LENGTH_SHORT).show();
        }
        else if (subjectCodeData.isEmpty()){
            Toast.makeText(AddCourseActivity.this,"Please Key In Data",Toast.LENGTH_SHORT).show();
        }
        else if (subjectNameData.isEmpty()){
            Toast.makeText(AddCourseActivity.this,"Please Key In Data",Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference.child("Intake").child(intakeData).child(courseData).child(subjectCodeData).setValue(subjectNameData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    editTextIntake.setText("");
                    editTextCourse.setText("");
                    editTextSubjectCode.setText("");
                    editTextSubjectName.setText("");
                    intakeDataList.clear();
                    intakeAdapter.notifyDataSetChanged();
                    Toast.makeText(AddCourseActivity.this, "Course Inserted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}