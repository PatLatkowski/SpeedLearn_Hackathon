package sample.model;

import com.google.gson.internal.LinkedTreeMap;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Course {
    private UUID id;
    private String name;
    private Set<Pair<UUID, String>> lessons = new HashSet<>();

    public Course(LinkedTreeMap map)
    {
        //this.id = (UUID)map.get("id");
        this.name = (String)map.get("name");
    }
    public Course(UUID id, String name, UUID[] lessonsIDs, String[] lessonNames) {
        this.id = id;
        this.name = name;
        for(int i = 0; i < lessonsIDs.length; i++){
            Pair<UUID, String> pair = new Pair<>(lessonsIDs[i],lessonNames[i]);
            lessons.add(pair);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pair<UUID, String>> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Pair<UUID, String>> lessons) {
        this.lessons = lessons;
    }
}
