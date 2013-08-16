package com.financial.pyramid.utils;

import com.google.gdata.util.common.base.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:02
 */
public class HTTPClient {

    /*
    * Sending GET requests
    * @url - request url
    * */

    public static List<String> sendRequest(String url) {
        return processRequest(url);
    }

    /*
    * Sending GET requests with parameters
    * @url - request url
    * @params - parameter objects
    * */

    public static List<String> sendRequest(String url, Object... params) {
        String urlRequest = MessageFormat.format(url, params);
        return processRequest(urlRequest);
    }

    /*
    * Sending GET or POST requests with header parameters
    * @url - request url
    * @properties -list of header properties
    * @method - request method GET or POST
    * */

    public static List<String> sendRequest(String url, List<Pair<String, String>> properties, String method) {
        URL requestUrl = null;
        BufferedReader reader = null;
        List<String> response = new ArrayList<String>();
        try {
            requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);

            for (Pair property : properties) {
                connection.addRequestProperty(property.getFirst().toString(), property.getSecond().toString());
            }

            connection.setReadTimeout(15000);
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /*
    * Sending POST requests with parameters
    * @url - request url
    * @params - parameter objects
    * */

    public static List<String> sendRequest(String url, List<Pair<String, String>> params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpPost postRequest = new HttpPost(url);
        List<NameValuePair> postParams = new ArrayList<NameValuePair>(params.size());
        for (Pair param : params) {
            if (param.getFirst() != null && param.getSecond() != null) {
                postParams.add(new BasicNameValuePair(param.getFirst().toString(), param.getSecond().toString()));
            }
        }
        try {
            postRequest.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8"));
            response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            List<String> result = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;

    }

    private static List<String> processRequest(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpGet request = new HttpGet(url);
        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            List<String> result = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }
}
