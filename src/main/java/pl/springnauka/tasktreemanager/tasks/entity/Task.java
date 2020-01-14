package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Data
@Table("tasks")
public class Task implements Comparable<Task> {

    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    //pierwotnie lista, dla testów jdbc zostanie Set, sprawdzić zachowanie przy użyciu JPA
    private Set<Attachment> attachments;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public List<String> getAttachmentsFilename() {
        return attachments
                .stream()
                .map(Attachment::getFilename)
                .collect(toList());
    }

    public List<Attachment> getAttachments() {
        return new ArrayList<>(attachments);
    }

    public void addAttachment(String filename, String comment) {
        attachments.add(new Attachment(filename, comment));
    }

    @Override
    public int compareTo(Task o) {
        return this.getId().compareTo(o.getId());
    }
}
