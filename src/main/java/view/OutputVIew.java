package view;


import static domain.AbsenceLevel.LATE_TO_ABSENCE_THRESHOLD;
import static domain.AbsenceLevel.NORMAL;
import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.ATTENDANCE;
import static domain.AttendanceResult.LATE;

import domain.AttendanceResult;
import dto.AbsenceCrewDto;
import dto.HistoriesDto;
import dto.HistoryDto;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OutputVIew {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void printAttendanceConfirmation(LocalDateTime attendanceTime, String attendanceResult) {
        String time = DateTimeViewConverter.dateFormattingForOutput(attendanceTime);
        System.out.printf("%s (%s)\n", time, attendanceResult);
    }

    public void printEditAttendance(LocalDateTime before, String beforeResult, LocalDateTime edit,
                                    String editResult) {
        String beforeTime = DateTimeViewConverter.dateFormattingForOutput(before);
        String editTime = DateTimeViewConverter.timeFormattingForOutput(edit);
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!\n", beforeTime, beforeResult, editTime, editResult);
    }

    public void printHistories(HistoriesDto historiesDto) {
        printAttendanceHistory(historiesDto);
        printAttendanceAllResult(historiesDto);
        printUserAttendanceResult(historiesDto);
    }

    private void printAttendanceHistory(HistoriesDto historiesDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", historiesDto.username());
        for (HistoryDto history : historiesDto.histories()) {
            String time = DateTimeViewConverter.dateFormattingForOutput(history.attendanceTime());
            System.out.printf("%s (%s)\n", time, history.attendanceResult());
        }
    }

    private void printUserAttendanceResult(HistoriesDto historiesDto) {
        String classifyResult = historiesDto.classifyAbsenceLevel();
        if (!classifyResult.equals(NORMAL.getLevel())) {
            System.out.printf("%s 대상자 입니다.\n", classifyResult);
        }
        System.out.print(LINE_SEPARATOR);
    }

    private void printAttendanceAllResult(HistoriesDto historiesDto) {
        Map<AttendanceResult, Integer> result = historiesDto.attendanceAllResult();
        System.out.print(LINE_SEPARATOR);
        System.out.printf("출석: %d회\n", result.getOrDefault(ATTENDANCE, 0));
        System.out.printf("지각: %d회\n", result.getOrDefault(LATE, 0));
        System.out.printf("결석: %d회\n", result.getOrDefault(ABSENCE, 0));
        System.out.print(LINE_SEPARATOR);
    }

    public void printDangerous(List<AbsenceCrewDto> crewsDto) {
        System.out.println("제적 위험자 조회 결과");
        sortByAbsenceCount(crewsDto);
        printDangerousCrews(crewsDto);
        System.out.print(LINE_SEPARATOR);
    }

    private void sortByAbsenceCount(List<AbsenceCrewDto> crewsDto) {
        crewsDto.sort(Comparator.comparingInt((AbsenceCrewDto crew) -> {
            int absenceCount = crew.getResults().getOrDefault(LATE, 0);
            absenceCount += crew.getResults().getOrDefault(ABSENCE, 0) * LATE_TO_ABSENCE_THRESHOLD;
            return absenceCount;
        }).reversed().thenComparing(AbsenceCrewDto::getUsername));
    }

    private void printDangerousCrews(List<AbsenceCrewDto> crewsDto) {
        crewsDto.forEach(crew -> {
            Map<AttendanceResult, Integer> results = crew.getResults();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", crew.getUsername(),
                    results.getOrDefault(ABSENCE, 0),
                    results.getOrDefault(LATE, 0),
                    crew.getClassifyAbsenceLevel());
        });
    }


}

