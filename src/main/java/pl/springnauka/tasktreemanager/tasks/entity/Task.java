package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.springnauka.tasktreemanager.tags.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "task")
@NoArgsConstructor
@Entity
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task")
    private Set<Attachment> attachments = new HashSet<>(0);

    @ManyToMany
    @JoinTable(name = "tag_task",
            joinColumns = @JoinColumn(name = "task"),
            inverseJoinColumns = @JoinColumn(name = "tag"))
    private Set<Tag> tags = new HashSet<>(0);

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
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }


    @Override
    public int compareTo(Task o) {
        return this.getId().compareTo(o.getId());
    }
}
