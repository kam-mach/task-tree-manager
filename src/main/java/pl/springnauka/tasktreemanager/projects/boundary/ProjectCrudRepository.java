package pl.springnauka.tasktreemanager.projects.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.springnauka.tasktreemanager.projects.entity.Project;

public interface ProjectCrudRepository extends JpaRepository<Project, Long> {

    @Modifying
    @Query("UPDATE Task SET project = :idProject WHERE id = :idTask")
    void assignTaskToProject(@Param("idTask") Long idTask, @Param("idProject") Long idProject);

    @Modifying
    @Query("UPDATE Task SET project = null WHERE id = :idTask")
    void removeTaskFromProject(@Param("idTask") Long idTask);

}
