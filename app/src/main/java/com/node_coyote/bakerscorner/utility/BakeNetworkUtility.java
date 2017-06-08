package com.node_coyote.bakerscorner.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by node_coyote on 6/6/17.
 */

public class BakeNetworkUtility {

    /**
     *  A helper method to connect to Miriam's recipe data.
     * @param url The url of Miriam's recipes.
     * @return A string of JSON recipes.
     * @throws IOException Check for Input/Output exceptions.
     */
    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
