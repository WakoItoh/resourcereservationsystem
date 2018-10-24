package rrs.dto;

public class Office {

    private int officeId;
    private String officeName;
    private String officeLocation;

    public Office(int officeId, String officeName, String officeLocation) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.officeLocation = officeLocation;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

}
