package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentRegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText editTextName,editTextEmail,editTextId,editTextPassword,editTextConPassword;
    private Spinner spinnerIntake,spinnerCourse;
    private Button btnSignUp;
    private DatabaseReference databaseReference,databaseReferenceSpinner;


    ValueEventListener listener,courseListener;
    ArrayAdapter<String> intakeAdapter,courseAdapter;
    ArrayList<String> intakeDataList,courseDataList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        editTextName = findViewById(R.id.editTextTextPersonName2);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress3);
        editTextId = findViewById(R.id.editTextNumber2);
        editTextPassword = findViewById(R.id.editTextTextPassword4);
        editTextConPassword = findViewById(R.id.editTextTextPassword5);
        spinnerCourse = findViewById(R.id.spinner2);
        spinnerIntake = findViewById(R.id.spinner);
        btnSignUp = findViewById(R.id.button3);

        intakeDataList = new ArrayList<>();
        intakeAdapter = new ArrayAdapter<String>(StudentRegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,intakeDataList);
        spinnerIntake.setAdapter(intakeAdapter);
        courseDataList = new ArrayList<>();
        courseAdapter = new ArrayAdapter<String>(StudentRegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,courseDataList);
        spinnerCourse.setAdapter(courseAdapter);



        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Students");
        databaseReferenceSpinner = FirebaseDatabase.getInstance().getReference("Course");
        firebaseAuth = FirebaseAuth.getInstance();
        retrieveIntakeData();
        //retrieveCourseCodeData();






        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountType = "";
                final String name = editTextName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String id = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();
                String ConPassword = editTextConPassword.getText().toString();
                final String intake = spinnerIntake.getSelectedItem().toString();
                final String course = spinnerCourse.getSelectedItem().toString();


                if (btnSignUp.isClickable())
                {
                    accountType = "Student";
                }

                if (name.isEmpty()) {
                    editTextName.setError("Name Required");
                    editTextName.requestFocus();
                    return;
                }


                if (email.isEmpty()) {
                    editTextEmail.setError(getString(R.string.input_error_email));
                    editTextEmail.requestFocus();
                    return;
                }

                if (id.isEmpty()) {
                    editTextId.setError(getString(R.string.input_error_id));
                    editTextId.requestFocus();
                    return;
                }

                if (id.length() < 6) {
                    editTextId.setError(getString(R.string.input_error_id_invalid));
                    editTextId.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError(getString(R.string.input_error_email_invalid));
                    editTextEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError(getString(R.string.input_error_password));
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError(getString(R.string.input_error_password_length));
                    editTextPassword.requestFocus();
                    return;
                }

                if (ConPassword.isEmpty()) {
                    editTextConPassword.setError(getString(R.string.input_error_password));
                    editTextConPassword.requestFocus();
                    return;
                }

                if (!ConPassword.equals(password)) {
                    editTextConPassword.setError(getString(R.string.input_error_conpassword));
                    editTextConPassword.requestFocus();
                    return;
                }

                final String finalAccountType = accountType;

                firebaseAuth.createUserWithEmailAndPassword(email, ConPassword)
                        .addOnCompleteListener(StudentRegisterActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Student information = new Student(
                                            email,id,name,intake,course,finalAccountType


                                    );

                                    FirebaseDatabase.getInstance().getReference("Users").child("Students(Backend Process Data)").child(intake).child(course).child(id)
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(StudentRegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });

                                    FirebaseDatabase.getInstance().getReference("Users").child("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information);


                                }

                                else {
                                    Toast.makeText(StudentRegisterActivity.this, "Registration Not Success", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }



        });


    }

    public void retrieveIntakeData(){

        listener = databaseReferenceSpinner.child("Intake").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item:dataSnapshot.getChildren()){

                    intakeDataList.add(item.getKey());

                }
                intakeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinnerIntake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseDataList.clear();
                courseListener = databaseReferenceSpinner.child("Intake").child(spinnerIntake.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item:snapshot.getChildren()){

                            courseDataList.add(item.getKey());
                        }

                        courseAdapter.notifyDataSetChanged();
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