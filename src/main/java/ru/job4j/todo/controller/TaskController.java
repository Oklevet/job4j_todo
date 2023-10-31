package ru.job4j.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", taskService.findAllDoneOrNew(false));
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", taskService.findAllDoneOrNew(true));
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        taskService.save(task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        System.out.println("getCreationPage");
        return "tasks/create";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, @RequestParam MultipartFile file, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/getDone/{id}")
    public String getDone(Model model, @ModelAttribute Task task) {
        try {
            System.out.println("start get done");
            boolean isUpdated = taskService.getDone(task, task.isDone());
            System.out.println("after upd");
            if (!isUpdated) {
                model.addAttribute("message", "Ошибка при обновлении задачи");
                return "errors/404";
            }
            System.out.println("redirect");
        }catch (Exception e) { e.printStackTrace();}
        return "redirect:/tasks/all";
    }
}
