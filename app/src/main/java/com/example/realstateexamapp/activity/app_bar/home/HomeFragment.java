package com.example.realstateexamapp.activity.app_bar.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realstateexamapp.R;
import com.example.realstateexamapp.activity.TypeDetailActivity;
import com.example.realstateexamapp.adapter.RealEstatesAdapter;
import com.example.realstateexamapp.domain.RealEstatesDomain;
import com.example.realstateexamapp.service.ApiService;
import com.example.realstateexamapp.service.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String NEW_ITEMS_KEY = "NEW_ITEMS";
    private static final String POPULAR_ITEMS_KEY = "POPULAR_ITEMS";
    private RealEstatesAdapter adapterNew;
    private RealEstatesAdapter adapterPopular;
    private RecyclerView recyclerViewPopular;
    private RecyclerView recyclerViewNew;
    private ApiService apiService;
    private boolean dataFetched = false;
    private View homeCategory;
    private View officeCategory;
    private View villaCategory;
    private View apartmentCategory;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private void initRecyclerViewItems() {
        if (!dataFetched) {
            getByCategory("new", adapterNew, NEW_ITEMS_KEY);
            getByCategory("popular", adapterPopular, POPULAR_ITEMS_KEY);
        } else {
            List<RealEstatesDomain> newItems = getFromSharedPreferences(NEW_ITEMS_KEY);
            adapterNew.setData(newItems);
            adapterNew.notifyDataSetChanged();
            adapterPopular.setData(getFromSharedPreferences(POPULAR_ITEMS_KEY));
            adapterPopular.notifyDataSetChanged();
        }

    }

    private void initRecyclerView() {
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapterNew = new RealEstatesAdapter(new ArrayList<>());
        adapterPopular = new RealEstatesAdapter(new ArrayList<>());

        recyclerViewNew.setAdapter(adapterNew);
        recyclerViewPopular.setAdapter(adapterPopular);
    }

    private void initListeners() {
        apartmentCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), TypeDetailActivity.class);
            intent.putExtra(TypeDetailActivity.TITLE_KEY, getString(R.string.apartment));
            intent.putExtra(TypeDetailActivity.DESCRIPTION_KEY, getString(R.string.apartment_type_description));
            startActivity(intent);
        });
        homeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), TypeDetailActivity.class);
            intent.putExtra(TypeDetailActivity.TITLE_KEY, getString(R.string.home));
            intent.putExtra(TypeDetailActivity.DESCRIPTION_KEY, getString(R.string.home_type_description));
            startActivity(intent);
        });
        officeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), TypeDetailActivity.class);
            intent.putExtra(TypeDetailActivity.TITLE_KEY, getString(R.string.office));
            intent.putExtra(TypeDetailActivity.DESCRIPTION_KEY, getString(R.string.office_type_description));
            startActivity(intent);
        });
        villaCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), TypeDetailActivity.class);
            intent.putExtra(TypeDetailActivity.TITLE_KEY, getString(R.string.villa));
            intent.putExtra(TypeDetailActivity.DESCRIPTION_KEY, getString(R.string.villa_type_description));
            startActivity(intent);
        });
    }

    private void initVariables(View root) {

        recyclerViewPopular = root.findViewById(R.id.viewPopular);
        recyclerViewNew = root.findViewById(R.id.viewNew);
        apiService = new RetrofitService().getRetrofit().create(ApiService.class);

        apartmentCategory = root.findViewById(R.id.apartmentCategory);
        homeCategory = root.findViewById(R.id.homeCategory);
        officeCategory = root.findViewById(R.id.officeCategory);
        villaCategory = root.findViewById(R.id.villaCategory);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initVariables(root);
        initListeners();
        initRecyclerView();
        initRecyclerViewItems();

        return root;
    }


    private void saveToSharedPreferences(String key, List<RealEstatesDomain> realEstates) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(realEstates);
        editor.putString(key, json);
        editor.apply();
    }

    private List<RealEstatesDomain> getFromSharedPreferences(String key) {
        Gson gson = new Gson();
        String json = getSharedPreferences().getString(key, null);
        Type type = new TypeToken<List<RealEstatesDomain>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private SharedPreferences getSharedPreferences() {
        return requireActivity().getPreferences(Context.MODE_PRIVATE);
    }

    private void getByCategory(String category, RealEstatesAdapter adapter, String keyToSave) {
        apiService.getByCategory(category).enqueue(new Callback<List<RealEstatesDomain>>() {
            @Override
            public void onResponse(@NonNull Call<List<RealEstatesDomain>> call, @NonNull Response<List<RealEstatesDomain>> response) {
                if (response.isSuccessful()) {
                    List<RealEstatesDomain> realEstatesDomains = response.body();
                    if (realEstatesDomains != null) {
                        adapter.setData(realEstatesDomains); // Update the adapter data
                        saveToSharedPreferences(keyToSave, adapter.getItems());
                        dataFetched = true;
                        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                    }
                } else {
                    Log.e("ERROR API", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RealEstatesDomain>> call, @NonNull Throwable t) {
                Log.e("ERROR API", "ERRRORRRR");
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
