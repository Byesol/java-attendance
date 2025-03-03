package configure;

import controller.AttendanceController;
import domain.AttendanceHistories;
import java.time.LocalDate;
import util.AttendanceDataReader;
import util.CsvAttendanceDataReader;
import view.InputView;
import view.OutputVIew;

public class AttendanceControllerFactory {
    private static AttendanceController attendanceController;
    private static OutputVIew outputVIew;
    private static InputView inputView;
    private static AttendanceHistories attendanceHistories;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            return new AttendanceController(outputVIew(), inputView(), attendanceHistories());
        }
        return attendanceController;
    }

    private OutputVIew outputVIew() {
        if (outputVIew == null) {
            outputVIew = new OutputVIew();
        }
        return outputVIew;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    private AttendanceHistories attendanceHistories() {
        if (attendanceHistories == null) {
            LocalDate now = LocalDate.of(2024, 12, 18);
            AttendanceDataReader attendanceDataReader = new CsvAttendanceDataReader();
            attendanceHistories = new AttendanceHistories(attendanceDataReader.loadAttendanceData(),
                    LocalDate.of(2024, 12, now.getDayOfMonth()));
        }
        return attendanceHistories;
    }
}