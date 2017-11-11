package com.example.itadmin.cryptoexchange;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.itadmin.cryptoexchange.MainActivity.LOG_TAG;

/**
 * Created by Omo_Nosa on 11/10/2017.
 */

public final class QueryUtils {

    private QueryUtils() {
    }



    public static List<Currency> fetchCurrencyData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Currency> currs = extractCurrencies(jsonResponse);

        // Return the {@link Event}
        return currs;
    }

    public static List<Currency> extractCurrencies(String currsJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(currsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding devs to
        List<Currency> currs = new ArrayList<>();

        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // build up a list of Developers objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(currsJSON);

            JSONObject btcObject = baseJsonResponse.getJSONObject("BTC");
            JSONObject ethObj = baseJsonResponse.getJSONObject("ETH");

            Log.e("btc",String.valueOf (btcObject));


            Iterator<String> iter = btcObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                double btcvalue = btcObject.getDouble(key);
                double ethvalue = ethObj.getDouble(key);

                Log.e("key",String.valueOf (key));
                Log.e("value",String.valueOf (btcvalue));

                Currency curr = new Currency(key, btcvalue, ethvalue);

                // Add the new {@link currency}
                currs.add(curr);

            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the currencies JSON results", e);
        }

        // Return the list of currencies
        return currs;
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

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
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
            urlConnection.connect();

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
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
