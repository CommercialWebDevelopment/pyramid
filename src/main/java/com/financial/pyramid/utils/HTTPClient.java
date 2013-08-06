package com.financial.pyramid.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:02
 */
public class HTTPClient {

    public static List<String> sendRequest(String url, Object... params){
        String urlRequest = MessageFormat.format(url, params);
        return processRequest(urlRequest);
    }

    private static List<String> processRequest(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
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
        }
        finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }
}
