package com.example.iTube.model;

/**
 * This is a User class, a model to represent individual users in the system.
 * It holds the user_id, username, password, and full_name information.
 *
 * @author Navin
 */
public class User {

    // ID of the user
    private int user_id;

    // Username of the user
    private String username;

    // Password of the user
    private String password;

    // Full name of the user
    private String full_name;

    // Getter method for full_name
    public String getFull_name() {
        return full_name;
    }

    // Setter method for full_name
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    // Constructor for creating a User object with full_name, username and password
    public User(String full_name, String username, String password) {
        this.username = username;
        this.password = password;
        this.full_name = full_name;
    }

    // Empty constructor for creating a User object without setting properties
    public User() {
    }

    // Getter method for user_id
    public int getUser_id() {
        return user_id;
    }

    // Setter method for user_id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }
}
