package com.example.pharmacymanagement;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class productAdapter2 extends RecyclerView.Adapter<MyViewHolder2>{

    public static ArrayList<ProductClass> cartItems = new ArrayList<>();
    private Context context;
    private List<ProductClass> dataList;
    public productAdapter2(Context context, List<ProductClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler2, parent, false);
        return new MyViewHolder2(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
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
        holder.addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductClass product = dataList.get(holder.getAdapterPosition());
                cartItems.add(product);
                Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();

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

}
class MyViewHolder2 extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recPrice, recQuantity, recCategory;
    ImageButton addtocartbtn;
    ConstraintLayout recCard;
    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.imageView2022);
        recCard = itemView.findViewById(R.id.imageView190022);
        recQuantity = itemView.findViewById(R.id.textView4922);
        recDesc = itemView.findViewById(R.id.editTextTextMultiLine22);
        recPrice = itemView.findViewById(R.id.textView5322);
        recTitle = itemView.findViewById(R.id.textView4222);
        addtocartbtn = itemView.findViewById(R.id.imageButton12);
    }
}
