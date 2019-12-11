package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.stereotype.Repository;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.*;

@Repository
public class MemoryTasksRepository implements TasksRepository {

    private final Set<Task> taskSet = new HashSet<>();

    @Override
    public void add(Task task) {
        taskSet.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return new ArrayList<>(taskSet);
    }

    @Override
    public Task fetchById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zadanie nie znalezione"));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).map(taskSet::remove)
                .orElseThrow(() -> new IllegalArgumentException("Zadanie nie znalezione"));

    }

    @Override
    public void update(Long id, String title, String description) {
        Task task = findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found with id = " + id));
        task.setTitle(title);
        task.setDescription(description);

    }

    @Override
    public void addFiles(Long id, String path) {
        Task task = findById(id).orElseThrow(() -> new IllegalArgumentException("Zadanie nie znalezione"));
        List<String> files = task.getFiles();
        files.add(path);
        task.setFiles(files);
    }

    private Optional<Task> findById(Long id) {
        return taskSet
                .stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst();
    }

}
