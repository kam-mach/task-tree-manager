package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("attachment")
@AllArgsConstructor
public class Attachment {
    private String filename;
    private String comment;
}
