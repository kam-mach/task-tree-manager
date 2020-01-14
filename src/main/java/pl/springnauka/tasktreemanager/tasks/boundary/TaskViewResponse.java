package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
class TaskViewResponse implements Comparable<TaskViewResponse> {
    Long id;
    String title;
    String description;
    LocalDateTime createdAt;


    static TaskViewResponse from(Task task) {
        return new TaskViewResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt());
    }

    @Override
    public int compareTo(TaskViewResponse o) {
        return this.getId().compareTo(o.getId());
    }
}
