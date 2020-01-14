package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pl.springnauka.tasktreemanager.tags.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Table("task")
public class Task implements Comparable<Task> {

    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    //pierwotnie lista, dla testów jdbc zostanie Set, sprawdzić zachowanie przy użyciu JPA
    private Set<Attachment> attachments;
    private Set<TagRef> tagRefs;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public List<Attachment> getAttachments() {
        return new ArrayList<>(attachments);
    }

    public void addAttachment(String filename, String comment) {
        attachments.add(new Attachment(filename, comment));
    }

    public void addTag(Tag tag) {
        tagRefs.add(new TagRef(tag));
    }

    public void removeTag(Tag tag) {
        tagRefs.remove(new TagRef(tag));
    }


    @Override
    public int compareTo(Task o) {
        return this.getId().compareTo(o.getId());
    }
}
