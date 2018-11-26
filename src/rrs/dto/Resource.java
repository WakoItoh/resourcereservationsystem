package rrs.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Resource {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String TIME_FORMAT_PATTERN = "HH:mm";

    private int resourceId;
    private String resourceName;
    private Category category;
    private int capacity;
    private Office office;
    private List<Property> propertyList;
    private String note;
    private Date suspendStart;
    private Date suspendEnd;
    private int deleted;
    private int status;

    public Resource() {
    }

    public Resource(int resourceId, String resourceName, Category category, int capacity, Office office, List<Property> propertyList, String note,
            Date suspendStart, Date suspendEnd, int deleted, int status) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.category = category;
        this.capacity = capacity;
        this.office = office;
        this.propertyList = propertyList;
        this.note = note;
        this.suspendStart = suspendStart;
        this.suspendEnd = suspendEnd;
        this.deleted = deleted;
        this.status = status;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSuspendStart() {
        return suspendStart;
    }

    public void setSuspendStart(Date suspendStart) {
        this.suspendStart = suspendStart;
    }

    public Date getSuspendEnd() {
        return suspendEnd;
    }

    public void setSuspendEnd(Date suspendEnd) {
        this.suspendEnd = suspendEnd;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSuspendStartDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String suspendStartDate = "";
        if (suspendStart != null) {
            suspendStartDate = formatter.format(suspendStart);
        }

        return suspendStartDate;
    }

    public String getSuspendStartTime() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String suspendStartTime = "";
        if (suspendStart != null) {
            suspendStartTime = formatter.format(suspendStart);
        }

        return suspendStartTime;
    }

    public String getSuspendEndDate() {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String suspendEndDate = "";
        if (suspendEnd != null) {
            suspendEndDate = formatter.format(suspendEnd);
        }

        return suspendEndDate;
    }

    public String getSuspendEndTime() {

        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        String suspendEndTime = "";
        if (suspendEnd != null) {
            suspendEndTime = formatter.format(suspendEnd);
        }

        return suspendEndTime;
    }

}
