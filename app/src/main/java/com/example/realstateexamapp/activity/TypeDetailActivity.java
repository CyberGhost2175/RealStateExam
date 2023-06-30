package com.example.realstateexamapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realstateexamapp.R;
import com.example.realstateexamapp.adapter.RealEstatesAdapter;
import com.example.realstateexamapp.domain.RealEstatesDomain;
import com.example.realstateexamapp.service.ApiService;
import com.example.realstateexamapp.service.RetrofitService;
import com.example.realstateexamapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeDetailActivity extends AppCompatActivity {

    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";

    private RealEstatesAdapter realEstatesAdapter;
    private RecyclerView recyclerView;
    private ApiService apiService;
    private String title;
    private String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        this.title = getIntent().getStringExtra(TITLE_KEY);
        this.description = getIntent().getStringExtra(DESCRIPTION_KEY);

        initVariables();
        initRecyclerView();
        initRecyclerViewItems();
    }


    private void initRecyclerViewItems() {
        getByCategory(title, realEstatesAdapter);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        realEstatesAdapter = new RealEstatesAdapter(new ArrayList<>(), Utils.createLayoutParams(getApplicationContext(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), 18));
        recyclerView.setAdapter(realEstatesAdapter);
    }

    private void initVariables(){
        recyclerView = findViewById(R.id.categoryDetailRecycleView);
        apiService = new RetrofitService().getRetrofit().create(ApiService.class);
        TextView titleTextView = findViewById(R.id.titleText);
        TextView descriptionTextView = findViewById(R.id.descriptionText);

        if(title != null){
            titleTextView.setText(title);
        }
        else Log.e("initVariables error", "title must be defined");
        if(description != null){
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(description);
        }
    }


    private void getByCategory(String type, RealEstatesAdapter adapter) {
        apiService.getByType(type).enqueue(new Callback<List<RealEstatesDomain>>() {
            @Override
            public void onResponse(@NonNull Call<List<RealEstatesDomain>> call, @NonNull Response<List<RealEstatesDomain>> response) {
                if (response.isSuccessful()) {
                    List<RealEstatesDomain> realEstatesDomains = response.body();
                    if (realEstatesDomains != null) {
                        adapter.setData(realEstatesDomains); // Update the adapter data
                        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                    }
                } else {
                    Log.e("ERROR API", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RealEstatesDomain>> call, @NonNull Throwable t) {
                Log.e("ERROR API(onFailure)", t.getMessage());
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
