package rrs.dto;

public class User {

    private String id;
    private String password;
    private String lastName;
    private String firstName;
    private int userLevel;
    private String phone;
    private String email;

    public User() {
    }

    public User(String id, String password, String lastName, String firstName, int userLevel, String phone, String email) {
        this.id = id;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userLevel = userLevel;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
