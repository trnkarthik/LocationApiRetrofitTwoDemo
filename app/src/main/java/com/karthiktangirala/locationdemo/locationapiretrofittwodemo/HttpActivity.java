package com.karthiktangirala.locationdemo.locationapiretrofittwodemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Addresses;
import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Locations;
import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.parsers.LocationsParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.makeText;

public class HttpActivity extends AppCompatActivity {

    @BindView(R.id.search_box)
    EditText searchBox;
    @BindView(R.id.list)
    ListView listView;

    private ListAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        adapter = new ListAdapter(this, 0, list);

        listView.setAdapter(adapter);
    }

    /**
     * @return true if network is available, false otherwise
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (menu != null) {
            MenuItem httpItem = menu.findItem(R.id.http);
            httpItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.retorfit:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                return false;
        }
        return true;
    }

    @OnClick(R.id.go)
    void onGoButtonClick(View v) {
        String text = searchBox.getText().toString();
        if (!text.isEmpty()) {
            if (isNetworkAvailable()) {
                new GetLocationsAsyncTask().execute(text);
            } else {
                makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetLocationsAsyncTask extends AsyncTask<String, Void, Locations> {

        URL url;

        @Override
        protected Locations doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            StringBuffer stringBuffer = null;
            LocationsParser locationsParser;
            Locations locations = null;

            try {
                stringBuffer = new StringBuffer();
                url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();

                while (line != null) {
                    stringBuffer.append(line);
                    Log.d("HTTPActivity", line);
                    line = bufferedReader.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            if (stringBuffer != null) {
                locationsParser = new LocationsParser();
                locations = locationsParser.parse(stringBuffer);
            }

            return locations;
        }

        @Override
        protected void onPostExecute(Locations locations) {
            adapter.clear();
            Addresses[] addresses = locations.getResults();
            for (Addresses address : addresses) {
                adapter.add(address.getFormatted_address());
            }
            adapter.notifyDataSetChanged();
        }
    }

}
