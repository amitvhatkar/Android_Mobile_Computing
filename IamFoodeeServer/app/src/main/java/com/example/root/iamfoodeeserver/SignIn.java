package com.example.root.iamfoodeeserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.iamfoodeeserver.Common.Common;
import com.example.root.iamfoodeeserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;
    CheckBox ckbRemember;

    FirebaseDatabase db;
    DatabaseReference users;
    DatabaseReference outlateMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (FButton) findViewById(R.id.btnSignIn);
        ckbRemember=(CheckBox)findViewById(R.id.ckbRemember);

        //Init Paper
        Paper.init(this);

        //Init firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");
        outlateMeta = db.getReference("OutlateMeta");

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signInUser(edtPhone.getText().toString(), edtPassword.getText().toString());
            }
        });

    }

    private void signInUser(String phone, String password) {

        //Save user and Password
        if(ckbRemember.isChecked())
        {
            Paper.book().write(Common.USR_KEY,edtPhone.getText().toString());
            Paper.book().write(Common.PWD_KEY,edtPassword.getText().toString());
        }
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Processing...");
        mDialog.show();


        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.w("Data snapshot",dataSnapshot.toString());
                if(dataSnapshot.child(localPhone).exists()){
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if(Boolean.parseBoolean(user.getIsStaff())){
                        if(user.getPassword().equals(localPassword)){



                            Intent login = new Intent(SignIn.this, Home.class);
                            Common.currentUser = user;
                            //OutlateMeta outlate = dataSnapshot.child(localPhone).getValue(OutlateMeta.class);
                            //outlate.setIsOpen("true");
                            //HashMap<String, Object> result = new HashMap<>();
                            //result.put(localPhone, "true");
                            outlateMeta.child(Common.currentUser.getPhone()).child("isOpen").setValue("true");
                            //finish();
                            startActivity(login);
                            finish();
                        }else{
                            Toast.makeText(SignIn.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignIn.this, "Please Login As Staff", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "User not exists !!!" + localPhone, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*private void changeOpenStatus(final String localPhone) {
        outlateMeta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(localPhone).exists()) {

                    Log.w("ping pong", "signin");
                    //OutlateMeta outlate = dataSnapshot.child(localPhone).getValue(OutlateMeta.class);
                    //outlate.setIsOpen("true");
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(localPhone, "true");
                    outlateMeta.updateChildren(result);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}
