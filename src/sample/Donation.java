package sample;

import javafx.scene.control.Button;

public class Donation {
    private String serial;
    private String name;
    private String loc;
    private String date;

    public Donation(String serial, String name, String loc, String date, Button details_button, Button update_button) {
        this.serial = serial;
        this.name = name;
        this.loc = loc;
        this.date = date;
        this.details_button = details_button;
        this.update_button = update_button;
        this.details_button.setText("Details");
        this.update_button.setText("Update");
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Button getDetails_button() {
        return details_button;
    }

    public void setDetails_button(Button details_button) {
        this.details_button = details_button;
    }

    public Button getUpdate_button() {
        return update_button;
    }

    public void setUpdate_button(Button update_button) {
        this.update_button = update_button;
    }

    private Button details_button;
    private Button update_button;

}
