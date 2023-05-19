package com.example.iTube;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * PlayVideo is an activity class that extends YouTubeBaseActivity and is used to play a YouTube video
 * within the application using the YouTubePlayer API.
 *
 * @author Navin
 */
public class PlayVideo extends YouTubeBaseActivity {
    // The YouTubePlayerView object to be used for video playback
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        // Initialize the YouTubePlayerView from the layout
        youTubePlayerView = findViewById(R.id.youtubePlayer);

        // Define the initialization listener for the YouTubePlayer
        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // Load the video from the YouTube link and start playing it
                youTubePlayer.loadVideo(getIntent().getStringExtra("id"));
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                // Show an error message if the video can't be played
                Toast.makeText(PlayVideo.this, "can't play this video", Toast.LENGTH_SHORT).show();
            }
        };

        // Initialize the YouTubePlayerView with the API key and the listener
        youTubePlayerView.initialize("AIzaSyDT61bfbMeU5A7ZjGXaBFace4kjl8x6gJ0",listener);

    }
}
