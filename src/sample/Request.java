package sample;

import javafx.scene.control.Button;

public class Request {
    private String serial;
    private String req_id;
    private String name;
    private String loc;


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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public Button getUp_button() {
        return up_button;
    }

    public void setUp_button(Button up_button) {
        this.up_button = up_button;
    }

    public Button getSearch_button() {
        return search_button;
    }

    public void setSearch_button(Button search_button) {
        this.search_button = search_button;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String bg;
    private String quantity;
    private String date;
    private Button up_button;
    private Button search_button;

    public Request(String serial, String req_id, String name, String loc, String bg, String quantity, String date) {
        this.serial = serial;
        this.req_id = req_id;
        this.name = name;
        this.loc = loc;
        this.bg = bg;
        this.quantity = quantity;
        this.date = date;
        this.up_button = new Button("Update");
        this.search_button = new Button("Search");
    }

}
