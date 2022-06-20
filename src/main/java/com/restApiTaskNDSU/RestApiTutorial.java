package com.restApiTaskNDSU;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class RestApiTutorial {

    public static int fileNumber = 0;
    // The main list to hold all the groups
    public static Map<String, ArrayList<String>> grouperList = new HashMap<>();


    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    // HTTP response generator
    public static String makeNewHTTPRequest (String guID) throws IOException, InterruptedException {
        HttpClient newClient = HttpClient.newHttpClient();
        HttpRequest newRequest = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", getBasicAuthenticationHeader(Constants.USERNAME, Constants.PASSWORD))
                .uri(URI.create(Constants.URL+guID+".json"))
                .build();
        HttpResponse<String> newResponse = newClient.send(newRequest, HttpResponse.BodyHandlers.ofString());
        String newResponse2 = newResponse.body();
        return newResponse2;
    }

    // Get the key searched for
    public static void getKey(JSONObject json, String key) throws IOException, InterruptedException {

        boolean exists = json.has(key);
        Iterator<?> keys;
        String nextKeys;

        if (!exists) {
            keys = json.keys();  //json.keys() returns an array of JSON object attributes
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                try {
                    //json.get() will return the value of the attribute/key
                    //instanceof provides the information of whether the value received from json.get() is an object or an array
                    if (json.get(nextKeys) instanceof JSONObject) {

                        if (!exists) {
                            getKey(json.getJSONObject(nextKeys), key);
                        }

                    } else if (json.get(nextKeys) instanceof JSONArray) {
                        JSONArray jsonarray = json.getJSONArray(nextKeys);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String jsonarrayString = jsonarray.get(i).toString();
                            JSONObject innerJSOn = new JSONObject(jsonarrayString);

                            if (!exists) {
                                getKey(innerJSOn, key);
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } else {
            String keyValue = (String) json.get(key);
            String guID = (String) json.get("guid");
            writeGroupsToFiles(keyValue, guID);
        }
    }

    // Retrieve usernames of the groups and create lists of usernames and write lists to output files
    public static void writeGroupsToFiles (String keyValue, String guID) throws IOException, InterruptedException {
        String response = makeNewHTTPRequest(guID);
        JSONArray jsonArray = new JSONArray(response);

        ArrayList<String> usernames = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            String arrayElementToString = jsonArray.get(i).toString();
            JSONObject arrayElementToObject = new JSONObject(arrayElementToString);
            usernames.add(arrayElementToObject.get("username").toString());
        }
        grouperList.put(keyValue, usernames);

        try{
            // Writing to output files
            fileNumber++;
            File file = new File ("outputFile_"+fileNumber+"_"+keyValue+"List.txt");
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("ListName = "+keyValue);

            for(String user : usernames)
                printWriter.println(user);
            printWriter.println();

            printWriter.close();
        }catch(IOException e){
            System.out.println("writing failed");
        }
    }

    public static void main(String[] args) throws Exception{

        // Create HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", getBasicAuthenticationHeader(Constants.USERNAME, Constants.PASSWORD))
                .uri(URI.create(Constants.URL+"tree.json"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject inputJSONOBject = new JSONObject(response.body());

        // Recursive function to find the nodes with listName attribute
        getKey(inputJSONOBject, "listName");

    }
}
