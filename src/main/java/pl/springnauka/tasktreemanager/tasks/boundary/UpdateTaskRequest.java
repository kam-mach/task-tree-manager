package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.Data;

@Data
class UpdateTaskRequest {

    String title;
    String description;
}
