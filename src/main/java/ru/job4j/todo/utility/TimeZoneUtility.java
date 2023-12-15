package ru.job4j.todo.utility;

import lombok.NoArgsConstructor;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.*;

public class TimeZoneUtility {

    public static Map<String, String> findAllTimeZones() {
        Map<String, String> zones = new TreeMap<>();
        String sTimeGMT;
        for (String timeId : TimeZone.getAvailableIDs()) {
            var timeGMT = TimeZone.getTimeZone(timeId).getRawOffset() / (60 * 60 * 1000D);
            if (timeGMT >= 0) {
                sTimeGMT = ",  +" + timeGMT;
            } else {
                sTimeGMT = ",  " + timeGMT;
            }
            zones.put(TimeZone.getTimeZone(timeId).getDisplayName() + sTimeGMT, timeId);
        }
        return zones;
    }

    public static Collection<Task> changeToUsersTimeZone(Collection<Task> tasks, User user) {
        tasks.forEach(task -> {
            task.setCreated(task.getCreated()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of(user.getTimezone())).toLocalDateTime());
        });
        return tasks;
    }
}