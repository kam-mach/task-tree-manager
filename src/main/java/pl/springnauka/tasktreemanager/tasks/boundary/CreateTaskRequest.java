package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.Data;

@Data
public class CreateTaskRequest {

    String title;
    String description;
}
