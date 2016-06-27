package com.karthiktangirala.locationdemo.locationapiretrofittwodemo.parsers;

import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Addresses;
import com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking.Locations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karthiktangirala on 6/26/16.
 */

public class LocationsParser {


    public Locations parse(StringBuffer input) {
        JSONObject locationObject;
        JSONArray resultsArrayObject;

        String status;
        Addresses[] resultsArray;

        JSONObject address;
        String formatted_address;
        Addresses addresses;
        Locations locations = null;

        try {
            locationObject = new JSONObject(input.toString());
            resultsArrayObject = locationObject.getJSONArray("results");

            status = locationObject.getString("status");
            resultsArray = new Addresses[resultsArrayObject.length()];

            locations = new Locations();
            locations.setStatus(status);

            for (int i = 0; i < resultsArrayObject.length(); i++) {
                address = resultsArrayObject.getJSONObject(i);
                formatted_address = address.getString("formatted_address");
                addresses = new Addresses();
                addresses.setFormatted_address(formatted_address);
                resultsArray[i] = addresses;
            }

            locations.setResults(resultsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locations;
    }

}
