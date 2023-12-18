package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.*;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.util.Collection;
import java.util.List;
@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final PriorityService priorityService;

    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAll(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAll(user));
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
    public String create(@ModelAttribute Task task, Model model, @SessionAttribute User user,
                         @RequestParam(name = "categoriesId") List<Integer> categoriesId) {
        try {
            task.setUser(user);
            taskService.save(task, categoriesId);
            return "redirect:/tasks/all";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAllPriors());
        model.addAttribute("categories", categoryService.findAll());
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
        model.addAttribute("priorities", priorityService.findAllPriors());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model, @SessionAttribute User user,
                         @RequestParam(name = "categoriesId") List<Integer> categoriesId) {
        task.setUser(user);
        var isUpdated = taskService.update(task, categoriesId);
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
