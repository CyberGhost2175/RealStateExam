package com.example.realstateexamapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.realstateexamapp.Activity.DetailActivity;
import com.example.realstateexamapp.Domain.ItemsDomain;
import com.example.realstateexamapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private ArrayList<ItemsDomain> items;
    private DecimalFormat formatter;
    private Context context;

    public ItemsAdapter(ArrayList<ItemsDomain> items) {
        this.items = items;
        this.formatter = new DecimalFormat("###,###,###,###,##");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewholder, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleText.setText(items.get(position).getTitle());
        holder.priceText.setText(String.format("$%s", formatter.format(items.get(position).getPrice())));
        holder.addressText.setText(items.get(position).getAddress());

        @SuppressLint("DiscouragedApi") int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        RequestBuilder<Drawable> glide = Glide.with(holder.itemView.getContext())
                .load(drawableResourceId);

        glide.into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleText;
        private final TextView addressText;
        private final TextView priceText;
        private final ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleText = itemView.findViewById(R.id.titleText);
            this.addressText = itemView.findViewById(R.id.addressText);
            this.priceText = itemView.findViewById(R.id.priceText);
            this.pic = itemView.findViewById(R.id.pic);
        }
    }


}
