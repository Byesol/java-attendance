import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Attendance;
import domain.AttendanceHistories;
import domain.AttendanceHistory;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoriesTest {

    @Test
    @DisplayName("이름을 기준으로 출석 기록 찾기")
    public void findAttendanceHistoriesByName() {
        // given
        String name = "쿠키";
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 18, 10, 1);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 19, 10, 2);
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance(attendanceTime1));
        attendances.add(new Attendance(attendanceTime2));
        attendances.add(new Attendance(attendanceTime3));
        AttendanceHistory attendanceHistory = new AttendanceHistory(name, attendances);

        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        attendanceHistories.add(attendanceHistory);
        AttendanceHistories histories = new AttendanceHistories(attendanceHistories);

        assertThat(histories.findByName(name).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("이름을 기준으로 기존 출석 기록에 추가하기")
    public void addAttendanceHistoriesByName() {
        // given
        String name = "쿠키";
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 18, 10, 1);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 19, 10, 2);
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance(attendanceTime1));
        attendances.add(new Attendance(attendanceTime2));
        attendances.add(new Attendance(attendanceTime3));
        AttendanceHistory attendanceHistory = new AttendanceHistory(name, attendances);

        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        attendanceHistories.add(attendanceHistory);
        AttendanceHistories histories = new AttendanceHistories(attendanceHistories);
        histories.addAttendanceHistory(name, new Attendance(LocalDateTime.of(2024, 12, 20, 10, 3)));

        assertThat(histories.findByName(name).getAttendances().size()).isEqualTo(4);


    }

    @Test
    @DisplayName("데이터로부터 결석 기록 포함해 출석 기록 객체 생성")
    public void makeAbsentHistoriesTest() {
        // given
        Map<String, List<LocalDateTime>> originalHistories = Map.of(
                "쿠키", List.of(
                        LocalDateTime.of(2024, 12, 1, 10, 0),
                        LocalDateTime.of(2024, 12, 3, 10, 0),
                        LocalDateTime.of(2024, 12, 5, 10, 0)
                )
        );
        LocalDate standard = LocalDate.of(2024, 12, 10);
        // when
        AttendanceHistories attendanceHistories = new AttendanceHistories(originalHistories, standard);
        // then
        List<AttendanceHistory> histories = attendanceHistories.getAttendanceHistories();
        assertThat(histories).isNotNull();
        assertThat(histories.size()).isEqualTo(originalHistories.size());
        AttendanceHistory aliceHistory = histories.get(0);
        List<Attendance> aliceAttendances = aliceHistory.getAttendances();
        assertThat(aliceAttendances.size()).isGreaterThan(originalHistories.get("쿠키").size());
        assertThat(aliceAttendances.stream()
                .anyMatch(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ABSENT))
                .isTrue();
    }


}
