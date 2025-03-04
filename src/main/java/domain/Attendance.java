package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Set;

public class Attendance implements Comparable<Attendance> {
    public static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);
    public static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;
    private static final LocalTime OPERATING_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATING_END_TIME = LocalTime.of(23, 0);
    public static final LocalTime ABSENT_DEFAULT_TIME = LocalTime.of(23, 59);


    private static final Set<DayOfWeek> WEEKENDS = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final Set<MonthDay> HOLIDAYS = Set.of(MonthDay.of(12, 25));

    private final LocalDateTime attendanceTime;
    private final AttendanceResult attendanceResult;

    public Attendance(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceResult = checkAttendanceStatus(attendanceTime);
    }


    public AttendanceResult checkAttendanceStatus(LocalDateTime attendanceTime) {
        LocalTime startTime = DEFAULT_START_TIME; // 기본 시작 시간
        if (attendanceTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = MONDAY_START_TIME; // 월요일 시작 시간 적용
        }
        if (LocalTime.from(attendanceTime).isBefore(startTime.plusMinutes(LATE_STANDARD))) {
            return AttendanceResult.ATTENDANCE;
        }
        if (LocalTime.from(attendanceTime).isBefore(startTime.plusMinutes(ABSENT_STANDARD))) {
            return AttendanceResult.LATE;
        }
        return AttendanceResult.ABSENCE;
    }

    public static boolean isOperatingTime(LocalTime attendanceTime) {
        return !attendanceTime.isBefore(OPERATING_START_TIME) && !attendanceTime.isAfter(OPERATING_END_TIME);
    }

    public static boolean isOperatingDay(LocalDateTime attendanceTime) {
        if (WEEKENDS.contains(DayOfWeek.from(attendanceTime)) || HOLIDAYS.contains(MonthDay.from(attendanceTime))) {
            return false;
        }
        return true;
    }


    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceResult getAttendanceResult() {
        return attendanceResult;
    }

    public boolean isBeforeHistory(LocalDateTime attendanceTIme) {
        LocalDateTime standardTime = LocalDateTime.of(attendanceTIme.getYear(), attendanceTIme.getMonthValue(),
                attendanceTIme.getDayOfMonth(), 0, 0);
        return attendanceTIme.isBefore(standardTime);
    }

    @Override
    public int compareTo(Attendance o) {
        return this.attendanceTime.compareTo(o.attendanceTime);
    }

}
