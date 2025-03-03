import configure.AttendanceControllerFactory;
import controller.AttendanceController;

public class Main {
    public static void main(String[] args) {
        AttendanceControllerFactory attendanceControllerFactory = new AttendanceControllerFactory();
        AttendanceController attendanceController = attendanceControllerFactory.attendanceController();
        attendanceController.start();
    }
}
