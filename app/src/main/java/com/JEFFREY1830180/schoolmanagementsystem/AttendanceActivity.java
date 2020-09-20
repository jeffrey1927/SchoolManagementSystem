package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    ListView listView;
    Spinner dateSpinner,subjectSpinner;

    DatabaseReference databaseReference;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = currentUser.getUid();

    ArrayList<String> dateDataList,subjectList,attendanceList;
    ArrayAdapter<String> dateAdapter,subjectAdapter,attendanceAdapter;
    ValueEventListener dateListener,subjectListener,attendanceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.attendanceListView);
        dateSpinner = findViewById(R.id.dateSpinner);
        subjectSpinner = findViewById(R.id.subjectSpinner);

        dateDataList = new ArrayList<>();
        dateAdapter = new ArrayAdapter<String>(AttendanceActivity.this,android.R.layout.simple_spinner_dropdown_item,dateDataList);
        dateSpinner.setAdapter(dateAdapter);

        subjectList = new ArrayList<>();
        subjectAdapter = new ArrayAdapter<String>(AttendanceActivity.this,android.R.layout.simple_spinner_dropdown_item,subjectList);
        subjectSpinner.setAdapter(subjectAdapter);

        attendanceList = new ArrayList<>();
        attendanceAdapter = new ArrayAdapter<String>(AttendanceActivity.this,android.R.layout.simple_spinner_dropdown_item,attendanceList);
        listView.setAdapter(attendanceAdapter);

        retrieveAttendanceData();
    }

    public void retrieveAttendanceData(){
        dateListener = databaseReference.child("Attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()){

                    dateDataList.add(item.getKey());

                }


                dateAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjectList.clear();
                subjectListener = databaseReference.child("Attendance").child(dateSpinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item:snapshot.getChildren()){

                            subjectList.add(item.getKey());

                        }


                        subjectAdapter.notifyDataSetChanged();
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

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attendanceList.clear();
                attendanceListener = databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String subject = snapshot.child("Attendance").child(dateSpinner.getSelectedItem().toString()).child(subjectSpinner.getSelectedItem().toString()).getValue().toString();
                        attendanceList.add(subject);
                        attendanceAdapter.notifyDataSetChanged();
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