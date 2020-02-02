package pl.springnauka.tasktreemanager.projects.boundary;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.springnauka.tasktreemanager.projects.entity.Project;

public interface ProjectCrudRepository extends CrudRepository<Project, Long> {

    @Modifying
    @Query("UPDATE Task SET project = :idProject WHERE id = :idTask")
    void assignTaskToProject(@Param("idTask") Long idTask, @Param("idProject") Long idProject);

    @Modifying
    @Query("UPDATE Task SET project = null WHERE id = :idTask")
    void removeTaskFromProject(@Param("idTask") Long idTask);

}
