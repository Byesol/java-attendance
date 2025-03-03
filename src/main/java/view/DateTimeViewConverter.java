package view;

import static domain.Attendance.ABSENT_DEFAULT_TIME;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeViewConverter {

    public static final int STANDARD_YEAR = 2024;
    public static final int STANDARD_MONTH = 12;

    private static final String DELIMITER = ":";
    private static final int HOUR_INDEX = 0;
    private static final int MINUTES_INDEX = 1;
    private static final String TIME_FORMAT = "^(?:[01]\\d|2[0-3])\\s*:\\s*[0-5]\\d$";
    private static final DateTimeFormatter NORMAL_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
    private static final DateTimeFormatter DATE_ONLY_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);

    public static LocalDateTime changeToDate(String input) {
        validateDate(input.trim());
        String[] words = input.split(DELIMITER);
        int hour = Integer.parseInt(words[HOUR_INDEX].trim());
        int minutes = Integer.parseInt(words[MINUTES_INDEX].trim());

        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, 18, hour, minutes);
    }

    public static String dateFormattingForOutput(LocalDateTime attendanceTime) {
        if (LocalTime.from(attendanceTime).equals(ABSENT_DEFAULT_TIME)) {
            return attendanceTime.format(DATE_ONLY_FORMATTER) + " --:--";
        }
        return attendanceTime.format(NORMAL_FORMATTER);
    }

    public static String dateFormattingForInput(LocalDateTime attendanceTime) {
        LocalDateTime changedDate = changeStandardDate(attendanceTime);
        return changedDate.format(DATE_ONLY_FORMATTER);
    }

    public static LocalDateTime changeStandardDate(LocalDateTime attendanceTime) {
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, attendanceTime.getDayOfMonth(), attendanceTime.getHour(),
                attendanceTime.getMinute());
    }

    public static String timeFormattingForOutput(LocalDateTime attendanceTime) {
        return attendanceTime.format(TIME_FORMATTER);
    }

    public static LocalDateTime editDayOfMonth(String attendanceDate, String attendanceTime) {
        validateDate(attendanceTime.trim());
        validateNumber(attendanceDate.trim());
        String[] words = attendanceTime.split(DELIMITER);
        int hour = Integer.parseInt(words[HOUR_INDEX].trim());
        int minutes = Integer.parseInt(words[MINUTES_INDEX].trim());
        int day = Integer.parseInt(attendanceDate);
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, day, hour, minutes);
    }

    public static void validateDate(String input) {
        if (!input.matches(TIME_FORMAT)) {
            throw new IllegalArgumentException("[ERROR] 00:00 형식으로 입력해주세요.");
        }
    }

    public static void validateNumber(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("[ERROR]  숫자를 입력해주세요.");
        }
    }
}
