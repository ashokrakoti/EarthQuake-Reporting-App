package com.example.android.quakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthQuakeLoader.class.getName();

    /** Query Url*/
    private String mUrl;

    /**
     * we pass the url string from where the data is loaded.
     * @param context current context or activity that calls this class constructor.
     * @param url to load the data from.
     */
    public EarthQuakeLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    /**This is on a background thread.*/
    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        return QueryUtils.fetchEarthQuakeData(mUrl);
    }
}