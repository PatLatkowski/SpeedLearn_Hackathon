package sample.model;


import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    public User() {
    }

    private UUID id;

    public ArrayList<LinkedTreeMap> getUserscourses() {
        return userscourses;
    }

    public void setUserscourses(ArrayList<LinkedTreeMap> userscourses) {
        this.userscourses = userscourses;
    }

    private ArrayList<LinkedTreeMap> userscourses;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Integer exp;

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getExp() {
        return exp;
    }
}
