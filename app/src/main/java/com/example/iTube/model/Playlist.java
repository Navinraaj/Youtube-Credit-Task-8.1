package com.example.iTube.model;

/**
 * This is a Playlist class, a model to represent individual video items in the playlist.
 * It holds the video_uri, user_id and video_id information.
 *
 * @author Navin
 */
public class Playlist {

    // URI for the video
    private  String video_uri;

    // ID of the user associated with the video
    private Integer user_id;

    // ID for the video
    private int video_id;

    // Empty constructor for creating a Playlist object without setting properties
    public Playlist() {
    }

    // Constructor for creating a Playlist object with video_uri and user_id
    public Playlist(String video_uri, int user_id) {
        this.video_uri = video_uri;
        this.user_id = user_id;
    }

    // Getter method for video_id
    public int getVideo_id() {
        return video_id;
    }

    // Setter method for video_id
    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    // Getter method for video_uri
    public String getVideo_uri() {
        return video_uri;
    }

    // Setter method for video_uri
    public void setVideo_uri(String video_uri) {
        this.video_uri = video_uri;
    }

    // Getter method for user_id
    public Integer getUser_id() {
        return user_id;
    }

    // Setter method for user_id
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
