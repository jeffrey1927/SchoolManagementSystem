package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class StudentTakeNewSubjectActivity extends AppCompatActivity {

    Spinner courseIntakeSpinner,courseNameSpinner,subjectIdSpinner;
    Button addSubjectBtn;
    DatabaseReference databaseReference;
    ValueEventListener courseIntakeListener,courseNameListener,subjectIdListener;
    ArrayList<String> courseIntakeDataList,courseNameDataList,subjectIdDataList;
    ArrayAdapter<String> courseIntakeAdapter,courseNameAdapter,subjectIdAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_take_new_subject);

        courseIntakeSpinner = findViewById(R.id.courseIntakeSpinner5);
        courseNameSpinner = findViewById(R.id.courseNameSpinner3);
        subjectIdSpinner = findViewById(R.id.subjectIdSpinner4);
        addSubjectBtn = findViewById(R.id.addSubjectBtn6);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        courseIntakeDataList = new ArrayList<>();
        courseIntakeAdapter = new ArrayAdapter<String>(StudentTakeNewSubjectActivity.this,android.R.layout.simple_spinner_dropdown_item,courseIntakeDataList);
        courseIntakeSpinner.setAdapter(courseIntakeAdapter);

        courseNameDataList = new ArrayList<>();
        courseNameAdapter = new ArrayAdapter<String>(StudentTakeNewSubjectActivity.this,android.R.layout.simple_spinner_dropdown_item,courseNameDataList);
        courseNameSpinner.setAdapter(courseNameAdapter);

        subjectIdDataList = new ArrayList<>();
        subjectIdAdapter = new ArrayAdapter<String>(StudentTakeNewSubjectActivity.this,android.R.layout.simple_spinner_dropdown_item,subjectIdDataList);
        subjectIdSpinner.setAdapter(subjectIdAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = currentUser.getUid();

        retrieveCourseData();

        addSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String courseIntake = courseIntakeSpinner.getSelectedItem().toString();
                final String courseName = courseNameSpinner.getSelectedItem().toString();
                final String subjectId = subjectIdSpinner.getSelectedItem().toString();


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                final String userID = currentUser.getUid();

                databaseReference.child("Course").child("Intake").child(courseIntake).child(courseName).child(subjectId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String subjectName = snapshot.getValue().toString();

                        FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userID).child("Subject Take").child(courseIntake).child(subjectId).setValue(subjectName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(StudentTakeNewSubjectActivity.this, "Subject Added Successful", Toast.LENGTH_LONG).show();


                                } else {
                                    Toast.makeText(StudentTakeNewSubjectActivity.this, "Subject Added Not Success", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }

    public void retrieveCourseData(){
        courseIntakeListener = databaseReference.child("Course").child("Intake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()){

                    courseIntakeDataList.add(item.getKey());

                }
                courseIntakeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        courseIntakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseNameDataList.clear();
                courseNameListener = databaseReference.child("Course").child("Intake").child(courseIntakeSpinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item:snapshot.getChildren()){

                            courseNameDataList.add(item.getKey());

                        }
                        courseNameAdapter.notifyDataSetChanged();
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

        courseNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjectIdDataList.clear();
                subjectIdListener = databaseReference.child("Course").child("Intake").child(courseIntakeSpinner.getSelectedItem().toString()).child(courseNameSpinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
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
}