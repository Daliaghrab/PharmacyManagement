package com.example.pharmacymanagement;

import android.content.Context;
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

public class CorderAdapter extends RecyclerView.Adapter<MyViewHolder4>{
    private Context context;
    private List<Order> dataList;
    public CorderAdapter(Context context, List<Order> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.corder_recycler, parent, false);
        return new MyViewHolder4(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder4 holder, int position) {
        holder.recProdTitles.setText(dataList.get(position).getProductnamelist());
        holder.recStatus.setText(dataList.get(position).getOrderstatus());
        holder.recProdPrices.setText(dataList.get(position).getProductpricelist());
        holder.recTotPrice.setText(dataList.get(position).getTotprice());
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
class MyViewHolder4 extends RecyclerView.ViewHolder{

    TextView recProdTitles, recStatus, recTotPrice, recProdPrices;

    public MyViewHolder4(@NonNull View itemView) {
        super(itemView);
        recProdTitles = itemView.findViewById(R.id.textView585);
        recStatus = itemView.findViewById(R.id.textView575);
        recTotPrice = itemView.findViewById(R.id.textView54225);
        recProdPrices = itemView.findViewById(R.id.textView595);
    }
}
