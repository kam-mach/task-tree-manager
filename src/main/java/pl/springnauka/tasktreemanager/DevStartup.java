package pl.springnauka.tasktreemanager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.springnauka.tasktreemanager.projects.boundary.ProjectCrudRepository;
import pl.springnauka.tasktreemanager.projects.entity.Project;
import pl.springnauka.tasktreemanager.tags.boundary.TagsCrudRepository;
import pl.springnauka.tasktreemanager.tags.entity.Tag;
import pl.springnauka.tasktreemanager.tasks.boundary.TasksRepository;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Component
@AllArgsConstructor
@Slf4j
public class DevStartup {

    private final TasksRepository tasksRepository;
    private final TagsCrudRepository tagsCrudRepository;
    private final ProjectCrudRepository projectCrudRepository;
    private final Clock clock;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeApplication() {
        log.info("Initialize DEV data");
        insertTasks();
        insertTags();
        insertProjects();
        log.info("Finished initializing DEV data");
    }

    private void insertProjects() {
        List<Project> projectList = Arrays.asList(
                new Project("Projekt produkcyjny"),
                new Project("Projekt testowy")
        );

        projectCrudRepository.saveAll(projectList);
    }

    private void insertTags() {
        List<Tag> tagList = Arrays.asList(
                new Tag("Pilne"),
                new Tag("W domu"),
                new Tag("Na mieście")
        );

        tagsCrudRepository.saveAll(tagList);
    }

    private void insertTasks() {
        List<Task> taskList = Arrays.asList(
                new Task("Zadanie 1", "Wykonać zadanie z modułu 1", clock.time()),
                new Task("Zadanie 2", "Wykonać zadanie z modułu 2", clock.time()),
                new Task("Zadanie 3", "Wykonać zadanie z modułu 3", clock.time()),
                new Task("Zadanie 4", "Wykonać zadanie z modułu 4", clock.time()),
                new Task("Zadanie 5", "Wykonać zadanie z modułu 5", clock.time()));

        tasksRepository.addAll(taskList);
    }
}
