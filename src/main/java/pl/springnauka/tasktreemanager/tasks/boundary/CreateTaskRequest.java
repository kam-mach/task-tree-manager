package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.Data;

@Data
class CreateTaskRequest {

    String title;
    String description;
}
