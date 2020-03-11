package pl.springnauka.tasktreemanager.tags.boundary;

import org.springframework.data.repository.CrudRepository;
import pl.springnauka.tasktreemanager.tags.entity.Tag;

public interface TagsCrudRepository extends CrudRepository<Tag, Long> {
}
