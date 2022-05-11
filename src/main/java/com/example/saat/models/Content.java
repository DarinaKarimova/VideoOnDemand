package com.example.saat.models;


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
    private String status;
    private String posterUrl;
    private String videoUrl;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(joinColumns =
            {
            @JoinColumn(name = "contents_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "licenses_id", referencedColumnName = "id")
            })
    private Set<License> licenses = new HashSet<>();

    public Content(Long id, String name, String status, String posterUrl, String videoUrl, Set<License> licenses) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.posterUrl = posterUrl;
        this.videoUrl = videoUrl;
        this.licenses = licenses;
    }


    public Content() {
    }

    public Content(Long id, String name, String status,
                    String posterUrl, String videoUrl) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.posterUrl = posterUrl;
        this.videoUrl = videoUrl;
    }

    public Content(String name, String status,
                    String posterUrl, String videoUrl) {
        this.name = name;
        this.status = status;
        this.posterUrl = posterUrl;
        this.videoUrl = videoUrl;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
