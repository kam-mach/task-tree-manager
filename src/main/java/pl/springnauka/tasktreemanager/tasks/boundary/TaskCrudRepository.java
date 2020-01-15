package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;

public interface TaskCrudRepository extends CrudRepository<Task, Long> {

    @Modifying
    @Query("UPDATE Task SET title = :title, description = :description WHERE id = :id")
    void updateTitleDescription(@Param("id") Long id, @Param("title") String title, @Param("description") String description);

    @Query("SELECT * FROM Task t WHERE upper(t.title) LIKE '%' || upper(:title) || '%'")
    List<Task> findByTitle(@Param("title") String title);

    @Query("SELECT * FROM Task t JOIN Attachment a ON t.id = a.task")
    List<Task> findWithAttachments();
}
