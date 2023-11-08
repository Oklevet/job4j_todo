package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/all")
    public String getAll(Model model, @SessionAttribute User user) {
        System.out.println("redirected to /all");
        model.addAttribute("tasks", taskService.findAll(user));
        System.out.println("made list /all");
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAllDoneOrNew(user, false));
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAllDoneOrNew(user, true));
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model, @SessionAttribute User user) {
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
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
    public String update(@ModelAttribute Task task, Model model, @SessionAttribute User user) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/getDone/{id}")
    public String getDone(Model model, @ModelAttribute Task task) {
        boolean isUpdated = taskService.getDone(task);
        if (!isUpdated) {
            model.addAttribute("message", "Ошибка при обновлении задачи");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }
}
