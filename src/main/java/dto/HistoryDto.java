package dto;

import java.time.LocalDateTime;

public record HistoryDto(
        LocalDateTime attendanceTime,
        String attendanceResult
) {
}
