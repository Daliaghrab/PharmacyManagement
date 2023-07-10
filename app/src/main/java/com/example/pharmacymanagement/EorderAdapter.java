package com.example.pharmacymanagement;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EorderAdapter extends RecyclerView.Adapter<MyViewHolder7>{
    private Context context;
    private List<Order> dataList;
    public EorderAdapter(Context context, List<Order> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eorder_recycler, parent, false);
        return new MyViewHolder7(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder7 holder, int position) {
        holder.recProdTitles.setText(dataList.get(position).getProductnamelist());
        holder.recStatus.setText(dataList.get(position).getOrderstatus());
        holder.recProdPrices.setText(dataList.get(position).getProductpricelist());
        holder.recTotPrice.setText(dataList.get(position).getTotprice());
        holder.recaddr.setText(dataList.get(position).getAddr());
        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the order status to "completed"
                String orderId = dataList.get(position).getOrderId();
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
                ordersRef.child("orderstatus").setValue("completed");
                Toast.makeText(context, "Order completed", Toast.LENGTH_SHORT).show();

                // Remove the item from the list
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });
        holder.dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the order status to "completed"
                String orderId = dataList.get(position).getOrderId();
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
                ordersRef.child("orderstatus").setValue("dispatched");
            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<Order> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder7 extends RecyclerView.ViewHolder{
    TextView recProdTitles, recStatus, recTotPrice, recProdPrices, recaddr;
    ImageButton dispatch, completed;

    public MyViewHolder7(@NonNull View itemView) {
        super(itemView);
        recProdTitles = itemView.findViewById(R.id.textView5854);
        recStatus = itemView.findViewById(R.id.textView5754);
        recTotPrice = itemView.findViewById(R.id.textView542254);
        recProdPrices = itemView.findViewById(R.id.textView5954);
        dispatch = itemView.findViewById(R.id.imageButton13);
        completed = itemView.findViewById(R.id.imageButton14);
        recaddr =  itemView.findViewById(R.id.textView61);
    }
}
