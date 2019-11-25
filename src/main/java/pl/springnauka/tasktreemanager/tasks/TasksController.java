package pl.springnauka.tasktreemanager.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/")
public class TasksController {


    private TasksRepository tasksRepository;

    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping()
    public List<Task> getTasks(){
        log.info("Zwracam listę zadań!");
        return tasksRepository.fetchAll();
    }

    @PostMapping()
    public void addTask(@RequestBody Task task){
        log.info("Dodaję zadanie {}", task);
        tasksRepository.add(task);
    }
}
