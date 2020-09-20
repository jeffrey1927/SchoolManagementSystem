package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentOwnSubjectActivity extends AppCompatActivity {

    //RecyclerView recyclerView;
    ListView listView;
    Spinner spinner;

    DatabaseReference databaseReference;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentUser.getUid();

    ArrayList<String> studentIntakeDataList,subjectTakeList;
    ArrayAdapter<String> studentIntakeAdapter,subjectTakeAdapter;
    ValueEventListener studentIntakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_own_subject);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userID);

        //recyclerView = findViewById(R.id.recyclerViewSubject);
        listView = findViewById(R.id.listViewSubject);
        spinner = findViewById(R.id.studentIntakeSpinner5);

        studentIntakeDataList = new ArrayList<>();
        studentIntakeAdapter = new ArrayAdapter<String>(StudentOwnSubjectActivity.this,android.R.layout.simple_spinner_dropdown_item,studentIntakeDataList);
        spinner.setAdapter(studentIntakeAdapter);

        subjectTakeList = new ArrayList<>();
        subjectTakeAdapter = new ArrayAdapter<String>(StudentOwnSubjectActivity.this,android.R.layout.simple_list_item_1,subjectTakeList);
        listView.setAdapter(subjectTakeAdapter);

        retrieveStudentData();

    }

    public void retrieveStudentData(){
        studentIntakeListener = databaseReference.child("Subject Take").addValueEventListener(new ValueEventListener() {
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjectTakeList.clear();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String subjectId = snapshot.child("Subject Take").child(spinner.getSelectedItem().toString()).getValue().toString();
                        subjectTakeList.add(subjectId);
                        subjectTakeAdapter.notifyDataSetChanged();
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