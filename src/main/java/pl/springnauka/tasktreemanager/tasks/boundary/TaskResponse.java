package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskResponse {
    long id;
    String title;
    String description;
    LocalDateTime createAt;
}
