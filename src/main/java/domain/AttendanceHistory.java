package domain;

import static domain.AttendanceResult.LATE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistory {

    private final String name;
    private final List<Attendance> attendances;

    public AttendanceHistory(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = new ArrayList<>(attendances);
    }

    public AbsenceLevel getClassifyAbsenceLevel(LocalDateTime standard) {
        Map<AttendanceResult, Integer> results = getAttendanceResultCount(standard);
        int absentCount = results.getOrDefault(AttendanceResult.ABSENCE, 0);
        int lateCount = results.getOrDefault(LATE, 0);
        return AbsenceLevel.findAbsenceLevel(absentCount, lateCount);

    }


    public Map<AttendanceResult, Integer> getAttendanceResultCount(LocalDateTime standard) {
        return attendances.stream()
                .filter(history -> history.isBeforeHistory(standard))
                .reduce(new HashMap<>(), (map, history) -> {
                    map.merge(history.getAttendanceResult(), 1, Integer::sum);
                    return map;
                }, (map1, map2) -> {
                    map2.forEach((key, value) -> map1.merge(key, value, Integer::sum));
                    return map1;
                });
    }


    public String getName() {
        return name;
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }
}
