package com.karthiktangirala.locationdemo.locationapiretrofittwodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Addresses;
import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Locations;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_box)
    EditText searchBox;
    @BindView(R.id.list)
    ListView listView;

    LocationsApiImpl locationsApi;

    ArrayAdapter adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        locationsApi = new LocationsApiImpl();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
    }

    @OnClick(R.id.go)
    void onGoButtonClick(View v) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (menu != null) {
            MenuItem retorfitItem = menu.findItem(R.id.http);
            retorfitItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.http:
                startActivity(new Intent(this, HttpActivity.class));
                break;
            default:
                return false;
        }
        return true;
    }

}
