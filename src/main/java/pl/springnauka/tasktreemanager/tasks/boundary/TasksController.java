package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.tasks.control.TasksService;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final StorageService storageService;
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;

    @PostConstruct
    void init() {
        tasksService.addTask("Zadanie 1", "Wykonać zadanie z modułu 1");
        tasksService.addTask("Zadanie 2", "Wykonać zadanie z modułu 2");
        tasksService.addTask("Zadanie 3", "Wykonać zadanie z modułu 3");
        tasksService.addTask("Zadanie 4", "Wykonać zadanie z modułu 4");
        tasksService.addTask("Zadanie 5", "Wykonać zadanie z modułu 5");
    }

    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam Optional<String> query) {
        log.info("Zwracam listę zadań! z query {}", query);
        return query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("Zwracam zadanie {}", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request) throws IOException {
        log.info("Zwracam załącznik o nazwie {} dla zadania {}", filename, id);
        Resource resource = storageService.loadFile(filename);
        String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (Objects.isNull(mimeType)) {
            mimeType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(resource);
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile filename) throws IOException {
        log.info("Dodaję załącznik o nazwie {} dla zadania {}", filename.getName(), id);
        storageService.saveFile(id, filename);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest taskRequest) {
        log.info("Nadpisuję zadanie {}", id);
        try {
            tasksService.updateTask(id, taskRequest.getTitle(), taskRequest.getDescription());
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedAt());
    }
}