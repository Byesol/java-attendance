package domain;

public enum AttendanceResult {
    ABSENCE("결석"),
    LATE("지각"),
    ATTENDANCE("출석");

    private final String result;

    AttendanceResult(String result) {
        this.result = result;
    }

//    public static AttendanceResult findAttendanceResult(LocalDateTime localDateTime) {
//        LocalTime lateTime = AttendanceTimePolicy.getLateTime(localDateTime.getDayOfWeek());
//        LocalTime absenceTime = AttendanceTimePolicy.getAbsenceTime(localDateTime.getDayOfWeek());
//        LocalTime currentTime = localDateTime.toLocalTime();
//
//        if (currentTime.isAfter(absenceTime)) {
//            return ABSENCE;
//        }
//        if (currentTime.isAfter(lateTime)) {
//            return LATE;
//        }
//        return ATTENDANCE;
//    }

    public String getResult() {
        return result;
    }
}
