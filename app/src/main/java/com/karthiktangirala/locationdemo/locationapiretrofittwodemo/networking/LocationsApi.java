package com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karthiktangirala on 6/24/16.
 */
public interface LocationsApi {

    @GET("/maps/api/geocode/json")
    Call<Locations> getLocations(@Query("address") String address);

}
