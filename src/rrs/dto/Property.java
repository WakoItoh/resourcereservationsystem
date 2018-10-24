package rrs.dto;

public class Property {

    private int propertyId;
    private String propertyName;

    public Property(int propertyId, String propertyName) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
