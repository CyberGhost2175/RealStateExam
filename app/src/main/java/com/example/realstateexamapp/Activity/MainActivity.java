package com.example.realstateexamapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.realstateexamapp.Adapter.ItemsAdapter;
import com.example.realstateexamapp.Domain.ItemsDomain;
import com.example.realstateexamapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterNew;
    private RecyclerView.Adapter adapterPopular;
    private RecyclerView recyclerViewPopular;
    private RecyclerView recyclerViewNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        this.recyclerViewPopular = findViewById(R.id.viewPopular);
        this.recyclerViewNew = findViewById(R.id.viewNew);

        this.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.recyclerViewNew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        this.adapterNew = new ItemsAdapter(getStates("new"));
        this.adapterPopular = new ItemsAdapter(getStates("popular"));

        this.recyclerViewNew.setAdapter(adapterNew);
        this.recyclerViewPopular.setAdapter(adapterPopular);
    }

    private ArrayList<ItemsDomain> getStates(String category) {
        String json = getJsonStringFromFile();
        if (json != null) {
            Gson gson = new Gson();
            try {
                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray statesArray = jsonObject.getJSONObject("states").getJSONArray(category);
                Type listType = new TypeToken<List<ItemsDomain>>() {
                }.getType();
                return gson.fromJson(statesArray.toString(), listType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private String getJsonStringFromFile() {
        @SuppressLint("DiscouragedApi") InputStream stream = getApplicationContext().getResources().openRawResource(
                getApplicationContext().getResources().getIdentifier("states", "raw", getApplicationContext().getPackageName())
        );
        String json = null;
        try {
            int size = stream.available();
            byte[] bufferData = new byte[size];
            stream.read(bufferData);
            stream.close();
            json = new String(bufferData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}

