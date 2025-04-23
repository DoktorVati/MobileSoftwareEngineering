package com.zybooks.softwareengineeringexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        // Apply the activity_main layout activity
        setContentView(R.layout.activity_main);

        // Hides the action bar title at the top of the screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Calls the method that Fetches the data from the given URL
                    fetchDataFromUrl();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void fetchDataFromUrl() throws IOException {
        // Create a reference to the given Fetch URL
        URL url = new URL("https://hiring.fetch.com/hiring.json");

        // Get input stream from the URL connection
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();


        //init id array list for all id's, listId's, and names
        ArrayList<Integer> id;
        ArrayList<Integer> listId = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        // Will begin going through line by line
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

        // Calls the data sorter method and passes the data lists
        sortData(listId, id, name);

        // These final variables are needed because it is using them in a lambda expression
        ArrayList<Integer> finalListId = listId;
        ArrayList<String> finalName = name;

        // This runs on the main UI thread since it can't update UI from background thread
        runOnUiThread(() -> {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            // creates and sets the adapter
            recyclerView.setAdapter(new ItemAdapter(finalListId, id, finalName));
        });

    }

    //This function will sort the data first by List ID, then secondly by Name
    private void sortData(ArrayList<Integer> listId, ArrayList<Integer> id, ArrayList<String> name) {
        // Create a list of indices of listId size
        Integer[] indices = new Integer[listId.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        // Sort the indices based on listId first, then by the numeric part of name
        Arrays.sort(indices, (a, b) -> {
            // First compare by listId
            int listIdCompare = listId.get(a).compareTo(listId.get(b));
            if (listIdCompare != 0) {
                return listIdCompare;
            }

            // If listIds are equal, compare by the numeric part of the name
            // This is because all of the names are Item and some #
            try {
                // Extract numbers from names due to the format Item ###
                int numA = extractNumberFromName(name.get(a));
                int numB = extractNumberFromName(name.get(b));
                return Integer.compare(numA, numB);
            } catch (Exception e) {
                // Required Fallback for string comparison if the parsing fails
                return name.get(a).compareTo(name.get(b));
            }
        });

        // Create temporary lists for the sorted vars
        ArrayList<Integer> sortedIds = new ArrayList<>();
        ArrayList<Integer> sortedListIds = new ArrayList<>();
        ArrayList<String> sortedNames = new ArrayList<>();

        // Reorder all the lists to the sorted indices
        for (int index : indices) {
            sortedIds.add(id.get(index));
            sortedListIds.add(listId.get(index));
            sortedNames.add(name.get(index));
        }

        // Replace original lists with the sorted ones
        id.clear();
        listId.clear();
        name.clear();

        id.addAll(sortedIds);
        listId.addAll(sortedListIds);
        name.addAll(sortedNames);
    }

    // Helper method to extract the numeric part from Item XXX
    private int extractNumberFromName(String name) {
        try {
            // Remove "Item " prefix and parse the remaining number
            return Integer.parseInt(name.substring(5).trim());
        } catch (Exception e) {
            return 0; // Required Default value for if the parsing fails
        }
    }
}