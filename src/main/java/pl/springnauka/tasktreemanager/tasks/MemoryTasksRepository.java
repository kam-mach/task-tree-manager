package pl.springnauka.tasktreemanager.tasks;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return taskSet
                .stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Zadanie nie znalezione"));
    }

    @Override
    public void deleteById(Long id) {
        taskSet
                .stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst()
                .ifPresent(taskSet::remove);

    }

}
