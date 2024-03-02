package com.surakshasathi;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FetchComplaints extends AppCompatActivity {
    ListView lv;
    DatabaseReference databaseReference;
    SharedPreferenceClass sharedPreferenceClass;
    ArrayList<String> complaintsList;
    ArrayList<String> original;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_complaints);
        lv = findViewById( R.id.complaintlist);
        original = new ArrayList<>();



        databaseReference = FirebaseDatabase.getInstance().getReference("COMPAINTS");
        sharedPreferenceClass = new SharedPreferenceClass(this);
        complaintsList = new ArrayList<>();

        fetchComplaints();


        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog( original.get( position ),position );
            }
        } );






    }



    private void fetchComplaints() {
        String token = sharedPreferenceClass.getValue_string("token");
        DatabaseReference tokenReference = databaseReference.child(token);

        tokenReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                complaintsList.clear(); // Clear existing data
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    String complaint = child.getValue(String.class);
                    original.add( complaint );
                    complaint = removeLastTwoLines( complaint);

                    complaintsList.add(complaint);
                }
                updateComplaintsListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }


    private void updateComplaintsListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>( FetchComplaints.this,
                android.R.layout.simple_list_item_1, complaintsList );
        lv.setAdapter( adapter );
    }



        private void showDeleteConfirmationDialog(final String complaint,int p) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FetchComplaints.this);
            builder.setTitle("Delete Complaint");
            builder.setMessage("Are you sure you want to delete this complaint?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform delete operation
                    deleteComplaint(complaint,p);
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }

        private void deleteComplaint(String complaint,int position) {
            String token = sharedPreferenceClass.getValue_string("token");
            DatabaseReference tokenReference = databaseReference.child(token);

            tokenReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot child = iterator.next();
                        if (TextUtils.equals(child.getValue(String.class), complaint)) {
                            // Remove the complaint from the database
                            child.getRef().removeValue();
                            original.remove( position );
                            Toast.makeText(FetchComplaints.this, "Complaint deleted", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }


    public static String removeLastTwoLines(String input) {
        // Split the input string into lines
        String[] lines = input.split("\n");

        // Check if there are at least two lines
        if (lines.length >= 2) {
            // Remove the last two lines
            String[] remainingLines = Arrays.copyOfRange(lines, 0, lines.length - 2);

            // Join the remaining lines back together
            return String.join("\n", remainingLines);
        } else {
            // If there are less than two lines, return the original string
            return input;
        }
    }

}