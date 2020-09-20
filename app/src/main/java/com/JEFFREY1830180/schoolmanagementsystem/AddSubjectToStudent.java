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
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.xml.validation.Validator;


public class AddSubjectToStudent extends AppCompatActivity {


    Spinner studentIntakeSpinner,studentCourseSpinner,studentIdSpinner,courseIntakeSpinner,courseNameSpinner,subjectIdSpinner;
    Button addSubjectBtn;
    DatabaseReference databaseReference;
    ValueEventListener studentIntakeListener,studentCourseListener,studentIdListener,courseIntakeListener,courseNameListener,subjectIdListener;
    ArrayList<String> studentIntakeDataList,studentCourseDataList,studentIdDataList,courseIntakeDataList,courseNameDataList,subjectIdDataList;
    ArrayAdapter<String> studentIntakeAdapter,studentCourseAdapter,studentIdAdapter,courseIntakeAdapter,courseNameAdapter,subjectIdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_to_student);

        studentIntakeSpinner = findViewById(R.id.studentIntakeSpinner);
        studentCourseSpinner = findViewById(R.id.studentCourseSpinner);
        studentIdSpinner = findViewById(R.id.studentIdSpinner);
        courseIntakeSpinner = findViewById(R.id.courseIntakeSpinner);
        courseNameSpinner = findViewById(R.id.courseNameSpinner);
        subjectIdSpinner = findViewById(R.id.subjectIdSpinner);
        addSubjectBtn = findViewById(R.id.addSubjectBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        studentIntakeDataList = new ArrayList<>();
        studentIntakeAdapter = new ArrayAdapter<String>(AddSubjectToStudent.this,android.R.layout.simple_spinner_dropdown_item,studentIntakeDataList);
        studentIntakeSpinner.setAdapter(studentIntakeAdapter);

        studentCourseDataList = new ArrayList<>();
        studentCourseAdapter = new ArrayAdapter<String>(AddSubjectToStudent.this,android.R.layout.simple_spinner_dropdown_item,studentCourseDataList);
        studentCourseSpinner.setAdapter(studentCourseAdapter);

        studentIdDataList = new ArrayList<>();
        studentIdAdapter = new ArrayAdapter<String>(AddSubjectToStudent.this,android.R.layout.simple_spinner_dropdown_item,studentIdDataList);
        studentIdSpinner.setAdapter(studentIdAdapter);

        courseIntakeDataList = new ArrayList<>();
        courseIntakeAdapter = new ArrayAdapter<String>(AddSubjectToStudent.this,android.R.layout.simple_spinner_dropdown_item,courseIntakeDataList);
        courseIntakeSpinner.setAdapter(courseIntakeAdapter);

        courseNameDataList = new ArrayList<>();
        courseNameAdapter = new ArrayAdapter<String>(AddSubjectToStudent.this,android.R.layout.simple_spinner_dropdown_item,courseNameDataList);
        courseNameSpinner.setAdapter(courseNameAdapter);

        subjectIdDataList = new ArrayList<>();
        subjectIdAdapter = new ArrayAdapter<String>(AddSubjectToStudent.this,android.R.layout.simple_spinner_dropdown_item,subjectIdDataList);
        subjectIdSpinner.setAdapter(subjectIdAdapter);

        retrieveStudentData();
        retrieveCourseData();

        addSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String studentIntake = studentIntakeSpinner.getSelectedItem().toString();
                String studentCourse = studentCourseSpinner.getSelectedItem().toString();
                String studentId = studentIdSpinner.getSelectedItem().toString();
                String courseIntake = courseIntakeSpinner.getSelectedItem().toString();
                String courseName = courseNameSpinner.getSelectedItem().toString();
                String subjectId = subjectIdSpinner.getSelectedItem().toString();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = currentUser.getUid();

                //FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(studentIntake).child(studentCourse).child(studentId).child("Subject Added").child(courseIntake).child(courseName).setValue(subjectId)


                FirebaseDatabase.getInstance().getReference().child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Subject Added").child(courseIntake).child(courseName).setValue(subjectId).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddSubjectToStudent.this, "Subject Added Successful", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(AddSubjectToStudent.this, "Subject Added Not Success", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void retrieveStudentData(){
        studentIntakeListener = databaseReference.child("Users").child("Students(Backend Process Data)").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()){

                    studentIntakeDataList.add(item.getKey());

                }


                studentIntakeAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        studentIntakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentCourseDataList.clear();
                studentCourseListener = databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntakeSpinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        studentCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentIdDataList.clear();
                studentIdListener = databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntakeSpinner.getSelectedItem().toString()).child(studentCourseSpinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item:snapshot.getChildren()){

                            studentIdDataList.add(item.getKey());
                        }

                        studentIdAdapter.notifyDataSetChanged();
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