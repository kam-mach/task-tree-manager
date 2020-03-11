package pl.springnauka.tasktreemanager.tags.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.tags.boundary.TagsCrudRepository;
import pl.springnauka.tasktreemanager.tags.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final TagsCrudRepository tagsRepository;

    public Tag findById(Long id) {
        return tagsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brak tagu o id" + id));
    }

    public Set<Tag> getAllById(List<Long> tagsId) {
        return StreamSupport
                .stream(tagsRepository.findAllById(tagsId).spliterator(), false)
                .collect(toSet());
    }
}
