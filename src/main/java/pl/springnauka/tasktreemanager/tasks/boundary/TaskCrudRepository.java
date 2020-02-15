package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;

public interface TaskCrudRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("UPDATE Task SET title = :title, description = :description WHERE id = :id")
    void updateTitleDescription(@Param("id") Long id, @Param("title") String title, @Param("description") String description);

    @Query
    List<Task> findByTitleLike(@Param("title") String title);
}
