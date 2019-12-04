package pl.springnauka.tasktreemanager.tasks.control;

import org.springframework.stereotype.Service;
import pl.springnauka.tasktreemanager.Clock;
import pl.springnauka.tasktreemanager.tasks.boundary.TasksRepository;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final Clock clock;

    private final AtomicLong nextTaskId = new AtomicLong(0);

    public TasksService(TasksRepository tasksRepository, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.clock = clock;
    }

    public void addTask(String title, String description) {
        tasksRepository.add(new Task(
                nextTaskId.getAndIncrement(),
                title,
                description,
                clock.time()
        ));
    }

    public void updateTask(Long id, String title, String description) {
        tasksRepository.update(id, title, description);
    }

    public List<Task> fetchAll() {
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query) {
        return tasksRepository.fetchAll()
                .stream().filter(task -> {
                            return task.getTitle().contains(query) || task.getDescription().contains(query);
                        }
                ).collect(Collectors.toList());
    }
}
