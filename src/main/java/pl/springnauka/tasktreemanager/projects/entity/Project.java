package pl.springnauka.tasktreemanager.projects.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //TODO
    private transient Set<Task> tasks = new HashSet<>(0);

    public Project(String projectName) {
        this.name = projectName;
    }


}
