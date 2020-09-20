package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

public class StudentTuitionFeeActivity extends AppCompatActivity {

    Spinner spinnerIntake;
    TextView textViewFee,textViewPaid;
    ArrayList<String> studentIntakeDataList;
    ArrayAdapter<String> studentIntakeAdapter;
    ValueEventListener studentIntakeListener;
    DatabaseReference databaseReference;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentUser.getUid();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_tuition_fee);

        spinnerIntake = findViewById(R.id.studentIntakeSpinner4);
        studentIntakeDataList = new ArrayList<>();
        studentIntakeAdapter = new ArrayAdapter<String>(StudentTuitionFeeActivity.this,android.R.layout.simple_spinner_dropdown_item,studentIntakeDataList);
        spinnerIntake.setAdapter(studentIntakeAdapter);

        textViewFee = findViewById(R.id.textView50);
        textViewPaid = findViewById(R.id.textView53);

        retrieveStudentData();


    }

    public void btnPayTuitionFee(View view) {
        Intent payment = new Intent(Intent.ACTION_VIEW,Uri.parse("https://sys.djzhlc.edu.my/fi/epayment/auth/login"));
        startActivity(payment);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String studentId = snapshot.child("Users").child("Students").child(userID).child("Id").getValue().toString();
                String studentIntake = snapshot.child("Users").child("Students").child(userID).child("Intake").getValue().toString();
                String studentCourse = snapshot.child("Users").child("Students").child(userID).child("Course").getValue().toString();
                String studentFeeStatus = snapshot.child("Users").child("Students").child(userID).child("Tuition Fee").child("Paid").getKey().toString();

                databaseReference.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Tuition Fee").child("Paid").child(spinnerIntake.getSelectedItem().toString()).setValue(textViewFee.getText().toString());
                databaseReference.child("Users").child("Students").child(userID).child("Tuition Fee").child("Paid").child(spinnerIntake.getSelectedItem().toString()).setValue(textViewFee.getText().toString());

               
                   textViewPaid.setText(studentFeeStatus);


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void retrieveStudentData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String studentId = snapshot.child("Users").child("Students").child(userID).child("Id").getValue().toString();
                String studentIntake = snapshot.child("Users").child("Students").child(userID).child("Intake").getValue().toString();
                String studentCourse = snapshot.child("Users").child("Students").child(userID).child("Course").getValue().toString();
                String studentFeeStatus = snapshot.child("Users").child("Students").child(userID).child("Tuition Fee").child("Paid").getKey().toString();


                for (DataSnapshot item : snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Tuition Fee").child("Unpaid").getChildren()) {

                    studentIntakeDataList.add(item.getKey());

                }
                studentIntakeAdapter.notifyDataSetChanged();

                if (studentFeeStatus.equals("Paid")){
                    textViewPaid.setText(studentFeeStatus);
                }
                else {
                    textViewPaid.setText("Unpaid");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerIntake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String studentId = snapshot.child("Users").child("Students").child(userID).child("Id").getValue().toString();
                        String studentIntake = snapshot.child("Users").child("Students").child(userID).child("Intake").getValue().toString();
                        String studentCourse = snapshot.child("Users").child("Students").child(userID).child("Course").getValue().toString();
                        String studentFee = snapshot.child("Users").child("Students(Backend Process Data)").child(studentIntake).child(studentCourse).child(studentId).child("Tuition Fee").child("Unpaid").child(spinnerIntake.getSelectedItem().toString()).getValue().toString();

                        textViewFee.setText(studentFee);
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