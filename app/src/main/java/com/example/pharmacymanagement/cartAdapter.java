package com.example.pharmacymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<MyViewHolder3>{
    private Context context;
    private List<ProductClass> dataList;
    public cartAdapter(Context context, List<ProductClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public void clearCartItems() {
        dataList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler1, parent, false);
        return new MyViewHolder3(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        Glide.with(context).load(dataList.get(position).getProdImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getProdName());
        holder.recDesc.setText(dataList.get(position).getProdDesc());
        holder.recPrice.setText(dataList.get(position).getProdPrice());
        holder.recQuantity.setText(dataList.get(position).getProdCategory());
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
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of the item to be removed
                int itemPosition = holder.getAdapterPosition();

                // Remove the item from the dataList
                dataList.remove(itemPosition);

                // Notify the adapter about the item removal
                notifyItemRemoved(itemPosition);
                notifyItemRangeChanged(itemPosition, dataList.size());
                updateTotalBill();
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<ProductClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

    public void setDataList(List<ProductClass> dataList) {
        this.dataList = dataList;
    }

    public void updateTotalBill() {
        // Calculate the total bill based on the current items in dataList
        double totalBill = 0;
        for (ProductClass item : dataList) {
            double price = Double.parseDouble(item.getProdPrice());
            totalBill += price;
        }

        // Update the total bill in the CartActivity
        if (context instanceof CartActivity) {
            ((CartActivity) context).updateTotalBill(totalBill);
        }
    }

}
class MyViewHolder3 extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recPrice, recQuantity, recCategory;
    ImageButton remove;
    ConstraintLayout recCard;
    public MyViewHolder3(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.imageView20227);
        recCard = itemView.findViewById(R.id.imageView1900227);
        recQuantity = itemView.findViewById(R.id.textView49227);
        recDesc = itemView.findViewById(R.id.editTextTextMultiLine227);
        recPrice = itemView.findViewById(R.id.textView53227);
        recTitle = itemView.findViewById(R.id.textView42227);
        remove = itemView.findViewById(R.id.imageButton127);
    }
}
