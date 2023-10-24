package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", taskService.findAllNew());
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", taskService.findAllDone());
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            System.out.println("before save");
            taskService.save(task);
            System.out.println("after save");
            return "redirect:/tasks/all";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
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
        try {
            var isUpdated = taskService.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "Задание с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
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
}
