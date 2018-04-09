package com.example.swapnil.iamfoodee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swapnil.iamfoodee.Common.Common;
import com.example.swapnil.iamfoodee.Database.Database;
import com.example.swapnil.iamfoodee.Model.MyResponse;
import com.example.swapnil.iamfoodee.Model.Notification;
import com.example.swapnil.iamfoodee.Model.Order;
import com.example.swapnil.iamfoodee.Model.Request;
import com.example.swapnil.iamfoodee.Model.Sender;
import com.example.swapnil.iamfoodee.Model.Token;
import com.example.swapnil.iamfoodee.Remote.APIService;
import com.example.swapnil.iamfoodee.ViewHolder.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    boolean isAnyOutlateOpen = false;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter cartAdapter;

    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Init Service
        mService =Common.getFCMService();

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        //Init
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new Request
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
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
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                        if(isAnyOutlateOpen) {
                        //submit to fire base
                        //we will be using System.CurrentMilli as key
                                if (cart.size() != 0) {
                                    String order_number = String.valueOf(System.currentTimeMillis());
                                    requests.child(order_number).setValue(request);
                                    //Delete cart
                                    new Database(getBaseContext()).clearCart();

                                    sendNotificationOrder(order_number);
                                    Toast.makeText(Cart.this, "Thank You, Order Placed", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(Cart.this, "Your cart is Empty", Toast.LENGTH_SHORT).show();
                                }
                                }
                                else{
                                    Toast.makeText(Cart.this, "All Outlates Are Closed!!!", Toast.LENGTH_SHORT).show();
                                }

                        }
                    });


                    loadListFood();
                }






    private void sendNotificationOrder(final String order_number) {
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        Query data=tokens.orderByChild("isServerToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                {
                    Token serverToken =postSnapShot.getValue(Token.class);

                   // Notification notification =new Notification("Swapnil" ,"You have new order "+order_number);
                    Notification notification =new Notification("Yaay" ,"You have new order ");
                    Sender content=new Sender(serverToken.getToken(),notification);

                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                                   if(response.code()==200) { //Run only when get Result
                                       if (response.body().success == 1) {
                                           Toast.makeText(Cart.this, "Thank You, Order Placed", Toast.LENGTH_SHORT).show();
                                           finish();
                                       } else {
                                           Toast.makeText(Cart.this, "Failed !!!", Toast.LENGTH_SHORT).show();

                                       }
                                   }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                    Log.e("ERROR",t.getMessage());

                                }
                            });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        cartAdapter = new CartAdapter(cart, this);
        cartAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(cartAdapter);

        //Calculate total
        int total = 0;
        for(Order order:cart){
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
           // total += (Integer.parseInt(order.getPrice()) * (Integer.parseInt(order.getDiscount())/100)) * (Integer.parseInt(order.getQuantity()));
        }

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int postion) {
        //We will remove item at List<Order>
        cart.remove(postion);
        //After that we will delete all data from SQLite
        new Database(this).clearCart();
        //And finally,we will update new data from List<Order> to SQLite
        for(Order item:cart)
            new Database(this).addToCart(item);

        //Refresh
        loadListFood();

    }
}
