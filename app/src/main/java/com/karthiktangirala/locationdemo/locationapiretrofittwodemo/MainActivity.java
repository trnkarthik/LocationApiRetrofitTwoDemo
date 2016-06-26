package com.karthiktangirala.locationdemo.locationapiretrofittwodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Addresses;
import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Locations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText searchBox;
    ListView listView;
    Button goButton;

    LocationsApiImpl locationsApi;

    ArrayAdapter adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationsApi = new LocationsApiImpl();

        searchBox = (EditText) findViewById(R.id.search_box);
        listView = (ListView) findViewById(R.id.list);
        goButton = (Button) findViewById(R.id.go);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBox.getText().toString();
                if (!text.isEmpty()) {
                    locationsApi.service.getLocations(text).enqueue(new Callback<Locations>() {
                        @Override
                        public void onResponse(Call<Locations> call, Response<Locations> response) {
                            adapter.clear();
                            Addresses[] addresses = response.body().getResults();
                            for (Addresses address : addresses) {
                                adapter.add(address.getFormatted_address());
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Locations> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
}
