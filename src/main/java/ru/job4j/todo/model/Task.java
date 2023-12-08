package ru.job4j.todo.model;

import lombok.*;
import ru.job4j.todo.service.PriorityService;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
public class Task {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @EqualsAndHashCode.Include
    private String title;

    private String description;

    private LocalDateTime created = LocalDateTime.now();

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    public String getPriorName(Task task) {
        if (task.priority == null) {
            return "normal";
        }
        return task.priority.getName();
    }

    public void setPriorPos(Task task, String name) {
        PriorityService priorityService = null;
        task.setPriority(priorityService.findByName(name).get());
    }
}