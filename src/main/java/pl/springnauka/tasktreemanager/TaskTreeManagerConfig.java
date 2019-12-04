package pl.springnauka.tasktreemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TaskTreeManagerConfig {

    @Bean
    public Clock clock() {
        log.info("Registering new clock as Spring Bean");
        return new SystemClock();
    }
}
