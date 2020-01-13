package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Table("tasks")
public class Task implements Comparable<Task> {

    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    //    private List<String> files;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }


    public List<String> getFiles() {
        return Collections.emptyList();
    }

    public void setFiles(List<String> files) {
    }

    @Override
    public int compareTo(Task o) {
        return this.getId().compareTo(o.getId());
    }
}
