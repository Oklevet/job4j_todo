package ru.job4j.todo.utility;

import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
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
        zones.put("--Не выбрано", "--Не выбрано");
        return zones;
    }
}