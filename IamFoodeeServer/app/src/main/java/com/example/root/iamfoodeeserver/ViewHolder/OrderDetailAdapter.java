package com.example.root.iamfoodeeserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.iamfoodeeserver.Model.Order;
import com.example.root.iamfoodeeserver.R;

import java.util.List;

/**
 * Created by swapnil on 3/4/18.
 */


class MyviewHolder extends RecyclerView.ViewHolder{

    public TextView name,quantity,price,discount;

    public MyviewHolder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.product_name);
        discount=(TextView)itemView.findViewById(R.id.product_discount);
        price=(TextView)itemView.findViewById(R.id.product_total);
        quantity=(TextView)itemView.findViewById(R.id.product_quantity);
    }
}

public class OrderDetailAdapter extends RecyclerView.Adapter<MyviewHolder>{

    List<Order>myOrders;

    public OrderDetailAdapter(List<Order> myOrders) {
        this.myOrders = myOrders;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_layout,parent,false);
        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        Order order=myOrders.get(position);
        holder.name.setText(String.format("Name : %s",order.getProductName()));
        holder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));
        holder.price.setText(String.format("Price : %s",order.getPrice()));
        holder.discount.setText(String.format("Discount : %s",order.getDiscount()));

    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}
