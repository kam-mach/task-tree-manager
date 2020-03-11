package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.ArrayList;
import java.util.List;

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
    public void addAll(Iterable<Task> taskList) {
        taskCrudRepository.saveAll(taskList);
    }

    @Override
    public List<Task> fetchAll() {
        return new ArrayList<>(taskCrudRepository.findAll());

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
        taskCrudRepository.updateTitleDescription(id, title, description);
    }

    @Override
    public List<Task> findByTitle(String title) {
        return taskCrudRepository.findByTitleLike(title);
    }

    @Override
    public List<Task> findWithAttachments() {
        return new ArrayList<>();
//        return taskCrudRepository.findWithAttachments();
    }

    @Override
    public void addAttachment(Long id, String path, String comment) {
        Task task = fetchById(id);
        task.addAttachment(path, comment);
        taskCrudRepository.save(task);
    }

}
