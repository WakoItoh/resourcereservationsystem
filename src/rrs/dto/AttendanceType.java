package rrs.dto;

public class AttendanceType {

    private int attendanceTypeId;
    private String attendanceTypeName;

    public AttendanceType(int attendanceTypeId, String attendanceTypeName) {
        this.attendanceTypeId = attendanceTypeId;
        this.attendanceTypeName = attendanceTypeName;
    }

    public int getAttendanceTypeId() {
        return attendanceTypeId;
    }

    public void setAttendanceTypeId(int attendanceTypeId) {
        this.attendanceTypeId = attendanceTypeId;
    }

    public String getAttendanceTypeName() {
        return attendanceTypeName;
    }

    public void setAttendanceTypeName(String attendanceTypeName) {
        this.attendanceTypeName = attendanceTypeName;
    }

}
