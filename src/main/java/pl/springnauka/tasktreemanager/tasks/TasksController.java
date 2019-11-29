package pl.springnauka.tasktreemanager.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
public class TasksController {


    private TasksRepository tasksRepository;

    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public List<Task> getTasks(){
        log.info("Zwracam listę zadań!");
        return tasksRepository.fetchAll();
    }

    @GetMapping(path = "/{id}")
    public Task getTaskById(@PathVariable Long id) {
        log.info("Zwracam zadanie {}", id);
        return tasksRepository.fetchById(id);
    }


    @PostMapping
    public void addTask(@RequestBody Task task){
        log.info("Dodaję zadanie {}", task);
        tasksRepository.add(task);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Usuwam zadanie {}", id);
        tasksRepository.deleteById(id);
    }

    @PutMapping
    public void updateTask() {
        log.info("Nadpisuję zadanie");
    }
}