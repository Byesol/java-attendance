package dto;


import domain.AbsenceLevel;
import domain.AttendanceResult;
import java.util.Map;

public class AbsenceCrewDto {

    private final String username;
    private final Map<AttendanceResult, Integer> results;
    private final String classifyAbsenceLevel;

    public AbsenceCrewDto(String username, Map<AttendanceResult, Integer> results, AbsenceLevel classifyAbsenceLevel) {
        this.username = username;
        this.results = results;
        this.classifyAbsenceLevel = classifyAbsenceLevel.getLevel();
    }

    public String getUsername() {
        return username;
    }

    public Map<AttendanceResult, Integer> getResults() {
        return results;
    }

    public String getClassifyAbsenceLevel() {
        return classifyAbsenceLevel;
    }
}
