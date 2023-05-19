package com.example.iTube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iTube.data.DatabaseHelperP;
import com.example.iTube.model.Playlist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Home is an activity class representing the home screen of the iTube application.
 * It provides the user with the ability to add YouTube video URLs to a playlist,
 * view the playlist, and play the YouTube videos.
 *
 * @author Navin
 */
public class Home extends AppCompatActivity {

    // Define UI elements
    Button addToPlaylistBtn,playBtn,myPlaylistBtn;
    EditText url;

    // Database helper object for playlist operations
    DatabaseHelperP dbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI elements and database helper
        addToPlaylistBtn = findViewById(R.id.addToPlayListBtn);
        url = findViewById(R.id.url);
        playBtn = findViewById(R.id.playBtn);
        myPlaylistBtn = findViewById(R.id.myPlaylistBtn);
        dbP = new DatabaseHelperP(this);

        // Add click listener to the "Add to playlist" button
        addToPlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if URL is a valid YouTube URL
                if(isYoutubeUrl(url.getText().toString().trim())){
                    // Add video to playlist in the database
                    long result = dbP.insertVideo(new Playlist(url.getText().toString().trim(), getIntent().getIntExtra("user_id", 0)));
                    // Show success or failure message
                    if(result > 0){
                        Toast.makeText(Home.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Home.this, "Couldn't add to playlist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show error message for invalid URL
                    Toast.makeText(Home.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add click listener to the "Play" button
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if URL is a valid YouTube URL
                if(isYoutubeUrl(url.getText().toString().trim())){
                    // Extract video ID from URL
                    String id = getVideoId(url.getText().toString().trim());
                    if(!id.equals("error")){
                        // Start PlayVideo activity to play the video
                        Intent intent = new Intent(Home.this, PlayVideo.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    } else {
                        // Show error message for invalid URL
                        Toast.makeText(Home.this, "Couldn't fetch ID. Please enter a valid URL", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show error message for invalid URL
                    Toast.makeText(Home.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add click listener to the "My Playlist" button
        myPlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // Start MyPlaylist activity to show the playlist
                    Intent oldIntent = getIntent();
                    Intent intent = new Intent(Home.this, MyPlaylist.class);
                    intent.putExtra("user_id", oldIntent.getIntExtra("user_id", 0));
                    startActivity(intent);
                } catch (Exception e){
                    Log.d("reached", e.getMessage());
                }
            }
        });

        // Add focus change listener to the URL EditText to hide the keyboard when it loses focus
        url.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    // Method to check if a URL is a valid YouTube URL
    public static boolean isYoutubeUrl(String youTubeURl) {
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        return !youTubeURl.isEmpty() && youTubeURl.matches(pattern);
    }

    // Method to hide the keyboard
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Method to extract video ID from a YouTube URL
    public static String getVideoId(@NonNull String videoUrl) {
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "error";
        }
    }
}
