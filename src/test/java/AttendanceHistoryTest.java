import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import domain.Attendance;
import domain.AttendanceHistory;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    @DisplayName("데이터로부터 이름과 출석시간들을 받아 출석 이력을 생성한다.")
    public void getAttendanceHistory() {
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 18, 10, 1);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 19, 10, 2);
        String name = "쿠키";
        List<Attendance> attendances = List.of(new Attendance(attendanceTime1), new Attendance(attendanceTime2),
                new Attendance(attendanceTime3));

        AttendanceHistory attendanceHistory = new AttendanceHistory(name, attendances);

        assertThat(attendanceHistory).isNotNull();

    }

}
