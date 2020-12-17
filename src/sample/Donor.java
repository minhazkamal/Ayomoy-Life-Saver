package sample;

import javafx.scene.control.Button;

public class Donor {
    private String serial;
    private String req_id;
    private String name;
    private String loc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String bg;
    private String paying_condition;
    private Button details_button;
    private String contact;
    private String username;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
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

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getPaying_condition() {
        return paying_condition;
    }

    public void setPaying_condition(String paying_condition) {
        this.paying_condition = paying_condition;
    }

    public Button getDetails_button() {
        return details_button;
    }

    public void setDetails_button(Button details_button) {
        this.details_button = details_button;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Donor(String username, String serial, String name, String loc, String bg, String contact, String paying_condition, Button details_button) {
        this.username = username;
        this.serial = serial;
        this.name = name;
        this.loc = loc;
        this.bg = bg;
        this.paying_condition = paying_condition;
        this.contact = contact;
        this.details_button = details_button;
        this.details_button.setText("Details");
        this.details_button.setPrefWidth(72.800048828125);
    }
}
