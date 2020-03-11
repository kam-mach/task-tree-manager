package pl.springnauka.tasktreemanager.tasks.boundary;

import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;

public interface TasksRepository {

    void add(Task task);

    List<Task> fetchAll();

    Task fetchById(Long id);

    void deleteById(Long id);

    void update(Long id, String title, String description);

    List<Task> findByTitle(String title);

    List<Task> findWithAttachments();

    void addAttachment(Long id, String path, String comment);

    void addAll(Iterable<Task> taskList);
}
