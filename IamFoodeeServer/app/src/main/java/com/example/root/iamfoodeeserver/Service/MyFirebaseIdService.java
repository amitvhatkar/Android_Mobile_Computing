package com.example.root.iamfoodeeserver.Service;

import com.example.root.iamfoodeeserver.Common.Common;
import com.example.root.iamfoodeeserver.Model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by swapnil on 6/4/18.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
            updateTokenToFirebase(refreshedToken);
    }

    private void updateTokenToFirebase(String refreshedToken) {

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference("Tokens");
        Token data=new Token(refreshedToken,true);//true bcauz this token is sent from Server
        tokens.child(Common.currentUser.getPhone()).setValue(data);
    }
}
