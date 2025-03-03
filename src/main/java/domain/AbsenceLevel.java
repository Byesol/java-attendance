package domain;

public enum AbsenceLevel {
    OUT("제적", 5),
    MEETING("면담", 3),
    WARNING("경고", 2),
    NORMAL("정상", 0);

    public static final int LATE_TO_ABSENCE_THRESHOLD = 3;

    private final String level;
    private final int standard;

    AbsenceLevel(String level, int standard) {
        this.level = level;
        this.standard = standard;
    }

    public static AbsenceLevel findAbsenceLevel(int absentCount, int lateCount) {
        absentCount += (lateCount / LATE_TO_ABSENCE_THRESHOLD);
        if (absentCount > OUT.standard) {
            return OUT;
        }
        if (absentCount >= MEETING.standard) {
            return MEETING;
        }
        if (absentCount >= WARNING.standard) {
            return WARNING;
        }
        return NORMAL;
    }

    public String getLevel() {
        return level;
    }
}
