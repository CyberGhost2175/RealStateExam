package com.example.realstateexamapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.realstateexamapp.activity.DetailActivity;
import com.example.realstateexamapp.domain.RealEstatesDomain;
import com.example.realstateexamapp.R;
import com.example.realstateexamapp.service.RetrofitService;
import com.example.realstateexamapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class RealEstatesAdapter extends RecyclerView.Adapter<RealEstatesAdapter.ViewHolder> {
    private final List<RealEstatesDomain> items;
    private final DecimalFormat formatter;
    private Context context;
    private ViewGroup.LayoutParams layoutParams;

    public RealEstatesAdapter(List<RealEstatesDomain> items, ViewGroup.LayoutParams layoutParams) {
        this.items = items;
        this.layoutParams = layoutParams;
        this.formatter = new DecimalFormat("###,###,###,###,##");
    }

    public RealEstatesAdapter(List<RealEstatesDomain> items) {
        // we init layoutParams in onCreateViewHolder
        this(items, null);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_real_estate, parent, false);
        context = parent.getContext();
        if (layoutParams == null)
            layoutParams = Utils.createLayoutParamsInDp(context, 250, 235, 8);

        inflate.setLayoutParams(layoutParams);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleText.setText(items.get(position).getTitle());
        holder.priceText.setText(String.format("$%s", formatter.format(items.get(position).getPrice())));
        holder.addressText.setText(items.get(position).getAddress());
        Picasso.get().load(RetrofitService.getDownloadFileUrl(items.get(position).getTitlePicture())).into(holder.pic);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
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

    public void setData(List<RealEstatesDomain> newData) {
        this.items.clear();
        this.items.addAll(newData);
    }

    public List<RealEstatesDomain> getItems() {
        return this.items;
    }
}
