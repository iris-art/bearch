package com.example.bearch;

import android.media.Image;

public class User {


    private String name;
    private String email;
    private String location;
    private String genre;
    private String instrument;
    private String imageURI;

    public User(String Name, String Email, String Location, String Genre, String Instrument, String ImageURI){
        name = Name;
        email = Email;
        location = Location;
        genre = Genre;
        instrument = Instrument;
        imageURI = ImageURI;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getGenre() {
        return genre;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getImageURI() {
        return imageURI;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
