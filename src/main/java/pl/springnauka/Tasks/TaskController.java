package pl.springnauka.Tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/")
public class TaskController {


    private TasksRepository tasksRepository;

    public TaskController(TasksRepository tasksRepository) {
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
