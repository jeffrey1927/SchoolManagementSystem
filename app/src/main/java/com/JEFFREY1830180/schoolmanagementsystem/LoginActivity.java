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
    TextView textViewShowName,textViewShowEmail,textViewShowAccount;
    EditText emailId, password;
    Button btnSignIn;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    DatabaseReference databaseReferenceStaff,databaseReferenceStudent,databaseReference;

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        textViewShowName = findViewById(R.id.textViewName);
        textViewShowEmail = findViewById(R.id.textViewShowEmail);
        textViewShowAccount = findViewById(R.id.textViewShowAccount);
        emailId  = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignIn = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String loginEmail = emailId.getText().toString();
                    String loginPassword = password.getText().toString();

                    if (loginEmail.isEmpty()) {
                        emailId.setError("Email Required");
                        emailId.requestFocus();
                        return;
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
                        emailId.setError(getString(R.string.input_error_email_invalid));
                        emailId.requestFocus();
                        return;
                    }else if (loginPassword.isEmpty()) {
                        password.setError(getString(R.string.input_error_password));
                        password.requestFocus();
                        return;
                    }else {
                        login(loginEmail,loginPassword);
                        progressBar.setVisibility(View.VISIBLE);

                    }

            }
        });
    }

    private void login(final String loginEmail, final String loginPassword){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String[] split = loginEmail.split ("@");
                    String domain = split[1]; //This Will Give You The Domain After '@'

                    if (domain.equals("student.com")){
                        Intent intentStaff= new Intent(LoginActivity.this, StudentActivity.class);
                        startActivity(intentStaff);
                        intentStaff.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                    }
                    else if (domain.equals("staff.com")){
                        Intent intentStaff= new Intent(LoginActivity.this, StaffActivity.class);
                        startActivity(intentStaff);
                        intentStaff.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                    }
                    else if (domain.equals("admin.com")){
                        Intent intentAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intentAdmin);
                        intentAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });
    }
}