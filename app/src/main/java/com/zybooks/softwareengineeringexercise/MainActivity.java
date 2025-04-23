package com.zybooks.softwareengineeringexercise;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fetchDataFromUrl();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void fetchDataFromUrl() throws IOException {
        // Create a reference to the Fetch URL
        URL url = new URL("https://hiring.fetch.com/hiring.json");

        // Get input stream from URL connection
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();


        //init id array list for all id's, listId's, and names
        ArrayList<Integer> id;
        ArrayList<Integer> listId = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // read each line and append to stringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // init jsonArray
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());

            //init id array list for all id's, listId's, and names
            id = new ArrayList<>();
            listId = new ArrayList<>();
            name = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) // go through all of the jsonobjects
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // If the name is not empty or not null, it will work with that item and add them to the array lists.
                // It will ignore the item if it does not have a name.

                if (!jsonObject.getString("name").equals("") && !jsonObject.getString("name").equals("null")) {
                    name.add(jsonObject.getString("name"));

                    id.add(jsonObject.getInt("id"));

                    listId.add(jsonObject.getInt("listId"));
                }
            }

            // Logging of all id's, listId's, and Names for debugging
            Log.d("MainActivity", "IDs: " + id + id.size());
            Log.d("MainActivity", "List IDs: " + listId + listId.size());
            Log.d("MainActivity", "Names: " + name + name.size());


        } catch (JSONException e) { // required catches for functions
            throw new RuntimeException(e);
        }

        int counter = 0;
        for (int i = 0; i < id.size(); i++)
        {

        }
        Log.d("MainActivity", "Amount of items built" + counter);
    }
}