package sample.model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.UUID;

public class UsersCourse {
    private String name;

    private UUID idcourse;

    private Double progress;

    private Integer completed;

    public UsersCourse()
    {
    }

    public UsersCourse(LinkedTreeMap map)
    {
        this.name = (String)map.get("name");
        this.progress = (Double)map.get("progress");
        this.idcourse = UUID.fromString((String)map.get("idcourse"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getIdcourse() {
        return idcourse;
    }

    public void setIdcourse(UUID idcourse) {
        this.idcourse = idcourse;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }
}
