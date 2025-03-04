package controller;

import static view.DateTimeViewConverter.changeStandardDate;

import domain.AbsenceLevel;
import domain.Attendance;
import domain.AttendanceHistories;
import domain.AttendanceHistory;
import domain.AttendanceResult;
import dto.HistoriesDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputVIew;
import view.SelectionOption;

public class AttendanceController {

    private final OutputVIew outputVIew;
    private final InputView inputView;
    private final AttendanceHistories attendanceHistories;

    public AttendanceController(OutputVIew outputVIew, InputView inputView, AttendanceHistories attendanceHistories) {
        this.outputVIew = outputVIew;
        this.inputView = inputView;
        this.attendanceHistories = attendanceHistories;
    }

    public void start() {
        SelectionOption answer;
        do {
            answer = inputView.getMenu();
            executeMenu(answer);
        } while (!(answer == SelectionOption.QUIT));
    }

    private void executeMenu(SelectionOption answer) {
        if (answer == SelectionOption.ADD_ATTENDANCE) {
            addAttendance();
        }
//        if (answer == SelectionOption.EDIT_ATTENDANCE) {
//            editAttendance();
//        }
        if (answer == SelectionOption.GET_ATTENDANCE_HISTORY) {
            getAllAttendance();
        }
//        if (answer == SelectionOption.CHECK_ABSENCE_USERS) {
//            getAbsenceUsers();
//        }
    }

    private void addAttendance() {
        String name = inputView.getName();
        LocalDateTime attendanceTime = inputView.getAttendanceTime();
        attendanceHistories.addAttendanceHistory(name, new Attendance(attendanceTime));
        String historyResult = attendanceHistories.findByName(name).getAttendances().stream()
                .filter(attendance -> attendance.getAttendanceTime().equals(attendanceTime))
                .findFirst()
                .map(attendance -> attendance.getAttendanceResult().name())
                .orElse("UNKNOWN");
        outputVIew.printAttendanceConfirmation(attendanceTime, historyResult);
    }

    //    private void editAttendance() {
//        String editName = inputView.getEditName();
//        LocalDateTime editHistory = inputView.getEditAttendanceTime();
//        AttendanceHistory attendanceHistory = attendanceHistories.findByName(editName);
//        LocalDateTime beforeHistory = attendanceHistory.getAttendances().stream()
//                .filter(attendance -> attendance.getAttendanceTime().toLocalDate().equals(editHistory.toLocalDate()))
//                .findFirst()
//                .map(Attendance::getAttendanceTime)
//                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 출석 기록이 없습니다."));
//        String beforeResult = attendanceHistory.getAttendances().stream()
//                .filter(attendance -> attendance.getAttendanceTime().equals(beforeHistory))
//                .findFirst()
//                .map(attendance -> attendance.getAttendanceStatus().name())
//                .orElse("UNKNOWN");
//        attendanceHistory.addAttendance(new Attendance(editHistory));
//        String editResult = attendanceHistory.getAttendances().stream()
//                .filter(attendance -> attendance.getAttendanceTime().equals(editHistory))
//                .findFirst()
//                .map(attendance -> attendance.getAttendanceStatus().name())
//                .orElse("UNKNOWN");
//        outputVIew.printEditAttendance(beforeHistory, beforeResult, editHistory, editResult);
//    }
//
    private void getAllAttendance() {
        String username = inputView.getName();
        LocalDateTime newDate = changeStandardDate(LocalDateTime.now());
        AttendanceHistory attendanceHistory = attendanceHistories.findByName(username);
        List<Attendance> beforeAttendanceHistory = attendanceHistory.getAttendances();
        Map<AttendanceResult, Integer> attendanceAllResult = attendanceHistories.getAttendanceAllResult(username,
                newDate);
        AbsenceLevel classifyAbsenceLevel = attendanceHistories.getClassifyAbsenceLevel(username, newDate);
        HistoriesDto historiesDto = HistoriesDto.of(username, beforeAttendanceHistory, attendanceAllResult,
                classifyAbsenceLevel);
        outputVIew.printHistories(historiesDto);
    }
//
//    private void getAbsenceUsers() {
//        LocalDateTime newDate = changeStandardDate(LocalDateTime.now());
//        List<AttendanceHistory> members = attendanceHistories.getAttendanceHistories();
//        List<AbsenceCrewDto> crewDtos = members.stream().map(member -> {
//            Map<AttendanceStatus, Integer> results = member.getAttendances().stream()
//                    .collect(Collectors.groupingBy(Attendance::getAttendanceStatus, Collectors.summingInt(e -> 1)));
//            AbsenceLevel classifyAbsenceLevel = AbsenceLevel.classify(results);
//            return new AbsenceCrewDto(member.getName(), results, classifyAbsenceLevel);
//        }).collect(Collectors.toList());
//        outputVIew.printDangerous(crewDtos);
//    }


}