package rrs.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AvailableResource {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String TIME_FORMAT_PATTERN = "HH:mm";
    private static final String TIME_00 = "00:00";
    private static final String TIME_24 = "24:00";
    private static final long HOURS_24 = 24 * 60 * 60 * 1000;

    private Date availableStart;
    private Date availableEnd;
    private Resource resource;

    public AvailableResource() {
    }

    public AvailableResource(Date availableStart, Date availableEnd, Resource resource) {
        this.availableStart = availableStart;
        this.availableEnd = availableEnd;
        this.resource = resource;
    }

    public Date getAvailableStart() {
        return availableStart;
    }

    public void setAvailableStart(Date availableStart) {
        this.availableStart = availableStart;
    }

    public Date getAvailableEnd() {
        return availableEnd;
    }

    public void setAvailableEnd(Date availableEnd) {
        this.availableEnd = availableEnd;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getAvailableStartDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String availableStartDate = formatter.format(availableStart);

        return availableStartDate;
    }

    public String getAvailableStartTime() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String availableStartTime = formatter.format(availableStart);

        return availableStartTime;
    }

    public String getAvailableEndDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String availableEndTime = formatter.format(availableEnd);
        formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String availableEndDate = formatter.format(availableEnd);
        if (TIME_00.equals(availableEndTime)) {
            availableEndDate = formatter.format(new Date(availableEnd.getTime() - HOURS_24));
        }

        return availableEndDate;
    }

    public String getAvailableEndTime() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String availableEndTime = formatter.format(availableEnd);
        if (TIME_00.equals(availableEndTime)) {
            availableEndTime = TIME_24;
        }

        return availableEndTime;
    }

}
