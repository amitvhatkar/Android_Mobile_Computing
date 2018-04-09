package com.example.swapnil.iamfoodee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swapnil.iamfoodee.Common.Common;
import com.example.swapnil.iamfoodee.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    Button btnSignIn,btnSignUp;
    TextView txtSlogan;

    boolean isAnyOutlateOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn =(Button) findViewById(R.id.btnSignIn);
        btnSignUp =(Button) findViewById(R.id.btnSignUp);

        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
        txtSlogan.setTypeface(face);

        //Init Paper
        Paper.init(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singUp=new Intent(MainActivity.this,SignUp.class);
                startActivity(singUp);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singIn=new Intent(MainActivity.this,SignIn.class);
                startActivity(singIn);
            }
        });

        //Check Remember Me
        final String user=Paper.book().read(Common.USR_KEY);
        final String pwd=Paper.book().read(Common.PWD_KEY);

        if(user!=null && pwd!=null)
        {

                if (!user.isEmpty() && !pwd.isEmpty()) {
                    DatabaseReference outlateMeta = FirebaseDatabase.getInstance().getReference("OutlateMeta");
                    outlateMeta.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataValue:
                                    dataSnapshot.getChildren()){

                                if(dataValue.child("isOpen").getValue().toString().equals("true")){
                                    isAnyOutlateOpen = true;
                                }

                            }
                            if(isAnyOutlateOpen){
                                login(user, pwd);
                            }else{
                                Toast.makeText(MainActivity.this, "All Outlates Are Clsoed Now !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }


    }


    private void login(final String phone, final String pwd) {
        //Same as signin.class


        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        if (Common.isConnectedToInternet(getBaseContext())) {

            //Save user and Password

            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Please Wait ...");
            mDialog.show();
            table_user.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Check if user exists
                    if (dataSnapshot.child(phone).exists()) {


                        //Get User Information
                        mDialog.dismiss();
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone);
                        if (user.getPassword().equals(pwd)) {
                            Intent homeIntent = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Sign in failed !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "User not exist in Database !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else
        {
            Toast.makeText(MainActivity.this, "Please check your Internet Connection!!", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}