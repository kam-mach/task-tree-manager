package pl.springnauka.tasktreemanager.projects.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.projects.boundary.ProjectCrudRepository;
import pl.springnauka.tasktreemanager.projects.entity.Project;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectCrudRepository projectRepository;


    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Nie znaleziono projektu o id {}" + id));
    }

    ;

    public void addProject(String projectName) {
        Project project = new Project(projectName);
        projectRepository.save(project);
    }

    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    public void updateProject(Project project, String name) {
        project.setName(name);
        projectRepository.save(project);
    }


    public void assignTask(Long project, Long task) {
        projectRepository.assignTaskToProject(task, project);
    }

    public void removeTask(Long taskId) {
        projectRepository.removeTaskFromProject(taskId);
    }

}
