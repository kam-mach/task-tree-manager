package pl.springnauka.tasktreemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.springnauka.tasktreemanager.tasks.boundary.FileSystemStorageService;
import pl.springnauka.tasktreemanager.tasks.boundary.StorageService;

import java.nio.file.Path;

@Slf4j
@Configuration
public class TaskTreeManagerConfig {

    @Value("${app.tasks.localPath}")
    private String localPath;

    @Bean
    public Clock clock() {
        log.info("Registering new clock as Spring Bean");
        return new SystemClock();
    }

    @Bean
    public StorageService storageService() {
        return new FileSystemStorageService(Path.of(localPath));
    }
}
