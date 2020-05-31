package sample.model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.UUID;

public class Lesson {

    private UUID id;
    private String name;
    private String theory;
    private Long reward;

    public Lesson() {

    }

    public Lesson(LinkedTreeMap map)
    {
        this.id = UUID.fromString((String)map.get("id"));
        this.name = (String)map.get("name");
        this.theory = (String)map.get("theory");
        this.reward = Math.round((Double)map.get("reward"));
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

    public String getTheory() {
        return theory;
    }

    public void setTheory(String theory) {
        this.theory = theory;
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }
}
