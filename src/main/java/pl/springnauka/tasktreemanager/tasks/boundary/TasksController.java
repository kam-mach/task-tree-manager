package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.springnauka.tasktreemanager.tasks.control.TasksService;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
public class TasksController {


    private TasksRepository tasksRepository;
    private TasksService tasksService;

    public TasksController(TasksRepository tasksRepository, TasksService tasksService) {
        this.tasksRepository = tasksRepository;
        this.tasksService = tasksService;
    }

    @PostConstruct
    void init() {
        tasksService.addTask("Zadanie 1", "Wykonać zadanie z modułu 1");
        tasksService.addTask("Zadanie 2", "Wykonać zadanie z modułu 2");
        tasksService.addTask("Zadanie 3", "Wykonać zadanie z modułu 3");
        tasksService.addTask("Zadanie 4", "Wykonać zadanie z modułu 4");
        tasksService.addTask("Zadanie 5", "Wykonać zadanie z modułu 5");
    }

    @GetMapping
    public List<TaskResponse> getTasks() {
        log.info("Zwracam listę zadań!");
        return tasksRepository.fetchAll()
                .stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("Zwracam zadanie {}", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }


    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        log.info("Dodaję zadanie {}", task);
        tasksService.addTask(task.title, task.description);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Usuwam zadanie {}", id);
        tasksRepository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest taskRequest) {
        log.info("Nadpisuję zadanie {}", id);
        tasksService.updateTask(id, taskRequest.getTitle(), taskRequest.getDescription());
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt());
    }
}