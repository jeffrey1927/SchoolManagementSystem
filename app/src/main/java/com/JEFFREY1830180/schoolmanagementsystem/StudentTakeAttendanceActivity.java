package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentTakeAttendanceActivity extends AppCompatActivity {

    Spinner subjectIdSpinner,studentCourseSpinner;
    Button presentBtn;
    DatabaseReference databaseReference;
    ValueEventListener studentCourseListener,subjectIdListener;
    ArrayList<String> studentCourseDataList,subjectIdDataList;
    ArrayAdapter<String> studentCourseAdapter,subjectIdAdapter;
    TextView date,time;

    String currentDate = java.text.DateFormat.getDateInstance().format(new Date());
    String currentTime = java.text.DateFormat.getTimeInstance().format(new Date());

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentUser.getUid();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_take_attendance);

        studentCourseSpinner = findViewById(R.id.courseIntakeSpinner6);
        subjectIdSpinner = findViewById(R.id.subjectIdSpinner5);
        presentBtn = findViewById(R.id.addSubjectBtn7);
        date = findViewById(R.id.textView69);
        time = findViewById(R.id.textView71);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        studentCourseDataList = new ArrayList<>();
        studentCourseAdapter = new ArrayAdapter<String>(StudentTakeAttendanceActivity.this,android.R.layout.simple_spinner_dropdown_item,studentCourseDataList);
        studentCourseSpinner.setAdapter(studentCourseAdapter);

        subjectIdDataList = new ArrayList<>();
        subjectIdAdapter = new ArrayAdapter<String>(StudentTakeAttendanceActivity.this,android.R.layout.simple_spinner_dropdown_item,subjectIdDataList);
        subjectIdSpinner.setAdapter(subjectIdAdapter);

        time.setText(currentTime);
        date.setText(currentDate);

        retrieveStudentData();
    }

    public void retrieveStudentData(){

        studentCourseListener = databaseReference.child("Users").child("Students").child(userID).child("Subject Take").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()){

                    studentCourseDataList.add(item.getKey());

                }


                studentCourseAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        studentCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjectIdDataList.clear();
                subjectIdListener = databaseReference.child("Users").child("Students").child(userID).child("Subject Take").child(studentCourseSpinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for (DataSnapshot item:snapshot.getChildren()){

                            subjectIdDataList.add(item.getKey());


                        }


                        subjectIdAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void btnPresent(View view) {
        final String subjectId = subjectIdSpinner.getSelectedItem().toString();
        final String studentCourse = studentCourseSpinner.getSelectedItem().toString();

        databaseReference.child("Users").child("Students").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String studentId = snapshot.child("Id").getValue().toString();
                String studentName = snapshot.child("Name").getValue().toString();
                String currentCourse = snapshot.child("Course").getValue().toString();


                databaseReference.child("Attendance").child(currentDate).child(subjectId).child(studentName).child("Student Id").setValue(studentId);
                databaseReference.child("Attendance").child(currentDate).child(subjectId).child(studentName).child("Time").setValue(currentTime).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(StudentTakeAttendanceActivity.this, "Attendance Record Successful", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}