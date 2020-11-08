package ru.kpfu.itis.iskander.classes;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String city;
    private String password;
    private Timestamp registeredAt;

    public User(int id, String name, String surname, String email, String city, String password, Timestamp registeredAt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.password = password;
        this.registeredAt = registeredAt;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public String getFormattedRegisteredAt() {
        return registeredAt.toString().split(" ")[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
