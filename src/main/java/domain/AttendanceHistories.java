package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceHistories {

    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceHistories(Map<String, List<LocalDateTime>> originalHistories, LocalDate standard) {
        this.attendanceHistories = new ArrayList<>();
        for (Map.Entry<String, List<LocalDateTime>> entry : originalHistories.entrySet()) {
            String name = entry.getKey();
            List<LocalDateTime> attendanceTimes = new ArrayList<>(entry.getValue());

            List<LocalDateTime> absenceTimes = generateAbsenceHistories(attendanceTimes, standard);
            attendanceTimes.addAll(absenceTimes);
            List<Attendance> attendances = attendanceTimes.stream()
                    .map(Attendance::new)
                    .collect(Collectors.toList());
            attendanceHistories.add(new AttendanceHistory(name, attendances));
        }
    }

    public AttendanceHistories(List<AttendanceHistory> attendanceHistories) {
        this.attendanceHistories = attendanceHistories;
    }

    private List<LocalDateTime> generateAbsenceHistories(List<LocalDateTime> attendanceTimes, LocalDate standard) {
        return getAbsentDays(attendanceTimes, standard).stream()
                .filter(date -> Attendance.isOperatingDay(date.atStartOfDay()))
                .map(date -> date.atTime(Attendance.ABSENT_DEFAULT_TIME))
                .collect(Collectors.toList());
    }

    private List<LocalDate> getAbsentDays(List<LocalDateTime> attendanceTimes, LocalDate standard) {
        Set<LocalDate> attendedDays = attendanceTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());

        return IntStream.range(1, standard.getDayOfMonth())
                .mapToObj(i -> standard.withDayOfMonth(i))
                .filter(date -> !attendedDays.contains(date))
                .collect(Collectors.toList());
    }

    public List<AttendanceHistory> getAttendanceHistories() {
        return attendanceHistories;
    }

    public AttendanceHistory findByName(String name) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 이름을 가진 출석 이력이 없습니다."));
    }

    public void addAttendanceHistory(String name, Attendance attendance) {
        findByName(name).addAttendance(attendance);
    }
}