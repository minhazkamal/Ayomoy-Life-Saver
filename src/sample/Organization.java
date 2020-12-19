package sample;

import javafx.scene.control.Button;

public class Organization {

    private String username;
    private String serial;
    private String name;
    private String loc;
    private String contact;
    private String cpmobile;
    private String email;
    private Button details_button;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCpmobile() {
        return cpmobile;
    }

    public void setCpmobile(String cpmobile) {
        this.cpmobile = cpmobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Button getDetails_button() {
        return details_button;
    }

    public void setDetails_button(Button details_button) {
        this.details_button = details_button;
    }

    public Organization(String username, String serial, String name, String loc, String contact, String cpmobile, String email, Button details_button) {
        this.username = username;
        this.serial = serial;
        this.name = name;
        this.loc = loc;
        this.contact = contact;
        this.cpmobile = cpmobile;
        this.email = email;
        this.details_button = details_button;
        this.details_button.setText("Details");
        this.details_button.setPrefWidth(73.60003662109375);
    }
}
