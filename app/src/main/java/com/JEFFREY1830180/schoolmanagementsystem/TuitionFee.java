package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TuitionFee extends AppCompatActivity {

    Spinner studentIntakeSpinner,studentCourseSpinner,studentIdSpinner,courseIntakeSpinner;
    EditText tuitionFeeEditText;
    Button addFeeBtn;
    DatabaseReference databaseReference;
    ValueEventListener studentIntakeListener,studentCourseListener,studentIdListener,courseIntakeListener;
    ArrayList<String> studentIntakeDataList,studentCourseDataList,studentIdDataList,courseIntakeDataList;
    ArrayAdapter<String> studentIntakeAdapter,studentCourseAdapter,studentIdAdapter,courseIntakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuitoin_fee);

        studentIntakeSpinner = findViewById(R.id.studentIntakeSpinner2);
        studentCourseSpinner = findViewById(R.id.studentCourseSpinner2);
        studentIdSpinner = findViewById(R.id.studentIdSpinner2);
        courseIntakeSpinner = findViewById(R.id.courseIntakeSpinner2);
        addFeeBtn = findViewById(R.id.addSubjectBtn2);
        tuitionFeeEditText = findViewById(R.id.editTextNumberDecimal);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        studentIntakeDataList = new ArrayList<>();
        studentIntakeAdapter = new ArrayAdapter<String>(TuitionFee.this,android.R.layout.simple_spinner_dropdown_item,studentIntakeDataList);
        studentIntakeSpinner.setAdapter(studentIntakeAdapter);

        studentCourseDataList = new ArrayList<>();
        studentCourseAdapter = new ArrayAdapter<String>(TuitionFee.this,android.R.layout.simple_spinner_dropdown_item,studentCourseDataList);
        studentCourseSpinner.setAdapter(studentCourseAdapter);

        studentIdDataList = new ArrayList<>();
        studentIdAdapter = new ArrayAdapter<String>(TuitionFee.this,android.R.layout.simple_spinner_dropdown_item,studentIdDataList);
        studentIdSpinner.setAdapter(studentIdAdapter);

        courseIntakeDataList = new ArrayList<>();
        courseIntakeAdapter = new ArrayAdapter<String>(TuitionFee.this,android.R.layout.simple_spinner_dropdown_item,courseIntakeDataList);
        courseIntakeSpinner.setAdapter(courseIntakeAdapter);


        retrieveStudentData();
        retrieveCourseData();

        addFeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fee = tuitionFeeEditText.getText().toString().trim();
                String studentIntake = studentIntakeSpinner.getSelectedItem().toString();
                String studentCourse = studentCourseSpinner.getSelectedItem().toString();
                String studentId = studentIdSpinner.getSelectedItem().toString();
                String courseIntake = courseIntakeSpinner.getSelectedItem().toString();

                databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Tuition Fee").child("Unpaid").child(courseIntake).setValue(fee).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TuitionFee.this, "Tuition Fee Added Successful", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(TuitionFee.this, "Tuition Fee Added Not Success", Toast.LENGTH_LONG).show();
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

    }
}