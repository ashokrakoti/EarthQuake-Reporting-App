package com.example.android.quakereport;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {

    private static final String LOCATION_SEPARATOR = " of ";
    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private QueryUtils() { }


    public static ArrayList<EarthQuake> fetchEarthQuakeData(String requestUrl)  {
       //creating url object from the url string
        URL url = createUrl(requestUrl); //handled the malformed uri exception using the method. else we could have generated a new url object here.

       String jsonResponse = null;
       try {
           jsonResponse = makeHttpRequest(url);
       }
       catch (IOException ex){
           Log.e(LOG_TAG, "Error closing input stream", ex);
       }
       //extract the earth quake objects from the json response.
        ArrayList<EarthQuake> earthQuakes = extractEarthquakes(jsonResponse);

        return earthQuakes;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early. json response is null.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();//actual connection happens here..............

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while(line != null){
               output.append(line);
               line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link EarthQuake} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<EarthQuake> extractEarthquakes(String json_response) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON.
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonRootObject = new JSONObject(json_response);

            //extracting the features named json array.
            JSONArray jsonArray = jsonRootObject.optJSONArray("features");

            //Iterating the json array and building json objects.
            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject  jsonObject = jsonArray.getJSONObject(i);

                //extracting the properties from the features object.
                JSONObject jsonPropertyObj = jsonObject.getJSONObject("properties");

                //extract the magnitude property from the jsonPropertyObj
                double magnitude = jsonPropertyObj.getDouble("mag" );

                //extract the place from the properties.
                String place = jsonPropertyObj.getString("place");

                String location_offset ;
                String location_primary ;
                if(place.contains(LOCATION_SEPARATOR)){
                    String [] locationDetails = place.split("of");
                    location_offset = locationDetails[0] + "of";
                    location_primary = locationDetails[1];
                }else{
                    location_offset ="Near By";
                            //Context.getString(R.string.near_the); // this string is used from the strings.xml
                    location_primary = place;
                }

                //extract the date from the properties.
                long timeInMilliSecs = Long.parseLong(jsonPropertyObj.getString("time"));
                //conversion to the needed format.
                Date dateObject = new Date(timeInMilliSecs);

                @SuppressLint ("SimpleDateFormat")
                SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyyy");
                String dateToDisplay = date.format(dateObject);

                //extract the time from properties.
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
                String timeToDisplay = timeFormatter.format(dateObject);

                //extract the url from the  properties object
                String url = jsonPropertyObj.getString("url");

                //creating an EarthQuake object out of the data we extracted from the json response.
                earthquakes.add(new EarthQuake(location_offset,location_primary, magnitude, dateToDisplay, timeToDisplay, url));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}
