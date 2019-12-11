package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import pl.springnauka.tasktreemanager.exceptions.NotFoundException;

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
    public void saveFile(Long id, MultipartFile file) throws IOException {
        Path targetPath = path.resolve(file.getName());
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        tasksRepository.addFiles(id, targetPath.toString());

    }

    @Override
    public Resource loadFile(Long id, String filename) throws MalformedURLException {

        if (tasksRepository.fetchById(id).getFiles().contains(path.resolve(filename).toString())) {
            return new UrlResource(path.resolve(filename).toUri());
        } else {
            throw new NotFoundException("Dla wybranego zadania, nie znaleziono takiego pliku");
        }
    }
}
