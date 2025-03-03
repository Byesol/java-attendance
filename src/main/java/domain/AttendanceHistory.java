package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceHistory {

    private final String name;
    private final List<Attendance> attendances;

    public AttendanceHistory(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = new ArrayList<>(attendances);
    }


    public String getName() {
        return name;
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }
}
