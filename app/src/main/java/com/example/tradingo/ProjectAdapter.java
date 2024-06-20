package com.example.tradingo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    ArrayList<ProjectModel> list;
    Context context;

    public ProjectAdapter(ArrayList<ProjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProjectModel model = list.get(position);

        if (model.getLinksOfImgsArray() != null && model.getLinksOfImgsArray().size() > 0) {
            Uri imageUri;
            imageUri = Uri.parse(model.getLinksOfImgsArray().get(0));
            Picasso.get().load(imageUri).placeholder(R.drawable.sale).into(holder.itemImage);
        } else {
            Picasso.get().load(R.drawable.sale).into(holder.itemImage);
        }

        holder.itemTitle.setText(model.getProductTitle());
        holder.itemDescription.setText(model.getDescription());
        holder.itemPrice.setText(model.getPrice());
        holder.itemStock.setText(model.getStock());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProductActivity.class);
                intent.putExtra("singleTitle", model.getProductTitle());
                intent.putExtra("singleDescription", model.getDescription());
                intent.putExtra("singlePrice", model.getPrice());
                intent.putExtra("singleStock", model.getStock());
                intent.putExtra("singleImageArray", model.getLinksOfImgsArray().toArray(new String[0]));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitle, itemDescription, itemPrice, itemStock;

        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemStock = itemView.findViewById(R.id.itemStock);

            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}

