package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.data.repository.CrudRepository;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

public interface TaskCrudRepository extends CrudRepository<Task, Long> {
}
