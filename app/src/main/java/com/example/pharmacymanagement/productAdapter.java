package com.example.pharmacymanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private List<ProductClass> dataList;
    public productAdapter(Context context, List<ProductClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getProdImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getProdName());
        holder.recDesc.setText(dataList.get(position).getProdDesc());
        holder.recPrice.setText(dataList.get(position).getProdPrice());
        holder.recQuantity.setText(dataList.get(position).getProdQuantity());
//        holder.recCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, productaddActivity.class);
//                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getProdImage());
//                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getProdDesc());
//                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getProdName());
//                intent.putExtra("Price",dataList.get(holder.getAdapterPosition()).getProdPrice());
//                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
//                intent.putExtra("Quantity", dataList.get(holder.getAdapterPosition()).getProdQuantity());
//                intent.putExtra("Category", dataList.get(holder.getAdapterPosition()).getProdCategory());
////                context.startActivity(intent);
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<ProductClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recPrice, recQuantity, recCategory;
    ConstraintLayout recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.imageView20);
        recCard = itemView.findViewById(R.id.imageView1900);
        recQuantity = itemView.findViewById(R.id.textView49);
        recDesc = itemView.findViewById(R.id.editTextTextMultiLine);
        recPrice = itemView.findViewById(R.id.textView53);
        recTitle = itemView.findViewById(R.id.textView42);
    }
}
