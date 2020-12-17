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

    public Button getDelete_button() {
        return delete_button;
    }

    public void setDelete_button(Button delete_button) {
        this.delete_button = delete_button;
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
    private Button delete_button;
    private Button search_button;
    private Button details_button;
    private String contact;

    public Request(String serial, String req_id, String name, String loc, String bg, String quantity, String date, Button d_button, Button s_button) {
        this.serial = serial;
        this.req_id = req_id;
        this.name = name;
        this.loc = loc;
        this.bg = bg;
        this.quantity = quantity;
        this.date = date;
        this.delete_button = d_button;
        this.search_button = s_button;
        this.delete_button.setText("Delete");
        this.search_button.setText("Search");
    }

    public Request(String serial, String req_id, String name, String bg, String loc, String date, String contact, Button dt_button) {
        this.serial = serial;
        this.req_id = req_id;
        this.name = name;
        this.loc = loc;
        this.bg = bg;
        this.date = date;
        this.contact = contact;
        this.details_button = dt_button;
        this.details_button.setText("Details");
        this.details_button.setPrefWidth(72.800048828125);
    }

}
