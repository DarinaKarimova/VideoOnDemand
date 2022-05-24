package com.example.saat.models;


import com.example.saat.constants.ContentStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ContentStatus status;
    private String posterUrl;
    private String videoUrl;
    private String provider;
    private Integer year;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(joinColumns =
            {
                    @JoinColumn(name = "contents_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "licenses_id", referencedColumnName = "id")
            })
    private Set<License> licenses = new HashSet<>();

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<VideoOperationProcess> processes;

    public Content(Long id, String name, ContentStatus status, String posterUrl, String videoUrl, String provider, Integer year, Set<License> licenses, Set<VideoOperationProcess> processes) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.posterUrl = posterUrl;
        this.videoUrl = videoUrl;
        this.provider = provider;
        this.year = year;
        this.licenses = licenses;
        this.processes = processes;
    }

    public Content() {
    }

    public Content(Set<License> licenses) {
        this.licenses = licenses;
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

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public Set<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(Set<License> licenses) {
        this.licenses = licenses;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Set<VideoOperationProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<VideoOperationProcess> processes) {
        this.processes = processes;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", licenses='" + licenses + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

    public void enrollLicense(License license) {
        licenses.add(license);
    }
}
