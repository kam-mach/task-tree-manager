package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import pl.springnauka.tasktreemanager.tags.entity.Tag;

@Data
@Table("tag_task")
@NoArgsConstructor
public class TagRef {
    Long tag;

    public TagRef(Tag tag) {
        this.tag = tag.getId();
    }
}
