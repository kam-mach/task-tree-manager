package pl.springnauka.tasktreemanager.tasks.control;

import org.springframework.stereotype.Service;
import pl.springnauka.tasktreemanager.Clock;
import pl.springnauka.tasktreemanager.tags.control.TagsService;
import pl.springnauka.tasktreemanager.tags.entity.Tag;
import pl.springnauka.tasktreemanager.tasks.boundary.TasksRepository;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final TagsService tagsService;
    private final Clock clock;

    public TasksService(TasksRepository tasksRepository, TagsService tagsService, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.clock = clock;
        this.tagsService = tagsService;
    }

    public Task addTask(String title, String description) {
        Task task = new Task(
                title,
                description,
                clock.time()
        );
        tasksRepository.add(task);
        return task;
    }

    public void updateTask(Long id, String title, String description) {
        tasksRepository.update(id, title, description);
    }

    public List<Task> fetchAll() {
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query) {
        return tasksRepository.fetchAll()
                .stream().filter(task -> task.getTitle().contains(query) || task.getDescription().contains(query)
                ).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        tasksRepository.deleteById(id);
    }

    public Task fetchById(Long id) {
        return tasksRepository.fetchById(id);
    }

    public void addTag(Long id, Long tagId) {
        Task task = tasksRepository.fetchById(id);
        Tag tag = tagsService.findById(tagId);
        task.addTag(tag);
        tasksRepository.add(task);
    }

    public void removeTag(Long id, Long tagId) {
        Task task = tasksRepository.fetchById(id);
        Tag tag = tagsService.findById(tagId);
        task.removeTag(tag);
        tasksRepository.add(task);
    }
}
