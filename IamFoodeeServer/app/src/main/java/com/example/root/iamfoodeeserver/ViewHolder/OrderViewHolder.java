package com.example.root.iamfoodeeserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.root.iamfoodeeserver.Common.Common;
import com.example.root.iamfoodeeserver.Interface.ItemClickListener;
import com.example.root.iamfoodeeserver.R;

/**
 * Created by swapnil on 30/3/18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        //View.OnLongClickListener,
        View.OnCreateContextMenuListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone,txtOrderDate;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderPhone= (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderStatus= (TextView)itemView.findViewById(R.id.order_status);
        txtOrderDate= (TextView)itemView.findViewById(R.id.order_date);

        itemView.setOnClickListener(this);
      //  itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the Action");

        contextMenu.add(0,0,getAdapterPosition(),Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(),Common.DELETE);
    }

//    @Override
//    public boolean onLongClick(View view) {
//        itemClickListener.onClick(view, getAdapterPosition(), true);
//        return true;
//    }
}