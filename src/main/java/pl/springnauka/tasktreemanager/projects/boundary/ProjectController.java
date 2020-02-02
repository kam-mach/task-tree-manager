package pl.springnauka.tasktreemanager.projects.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.projects.control.ProjectService;
import pl.springnauka.tasktreemanager.projects.entity.Project;

@Slf4j
@RestController
@RequestMapping(path = "/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity getProjectById(@PathVariable Long id) {
        log.info("Zwracam projekt {}", id);
        try {
            Project project = projectService.findById(id);
            return ResponseEntity.ok(ProjectResponse.from(project));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping
    public ResponseEntity addProject(@RequestBody CreateProjectRequest projectRequest) {
        log.info("Dodaję projekt {}", projectRequest);
        projectService.addProject(projectRequest.name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateProject(@PathVariable Long id, @RequestBody String updatedName) {
        log.info("Nadpisuję projekt {}", id);
        try {
            Project project = projectService.findById(id);
            projectService.updateProject(project, updatedName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        log.info("Usuwam project {}", id);
        try {
            Project project = projectService.findById(id);
            projectService.deleteProject(project);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping(path = "/{idProject}/task/{idTask}")
    public ResponseEntity addToProject(@PathVariable Long idProject, @PathVariable Long idTask) {
        log.info("Dodaję task {} do projektu {}", idTask, idProject);
        projectService.assignTask(idProject, idTask);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/task/{idTask}")
    public ResponseEntity removeFromProject(@PathVariable Long idTask) {
        log.info("Usuwam task {} z przypisanego projektu ", idTask);

        projectService.removeTask(idTask);
        return ResponseEntity.ok().build();

    }
}
