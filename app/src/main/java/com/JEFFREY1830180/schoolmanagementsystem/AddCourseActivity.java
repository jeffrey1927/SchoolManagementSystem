package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.os.Bundle;
import android.view.View;
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

public class AddCourseActivity extends AppCompatActivity {
    Spinner spinnerIntake,spinnerCourse;
    EditText editTextIntake,editTextCourse,editTextSubject;
    String textData = "";
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> intakeDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_course);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerIntake = findViewById(R.id.spinnerIntake);
        editTextCourse = findViewById(R.id.editTextTextCourseName);
        editTextIntake = findViewById(R.id.editTextTextIntake);
        editTextSubject = findViewById(R.id.editTextSubject);
        databaseReference = FirebaseDatabase.getInstance().getReference("Intake");

        intakeDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(AddCourseActivity.this,android.R.layout.simple_spinner_dropdown_item,intakeDataList);
        spinnerIntake.setAdapter(adapter);
        retrieveData();


    }
    public void btnAddIntake(View view) {
        textData = editTextIntake.getText().toString().trim();
        if (textData.isEmpty()){
            Toast.makeText(AddCourseActivity.this,"Please Key In Data",Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    editTextIntake.setText("");
                    intakeDataList.clear();
                    retrieveData();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AddCourseActivity.this, "Intake Inserted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void retrieveData(){

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item:dataSnapshot.getChildren()){

                    intakeDataList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}