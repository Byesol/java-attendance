import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.AttendanceResult;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CheckAttendanceTest {


    //1번 과정
    //출석, 결석, 지각,
    @Test
    @DisplayName("출석 확인 처리 - 출석, 지각, 결석")
    public void checkAttendanceStatusTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 17, 10, 6);
        LocalDateTime absentTime = LocalDateTime.of(2024, 12, 17, 10, 40);
        // when
        Attendance attendance = new Attendance(attendanceTime);
        Attendance late = new Attendance(lateTime);
        Attendance absent = new Attendance(absentTime);
        // then
        assertThat(attendance.getAttendanceResult()).isEqualTo(AttendanceResult.ATTENDANCE);
        assertThat(late.getAttendanceResult()).isEqualTo(AttendanceResult.LATE);
        assertThat(absent.getAttendanceResult()).isEqualTo(AttendanceResult.ABSENCE);

    }

    @Test
    @DisplayName("월요일 출석 확인 처리 - 출석, 지각, 결석")
    public void checkMondayAttendanceStatus() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 16, 13, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 16, 13, 6);
        LocalDateTime absentTime = LocalDateTime.of(2024, 12, 16, 13, 40);
        // when
        Attendance attendance = new Attendance(attendanceTime);
        Attendance late = new Attendance(lateTime);
        Attendance absent = new Attendance(absentTime);
        // then
        assertThat(attendance.getAttendanceResult()).isEqualTo(AttendanceResult.ATTENDANCE);
        assertThat(late.getAttendanceResult()).isEqualTo(AttendanceResult.LATE);
        assertThat(absent.getAttendanceResult()).isEqualTo(AttendanceResult.ABSENCE);

    }

    // 운영시간 외 처리
    @Test
    @DisplayName("운영 시간 예외 처리 테스트")
    public void isOperatingTimeTest() {
        //given
        LocalDateTime beforeOperatingTime = LocalDateTime.of(2024, 12, 17, 7, 0);
        LocalDateTime afterOperatingTime = LocalDateTime.of(2024, 12, 17, 23, 40);
        //when

        assertThatThrownBy(() -> new Attendance(beforeOperatingTime))
                .hasMessage("운영시간이 아닙니다.");
        assertThatThrownBy(() -> new Attendance(afterOperatingTime))
                .hasMessage("운영시간이 아닙니다.");

    }

    @Test
    @DisplayName("휴일 처리 예외 테스트(주말, 공휴일 예외 처리")
    public void isHoliday() {
        LocalDateTime notHoliday = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime weekend = LocalDateTime.of(2024, 12, 21, 10, 0);
        LocalDateTime christmas = LocalDateTime.of(2024, 12, 25, 10, 0);

        assertThatThrownBy(() -> new Attendance(weekend))
                .hasMessageContaining("12월 21일 토요일은 운영일이 아닙니다..");
        assertThatThrownBy(() -> new Attendance(christmas))
                .hasMessageContaining("12월 25일 수요일은 운영일이 아닙니다.");
    }


}
