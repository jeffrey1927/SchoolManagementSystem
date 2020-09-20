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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentResultActivity extends AppCompatActivity {

    Spinner spinnerIntake,spinnerSubject;
    TextView textViewResult;
    ArrayList<String> studentIntakeDataList,studentSubjectDataList;
    ArrayAdapter<String> studentIntakeAdapter,studentSubjectAdapter;
    ValueEventListener studentIntakeListener,studentSubjectListener;
    Button checkResultBtn;

    DatabaseReference databaseReference;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentUser.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);

        spinnerIntake = findViewById(R.id.courseIntakeSpinner4);
        spinnerSubject = findViewById(R.id.subjectIdSpinner3);

        studentIntakeDataList = new ArrayList<>();
        studentIntakeAdapter = new ArrayAdapter<String>(StudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,studentIntakeDataList);
        spinnerIntake.setAdapter(studentIntakeAdapter);

        studentSubjectDataList = new ArrayList<>();
        studentSubjectAdapter = new ArrayAdapter<String>(StudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,studentSubjectDataList);
        spinnerSubject.setAdapter(studentSubjectAdapter);

        textViewResult = findViewById(R.id.textView56);

        checkResultBtn = findViewById(R.id.addSubjectBtn5);

        retrieveStudentData();


    }


    public void retrieveStudentData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        studentIntakeListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String studentId = snapshot.child("Users").child("Students").child(userID).child("Id").getValue().toString();
                String studentIntake = snapshot.child("Users").child("Students").child(userID).child("Intake").getValue().toString();
                String studentCourse = snapshot.child("Users").child("Students").child(userID).child("Course").getValue().toString();

                for (DataSnapshot item : snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").getChildren()) {

                    studentIntakeDataList.add(item.getKey());

                }
                studentIntakeAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerIntake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentSubjectDataList.clear();
                studentSubjectListener = databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String studentId = snapshot.child("Users").child("Students").child(userID).child("Id").getValue().toString();
                        String studentIntake = snapshot.child("Users").child("Students").child(userID).child("Intake").getValue().toString();
                        String studentCourse = snapshot.child("Users").child("Students").child(userID).child("Course").getValue().toString();
                        String subject = snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(spinnerIntake.getSelectedItem().toString()).getKey();

                        for (DataSnapshot item : snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(subject).getChildren()) {

                            studentSubjectDataList.add(item.getKey());

                        }
                        studentSubjectAdapter.notifyDataSetChanged();




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


        checkResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String studentId = snapshot.child("Users").child("Students").child(userID).child("Id").getValue().toString();
                        String studentIntake = snapshot.child("Users").child("Students").child(userID).child("Intake").getValue().toString();
                        String studentCourse = snapshot.child("Users").child("Students").child(userID).child("Course").getValue().toString();
                        String subject = snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(spinnerIntake.getSelectedItem().toString()).getKey();
                        String result = snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(subject).child(spinnerSubject.getSelectedItem().toString()).child("PassFail").getValue().toString();

                        textViewResult.setText(result);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}