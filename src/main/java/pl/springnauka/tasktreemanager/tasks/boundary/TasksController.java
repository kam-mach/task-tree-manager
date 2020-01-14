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
import pl.springnauka.tasktreemanager.tags.control.TagsService;
import pl.springnauka.tasktreemanager.tags.entity.Tag;
import pl.springnauka.tasktreemanager.tasks.control.TasksService;
import pl.springnauka.tasktreemanager.tasks.entity.TagRef;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
//@CrossOrigin
public class TasksController {

    private final StorageService storageService;
    private final TasksService tasksService;
    private final TagsService tagsService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@RequestParam Optional<String> query) {
        log.info("Zwracam listę zadań! z query {}", query);
        return ResponseEntity.ok(query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(toList()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        log.info("Zwracam zadanie {}", id);
        try {
            TaskResponse taskResponse = toTaskResponse(tasksService.fetchById(id));
            return ResponseEntity.ok(taskResponse);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity<?> getAttachment(@PathVariable Long id, @PathVariable String filename, HttpServletRequest
            request) {
        log.info("Zwracam załącznik o nazwie {} dla zadania {}", filename, id);
        try {
            Resource resource = storageService.loadFile(id, filename);
            String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (Objects.isNull(mimeType)) {
                mimeType = "application/octet-stream";
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(resource);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity<String> addAttachment(
            @PathVariable Long id,
            @RequestParam("comment") String comment,
            @RequestParam("file") MultipartFile filename) {
        log.info("Dodaję załącznik o nazwie {} dla zadania {}", filename.getName(), id);
        try {
            storageService.saveFile(id, filename, comment);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping
    public ResponseEntity addTask(@RequestBody CreateTaskRequest task) {
        log.info("Dodaję zadanie {}", task);
        tasksService.addTask(task.title, task.description);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/{id}/tags")
    public ResponseEntity addTag(@PathVariable Long id, @RequestBody AddTagRequest request) {
        log.info("Dodaję tag {}", id);
        try {
            tasksService.addTag(id, request.getTagId());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(path = "/{id}/tags/{tagId}")
    public ResponseEntity removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        log.info("Usuwam tag {}", id);
        try {
            tasksService.removeTag(id, tagId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        log.info("Usuwam zadanie {}", id);
        try {
            tasksService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest taskRequest) {
        log.info("Nadpisuję zadanie {}", id);
        try {
            tasksService.updateTask(id, taskRequest.getTitle(), taskRequest.getDescription());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private TaskResponse toTaskResponse(Task task) {
        List<Long> tagsId = task.getTagRefs().stream().map(TagRef::getTag).collect(toList());
        Set<Tag> tagSet = tagsService.getAllById(tagsId);
        return TaskResponse.from(task, tagSet);
    }

}