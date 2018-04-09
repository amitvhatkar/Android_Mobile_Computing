package com.example.swapnil.iamfoodee.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.swapnil.iamfoodee.Interface.ItemClickListener;
import com.example.swapnil.iamfoodee.R;

/**
 * Created by asvhatkar on 21/3/18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone,txtOrderDate;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderPhone= (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderStatus= (TextView)itemView.findViewById(R.id.order_status);
        txtOrderDate= (TextView)itemView.findViewById(R.id.order_date);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

            //itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
