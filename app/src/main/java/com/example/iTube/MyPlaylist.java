package com.example.iTube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.iTube.data.DatabaseHelper;
import com.example.iTube.data.DatabaseHelperP;
import com.example.iTube.model.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * MyPlaylist is an activity class that represents a screen in the iTube application
 * showing a list of all videos in the user's playlist. The videos are fetched from the database.
 *
 * @author Navin
 */
public class MyPlaylist extends AppCompatActivity {
    // UI elements and necessary objects for database operations and list handling
    ListView playList;
    ArrayList<String> videoList;
    ArrayAdapter<String> adapter;
    DatabaseHelperP dbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        try {
            // Fetch the user_id from the intent extras
            Intent oldIntent = getIntent();
            int user_id = oldIntent.getIntExtra("user_id", 0);

            // Initialize the database helper
            dbP = new DatabaseHelperP(this);

            // Initialize the list view and array list
            playList = findViewById(R.id.playList);
            videoList = new ArrayList<>();

            // Fetch all videos from the database
            List<Playlist> list = dbP.fetchAllVideo(user_id);
            for (Playlist video : list) {
                // Add each video URI to the array list
                videoList.add(video.getVideo_uri());
            }

            // Initialize and set the adapter for the list view
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, videoList);
            playList.setAdapter(adapter);
        } catch (Exception e) {
            // Log any exceptions that occur
            Log.d("reached", e.getMessage());
        }
    }
}
