package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
class TaskResponse {
    long id;
    String title;
    String description;
    LocalDateTime createAt;
    List<AttachmentResponse> files;

    static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getAttachments()
                        .stream()
                        .map(AttachmentResponse::from)
                        .collect(Collectors.toList()));
    }
}
