package pl.springnauka.tasktreemanager.tasks.control;

import org.springframework.stereotype.Service;
import pl.springnauka.tasktreemanager.tasks.boundary.TasksRepository;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;

    private final AtomicLong nextTaskId = new AtomicLong(0);

    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public void addTask(String title, String description) {
        tasksRepository.add(new Task(
                nextTaskId.getAndIncrement(),
                title,
                description
        ));
    }
}
