package rrs.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String TIME_FORMAT_PATTERN = "HH:mm";
    private static final String TIME_00 = "00:00";
    private static final String TIME_24 = "24:00";
    private static final long HOURS_24 = 24 * 60 * 60 * 1000;

    private int reservationId;
    private Date useStart;
    private Date useEnd;
    private Resource resource;
    private String meetingName;
    private User reservator;
    private User coReservator;
    private int attendanceCount;
    private AttendanceType attendanceType;
    private String note;

    public Reservation() {
    }

    public Reservation(int reservationId, Date useStart, Date useEnd, Resource resource, String meetingName, User reservator, User coReservator,
            int attendanceCount, AttendanceType attendanceType, String note) {
        this.reservationId = reservationId;
        this.useStart = useStart;
        this.useEnd = useEnd;
        this.resource = resource;
        this.meetingName = meetingName;
        this.reservator = reservator;
        this.coReservator = coReservator;
        this.attendanceCount = attendanceCount;
        this.attendanceType = attendanceType;
        this.note = note;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getUseStart() {
        return useStart;
    }

    public void setUseStart(Date useStart) {
        this.useStart = useStart;
    }

    public Date getUseEnd() {
        return useEnd;
    }

    public void setUseEnd(Date useEnd) {
        this.useEnd = useEnd;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public User getReservator() {
        return reservator;
    }

    public void setReservator(User reservator) {
        this.reservator = reservator;
    }

    public User getCoReservator() {
        return coReservator;
    }

    public void setCoReservator(User coReservator) {
        this.coReservator = coReservator;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(int attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUseStartDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String useStartDate = formatter.format(useStart);

        return useStartDate;
    }

    public String getUseStartTime() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String useStartTime = formatter.format(useStart);

        return useStartTime;
    }

    public String getUseEndDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String useEndTime = formatter.format(useEnd);
        formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String useEndDate = formatter.format(useEnd);
        if (TIME_00.equals(useEndTime)) {
            useEndDate = formatter.format(new Date(useEnd.getTime() - HOURS_24));
        }

        return useEndDate;
    }

    public String getUseEndTime() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String useEndTime = formatter.format(useEnd);
        if (TIME_00.equals(useEndTime)) {
            useEndTime = TIME_24;
        }

        return useEndTime;
    }

}
