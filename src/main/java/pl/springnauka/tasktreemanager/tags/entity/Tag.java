package pl.springnauka.tasktreemanager.tags.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tag")
@AllArgsConstructor
public class Tag {
    @Id
    private Long id;
    private String name;
}
