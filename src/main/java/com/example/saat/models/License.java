package com.example.saat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "HH:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Long startTime;

//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "HH:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Long endTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "licenses", fetch = FetchType.LAZY)
    private Set<Content> contents = new HashSet<>();

    public License() {
    }

    public License(Long id, String name, Long startTime,
                   Long endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public License(String name, Long startTime,
                   Long endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }


    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
