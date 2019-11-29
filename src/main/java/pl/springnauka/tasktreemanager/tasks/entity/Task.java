package pl.springnauka.tasktreemanager.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {

    private Long id;
    private String title;
    private String description;
}