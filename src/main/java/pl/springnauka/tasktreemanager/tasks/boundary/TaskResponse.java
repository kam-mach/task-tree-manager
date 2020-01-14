package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.springnauka.tasktreemanager.tags.entity.Tag;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
class TaskResponse {
    long id;
    String title;
    String description;
    LocalDateTime createAt;
    List<AttachmentResponse> files;
    Set<TagResponse> tags;

    static TaskResponse from(Task task, Set<Tag> tags) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getAttachments()
                        .stream()
                        .map(AttachmentResponse::from)
                        .collect(toList()),
                tags.stream().map(TagResponse::new).collect(toSet()));
    }
}
