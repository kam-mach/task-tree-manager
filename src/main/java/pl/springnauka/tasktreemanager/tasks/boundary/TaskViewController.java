package pl.springnauka.tasktreemanager.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.springnauka.tasktreemanager.tasks.control.TasksService;
import pl.springnauka.tasktreemanager.tasks.entity.Task;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TaskViewController {

    private final StorageService storageService;
    private final TasksService tasksService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(
            @ModelAttribute("newTask") CreateTaskRequest request,
            @RequestParam("attachment") MultipartFile attachment) throws IOException {
        Task task = tasksService.addTask(request.title, request.description);
        if (!attachment.isEmpty()) {
            storageService.saveFile(task.getId(), attachment);
        }
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasksService.deleteById(id);
        return "redirect:/";
    }
}
