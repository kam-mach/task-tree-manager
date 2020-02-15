package pl.springnauka.tasktreemanager.tasks.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.springnauka.tasktreemanager.Clock;
import pl.springnauka.tasktreemanager.SystemClock;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TaskCrudRepositoryTest {

    public static final String TASK_NAME = "Kupic lodowke";
    Clock clock = new SystemClock();

    @Autowired
    TaskCrudRepository taskCrudRepository;

    @Test
    public void shouldLoadEntity() {
        //given
        Task task = new Task(TASK_NAME, "Pod zabudowe", clock.time());
        //when
        taskCrudRepository.save(task);
        List<Task> taskList = taskCrudRepository.findAll();
        //then
        assertEquals(taskList.size(), 1);
        assertEquals(taskList.get(0).getTitle(), TASK_NAME);
    }

}