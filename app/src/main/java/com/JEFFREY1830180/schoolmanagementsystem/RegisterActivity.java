package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextId, editTextPassword, editTextConformPassword , editTextPersonName;
    private RadioGroup radioGroup;
    private RadioButton radioBtnAdmin, radioBtnStaff;
    private ProgressBar progressBar;
    private Button registerBtn;
    private DatabaseReference databaseReference;


    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextTextEmailAddress2);
        editTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextId = findViewById(R.id.editTextNumber);
        editTextPassword = findViewById(R.id.editTextTextPassword2);
        editTextConformPassword = findViewById(R.id.editTextTextPassword3);
        radioGroup = findViewById(R.id.radioGroup);
        radioBtnAdmin = findViewById(R.id.radioButton3);
        radioBtnStaff = findViewById(R.id.radioButton2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        registerBtn = findViewById(R.id.button2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Staff");

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String accountType = "";
                final String name = editTextPersonName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String id = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();
                String ConPassword = editTextConformPassword.getText().toString();

                if (radioBtnAdmin.isChecked()){

                    accountType = "Admin";
                }


                if (radioBtnStaff.isChecked()){

                    accountType = "Staff";
                }

                if (name.isEmpty()) {
                    editTextPersonName.setError("Name Required");
                    editTextPersonName.requestFocus();
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
                    editTextConformPassword.setError(getString(R.string.input_error_password));
                    editTextConformPassword.requestFocus();
                    return;
                }

                if (!ConPassword.equals(password)) {
                    editTextConformPassword.setError(getString(R.string.input_error_conpassword));
                    editTextConformPassword.requestFocus();
                    return;
                }



                final String finalAccountType = accountType;

                mAuth.createUserWithEmailAndPassword(email, ConPassword)
                        .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Staff information = new Staff(
                                            email,id,name,finalAccountType


                                    );
                                    FirebaseDatabase.getInstance().getReference("Users").child(finalAccountType).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                                else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Registration Not Success", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });


    }


}



