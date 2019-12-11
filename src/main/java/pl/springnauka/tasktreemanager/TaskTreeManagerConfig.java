package pl.springnauka.tasktreemanager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.springnauka.tasktreemanager.tasks.boundary.FileSystemStorageService;
import pl.springnauka.tasktreemanager.tasks.boundary.StorageService;
import pl.springnauka.tasktreemanager.tasks.boundary.TasksRepository;

import java.nio.file.Path;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TaskTreeManagerConfig {

    private final StorageConfig storageConfig;

    @Bean
    public Clock clock() {
        log.info("Registering new clock as Spring Bean");
        return new SystemClock();
    }

    @Bean
    public StorageService storageService(TasksRepository tasksRepository) {
        return new FileSystemStorageService(Path.of(storageConfig.getLocalPath()), tasksRepository);
    }
}
