package com.karthiktangirala.locationdemo.locationapiretrofittwodemo;

import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.LocationsApi;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by karthiktangirala on 6/24/16.
 */
public class LocationsApiImpl {

    private static String BASE_URL = "https://maps.googleapis.com";
    Retrofit retrofit;
    LocationsApi service;

    public LocationsApiImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(LocationsApi.class);
    }

}
