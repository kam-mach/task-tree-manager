package pl.springnauka.tasktreemanager.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.springnauka.tasktreemanager.Clock;
import pl.springnauka.tasktreemanager.SystemClock;

@Slf4j
@Configuration
public class TaskTreeManagerConfig {

    @Bean
    public Clock clock() {
        log.info("Registering new clock as Spring Bean");
        return new SystemClock();
    }
}
