package pl.springnauka.tasktreemanager.projects.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.Set;

@Data
@Table("project")
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    private Long id;
    private String name;
    private Set<Task> tasks;

    public Project(String projectName) {
        this.name = projectName;
    }
}
