package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private RadioButton radioBtnStudent, radioBtnStaff;
    DatabaseReference databaseReferenceStaff,databaseReferenceStudent;

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        emailId  = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignIn = findViewById(R.id.button);
        radioBtnStudent = findViewById(R.id.radioButtonStudent);
        radioBtnStaff = findViewById(R.id.radioButtonStaff);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioBtnStudent.isChecked()){
                    String studentLoginEmail = emailId.getText().toString();
                    String studentLoginPassword = password.getText().toString();

                    if (studentLoginEmail.isEmpty()) {
                        emailId.setError("Email Required");
                        emailId.requestFocus();
                        return;
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(studentLoginEmail).matches()) {
                        emailId.setError(getString(R.string.input_error_email_invalid));
                        emailId.requestFocus();
                        return;
                    }else if (studentLoginPassword.isEmpty()) {
                        password.setError(getString(R.string.input_error_password));
                        password.requestFocus();
                        return;
                    }else {
                        loginStudent(studentLoginEmail,studentLoginPassword);
                        progressBar.setVisibility(View.VISIBLE);

                    }




                }else if (radioBtnStaff.isChecked()){
                    String staffLoginEmail = emailId.getText().toString();
                    String staffLoginPassword = password.getText().toString();
                    if (staffLoginEmail.isEmpty()) {
                        emailId.setError("Email Required");
                        emailId.requestFocus();
                        return;
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(staffLoginEmail).matches()) {
                        emailId.setError(getString(R.string.input_error_email_invalid));
                        emailId.requestFocus();
                        return;
                    }else if (staffLoginPassword.isEmpty()) {
                        password.setError(getString(R.string.input_error_password));
                        password.requestFocus();
                        return;
                    }else {
                        loginStaff(staffLoginEmail,staffLoginPassword);
                        progressBar.setVisibility(View.VISIBLE);

                    }

                }
            }
        });
    }
    private void loginStudent(final String studentLoginEmail, final String studentLoginPassword){
        firebaseAuth.signInWithEmailAndPassword(studentLoginEmail, studentLoginPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intentStudent = new Intent(LoginActivity.this, StudentActivity.class);
                    startActivity(intentStudent);
                    intentStudent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginStaff(final String staffLoginEmail, final String staffLoginPassword){
        firebaseAuth.signInWithEmailAndPassword(staffLoginEmail, staffLoginPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    final String RegisteredUserID = currentUser.getUid();
                    databaseReferenceStaff = FirebaseDatabase.getInstance().getReference().child("Staff").child(RegisteredUserID);
                    databaseReferenceStaff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String userType = dataSnapshot.child("TypeOfAccount").getValue().toString();
                            if (userType.equals("Staff")) {
                                Intent intentStudent = new Intent(LoginActivity.this, StaffActivity.class);
                                startActivity(intentStudent);
                                intentStudent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();

                            } else if (userType.equals("Admin")) {
                                Intent intentStaff = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intentStaff);
                                intentStaff.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();

                            }else {
                                Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            progressBar.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
    }
}