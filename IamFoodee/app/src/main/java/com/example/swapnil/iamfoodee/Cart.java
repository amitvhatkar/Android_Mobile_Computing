package com.example.swapnil.iamfoodee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swapnil.iamfoodee.Common.Common;
import com.example.swapnil.iamfoodee.Database.Database;
import com.example.swapnil.iamfoodee.Model.Order;
import com.example.swapnil.iamfoodee.Model.Request;
import com.example.swapnil.iamfoodee.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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
                //submit to fire base
                //we will be using System.CurrentMilli as key
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                //Delete cart
                new Database(getBaseContext()).clearCart();
                Toast.makeText(Cart.this, "Thank You, Order Placed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        loadListFood();

    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        cartAdapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(cartAdapter);

        //Calculate total
        int total = 0;
        for(Order order:cart){
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));


    }


}
