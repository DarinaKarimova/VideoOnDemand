package com.example.saat.models;

import com.example.saat.constants.ProcessStatus;

import javax.persistence.*;

@Entity
@Table
public class VideoOperationProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private Content content;

    public VideoOperationProcess(Long id, String name, Content content, ProcessStatus status) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.status = status;
    }

    public VideoOperationProcess() {
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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Process{" + "id=" + id + ", name='" + name + '\'' + ", content=" + content + '}';
    }
}
