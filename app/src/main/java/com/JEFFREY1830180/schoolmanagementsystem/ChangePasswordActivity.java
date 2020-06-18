package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPass,newPass,conPass,email;
    private Button btnChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        currentPass = findViewById(R.id.editTextCurrentPass);
        newPass = findViewById(R.id.editTextNewPass);
        conPass = findViewById(R.id.editTextConPass);
        btnChange = findViewById(R.id.buttonChangePass);
        email = findViewById(R.id.editTextTextEmailAddressChangePass);


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String Email = email.getText().toString();
                String currentPassword = currentPass.getText().toString();
                String newPassword = newPass.getText().toString();
                final String conPassword = conPass.getText().toString();

                if (Email.isEmpty()) {
                    email.setError(getString(R.string.input_error_email));
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError(getString(R.string.input_error_email_invalid));
                    email.requestFocus();
                    return;
                }

                if (newPassword.isEmpty()) {
                    newPass.setError(getString(R.string.input_error_password));
                    newPass.requestFocus();
                    return;
                }

                if (newPassword.length() < 6) {
                    newPass.setError(getString(R.string.input_error_password_length));
                    newPass.requestFocus();
                    return;
                }

                if (conPassword.isEmpty()) {
                    conPass.setError(getString(R.string.input_error_password));
                    conPass.requestFocus();
                    return;
                }

                if (!conPassword.equals(newPassword)) {
                    conPass.setError(getString(R.string.input_error_conpassword));
                    conPass.requestFocus();
                    return;
                }
                else {
                    AuthCredential credential = EmailAuthProvider.getCredential(Email, currentPassword);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(conPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ChangePasswordActivity.this, "Password Updated", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(ChangePasswordActivity.this, "Error password not updated", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Error auth failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            }


         });
    }
}