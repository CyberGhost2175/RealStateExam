package com.example.realstateexamapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.realstateexamapp.domain.RealEstatesDomain;
import com.example.realstateexamapp.R;
import com.example.realstateexamapp.service.RetrofitService;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView addressText;
    private TextView priceText;
    private TextView descriptionText;
    private TextView wifiText;
    private TextView bedText;
    private TextView bathText;
    private RealEstatesDomain item;
    private ImageView pic;

    private DecimalFormat formatter = new DecimalFormat("###,###,###,##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        setVariable();
    }

    @SuppressLint("SetTextI18n")
    private void setVariable() {
        this.item = (RealEstatesDomain) getIntent().getSerializableExtra("object");
        titleText.setText(item.getTitle());
        addressText.setText(item.getAddress());
        priceText.setText("$" + formatter.format(item.getPrice()));
        bedText.setText(item.getBed() + " Bed");
        bathText.setText(item.getBath() + " Bath");
        descriptionText.setText(item.getDescription());


        if (item.isWife()) {
            wifiText.setText("Wifi");
        } else {
            wifiText.setText("Not Wifi");
        }
        Picasso.get().load(RetrofitService.getDownloadFileUrl(item.getTitlePicture())).into(pic);
    }


    private void initView() {
        this.titleText = findViewById(R.id.titleText);
        this.addressText = findViewById(R.id.addressText);
        this.priceText = findViewById(R.id.priceText);
        this.descriptionText = findViewById(R.id.descriptionText);
        this.wifiText = findViewById(R.id.wifiText);
        this.bedText = findViewById(R.id.bedText);
        this.bathText = findViewById(R.id.bathText);
        this.pic = findViewById(R.id.pic);
    }
}