package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WriteStudentResultActivity extends AppCompatActivity {

    Spinner studentIntakeSpinner,studentCourseSpinner,studentIdSpinner,courseIntakeSpinner,courseNameSpinner,subjectIdSpinner;
    EditText resultEditText;
    TextView gradeTextView,passTextView;
    Button addResultBtn,checkResultBtn;
    DatabaseReference databaseReference;
    ValueEventListener studentIntakeListener,studentCourseListener,studentIdListener,courseIntakeListener,courseNameListener,subjectIdListener;
    ArrayList<String> studentIntakeDataList,studentCourseDataList,studentIdDataList,courseIntakeDataList,courseNameDataList,subjectIdDataList;
    ArrayAdapter<String> studentIntakeAdapter,studentCourseAdapter,studentIdAdapter,courseIntakeAdapter,courseNameAdapter,subjectIdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_student_result);

        studentIntakeSpinner = findViewById(R.id.studentIntakeSpinner3);
        studentCourseSpinner = findViewById(R.id.studentCourseSpinner3);
        studentIdSpinner = findViewById(R.id.studentIdSpinner3);
        courseIntakeSpinner = findViewById(R.id.courseIntakeSpinner3);
        courseNameSpinner = findViewById(R.id.courseNameSpinner2);
        subjectIdSpinner = findViewById(R.id.subjectIdSpinner2);
        addResultBtn = findViewById(R.id.addSubjectBtn3);
        gradeTextView = findViewById(R.id.textView47);
        passTextView = findViewById(R.id.textView48);
        resultEditText = findViewById(R.id.editTextNumber3);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        studentIntakeDataList = new ArrayList<>();
        studentIntakeAdapter = new ArrayAdapter<String>(WriteStudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,studentIntakeDataList);
        studentIntakeSpinner.setAdapter(studentIntakeAdapter);

        studentCourseDataList = new ArrayList<>();
        studentCourseAdapter = new ArrayAdapter<String>(WriteStudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,studentCourseDataList);
        studentCourseSpinner.setAdapter(studentCourseAdapter);

        studentIdDataList = new ArrayList<>();
        studentIdAdapter = new ArrayAdapter<String>(WriteStudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,studentIdDataList);
        studentIdSpinner.setAdapter(studentIdAdapter);

        courseIntakeDataList = new ArrayList<>();
        courseIntakeAdapter = new ArrayAdapter<String>(WriteStudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,courseIntakeDataList);
        courseIntakeSpinner.setAdapter(courseIntakeAdapter);

        courseNameDataList = new ArrayList<>();
        courseNameAdapter = new ArrayAdapter<String>(WriteStudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,courseNameDataList);
        courseNameSpinner.setAdapter(courseNameAdapter);

        subjectIdDataList = new ArrayList<>();
        subjectIdAdapter = new ArrayAdapter<String>(WriteStudentResultActivity.this,android.R.layout.simple_spinner_dropdown_item,subjectIdDataList);
        subjectIdSpinner.setAdapter(subjectIdAdapter);

        retrieveStudentData();
        retrieveCourseData();


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

    public void btnCheck(View view) {

        String mark = resultEditText.getText().toString();
        int number = Integer.parseInt(mark);
        String grade = "";
        String pass = "";


        if ( number > 79 ){
            grade = "Grade A";
            gradeTextView.setText(grade);
            pass = "Pass";
            passTextView.setText(pass);
        }
        else if ( number > 69 ){
            grade = "Grade B";
            gradeTextView.setText(grade);
            pass = "Pass";
            passTextView.setText(pass);
        }

        else if ( number > 54 ){
            grade = "Grade C";
            gradeTextView.setText(grade);
            pass = "Pass";
            passTextView.setText(pass);
        }

        else if ( number > 39 ){
            grade = "Grade E";
            gradeTextView.setText(grade);
            pass = "Pass";
            passTextView.setText(pass);
        }

        else if ( number < 40  ){
            grade = "Grade F";
            gradeTextView.setText(grade);
            pass = "Fail";
            passTextView.setText(pass);
        }


    }

    public void btnResult(View view) {

        String mark = resultEditText.getText().toString();
        int number = Integer.parseInt(mark);
        String grade = gradeTextView.getText().toString();
        String pass = passTextView.getText().toString();

        String studentIntake = studentIntakeSpinner.getSelectedItem().toString();
        String studentCourse = studentCourseSpinner.getSelectedItem().toString();
        String studentId = studentIdSpinner.getSelectedItem().toString();
        String courseIntake = courseIntakeSpinner.getSelectedItem().toString();
        String courseName = courseNameSpinner.getSelectedItem().toString();
        String subjectId = subjectIdSpinner.getSelectedItem().toString();

        if (number < 0){
            resultEditText.setError("Enter a valid Mark");
            resultEditText.requestFocus();
            return;
        }
        else if (number > 100){
            resultEditText.setError("Enter a valid Mark");
            resultEditText.requestFocus();
            return;
        }
        else {
            databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(courseIntake).child(subjectId).child("Mark").setValue(mark);
            databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(courseIntake).child(subjectId).child("Grade").setValue(grade);
            databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Result").child(courseIntake).child(subjectId).child("PassFail").setValue(pass);
            Toast.makeText(WriteStudentResultActivity.this, "Result Added Successful", Toast.LENGTH_LONG).show();


        }
    }


}