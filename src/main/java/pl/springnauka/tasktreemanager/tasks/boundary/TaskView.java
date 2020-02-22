package pl.springnauka.tasktreemanager.tasks.boundary;

import java.time.LocalDateTime;

public interface TaskView {

    Long getId();

    String getTitle();

    String getDescription();

    LocalDateTime getCreatedAt();
}
