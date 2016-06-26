package com.karthiktangirala.locationdemo.locationapiretrofittwodemo.networking;

/**
 * Created by karthiktangirala on 6/24/16.
 */
public class Locations {
    Addresses[] results;
    String status;

    public Addresses[] getResults() {
        return results;
    }

    public void setResults(Addresses[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
