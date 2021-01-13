package com.example.assignment4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Handler {

    public String httpCall(String requestUrl, String requestMethod)
    {
        String result = null;
        HttpURLConnection connection = null;
        try
        {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);

            InputStream istream = connection.getInputStream();
            result = convertString(istream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (connection != null)
        {
            connection.disconnect();
        }

        return result;
    }

    public String convertString(InputStream istream) throws IOException {
        InputStreamReader isreader = new InputStreamReader(istream);
        String str = "";
        int data = isreader.read();
        while(data != -1)
        {
            str += (char) data;
            data = isreader.read();
        }
        return str;
    }
}
