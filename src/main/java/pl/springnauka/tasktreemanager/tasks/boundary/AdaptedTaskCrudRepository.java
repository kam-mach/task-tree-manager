package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Primary
@Repository
@AllArgsConstructor
public class AdaptedTaskCrudRepository implements TasksRepository {
    private final TaskCrudRepository taskCrudRepository;


    @Override
    public void add(Task task) {
        taskCrudRepository.save(task);
    }

    @Override
    public List<Task> fetchAll() {
        return StreamSupport
                .stream(taskCrudRepository.findAll().spliterator(), false)
                .collect(toList());

    }

    @Override
    public Task fetchById(Long id) {
        return taskCrudRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nie można odnaleźć zadania o numerze " + id));
    }

    @Override
    public void deleteById(Long id) {
        taskCrudRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String title, String description) {
        taskCrudRepository.findById(id).map(task -> {
            task.setTitle(title);
            task.setDescription(description);
            return task;
        }).ifPresent(taskCrudRepository::save);

    }

    @Override
    public void addFiles(Long id, String path) {

    }
}
