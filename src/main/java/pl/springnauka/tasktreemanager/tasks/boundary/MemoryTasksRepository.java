package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.stereotype.Repository;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.*;

import static java.util.stream.Collectors.toList;

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
                .orElseThrow(() -> new NotFoundException("Zadanie nie znalezione"));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).map(taskSet::remove)
                .orElseThrow(() -> new NotFoundException("Zadanie nie znalezione"));

    }

    @Override
    public void update(Long id, String title, String description) {
        Task task = findById(id).orElseThrow(() -> new NotFoundException("Task not found with id = " + id));
        task.setTitle(title);
        task.setDescription(description);

    }

    @Override
    public void addAttachment(Long id, String path, String comment) {
        Task task = findById(id).orElseThrow(() -> new NotFoundException("Zadanie nie znalezione"));
        task.addAttachment(path, comment);
    }


    private Optional<Task> findById(Long id) {
        return taskSet
                .stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst();
    }

    @Override
    public List<Task> findByTitle(String title) {
        return taskSet.stream().filter(task -> task.getTitle().equals(title)).collect(toList());
    }

    @Override
    public List<Task> findWithAttachments() {
        return taskSet.stream().filter(task -> !task.getAttachments().isEmpty()).collect(toList());
    }
}
