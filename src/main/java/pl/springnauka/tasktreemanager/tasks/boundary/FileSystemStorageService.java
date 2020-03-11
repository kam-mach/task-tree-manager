package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;
import pl.springnauka.tasktreemanager.tasks.entity.Attachment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileSystemStorageService implements StorageService {

    private final Path path;
    private final TasksRepository tasksRepository;

    public FileSystemStorageService(Path path, TasksRepository tasksRepository) {
        this.path = path;
        this.tasksRepository = tasksRepository;
    }

    @Override
    public void saveFile(Long id, MultipartFile file, String comment) throws IOException {
        String filename = file.getOriginalFilename();
        Path targetPath = path.resolve(filename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        tasksRepository.addAttachment(id, filename, comment);
    }

    @Override
    public Resource loadFile(Long id, String filename) throws MalformedURLException {

        if (tasksRepository.fetchById(id).getAttachments().stream().map(Attachment::getFilename).anyMatch(it -> it.equals(filename))) {
            return new UrlResource(path.resolve(filename).toUri());
        } else {
            throw new NotFoundException("Dla wybranego zadania, nie znaleziono takiego pliku");
        }
    }
}
