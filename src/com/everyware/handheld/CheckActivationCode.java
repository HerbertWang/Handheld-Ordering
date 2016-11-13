package com.everyware.handheld;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 8/7/2016.
 */
public class CheckActivationCode extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        // do something  // show some progress of loading images
    }

    @Override
    protected String doInBackground(String... str) {

        try {
            URL url = new URL(str[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String result = null;
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();

            return result;

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
