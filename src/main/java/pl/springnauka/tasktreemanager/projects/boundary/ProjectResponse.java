package pl.springnauka.tasktreemanager.projects.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.springnauka.tasktreemanager.projects.entity.Project;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProjectResponse {

    long id;
    String name;
    Set<Task> tasks;

    static ProjectResponse from(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getTasks());
    }

}
