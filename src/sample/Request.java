package sample;

import javafx.scene.control.Button;

/**
 * This class is for the instances of DNation requests in the system.
 * @author minhaz231
 */
public class Request {
    private String serial;
    private String req_id;
    private String name;
    private String loc;


    /**
     * Getter method for the serial number of a Request
     * @return String, serial number of a Request
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Setter method for the Serial Number of a Request
     * @param serial, serial Number of a Request
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }
    /**
     * Getter method for the Request ID of a Request
     * @return String, Request ID of a Request
     */
    public String getReq_id() {
        return req_id;
    }
    /**
     * Setter method for the Request ID of a Request
     * @param req_id, Request ID of a Request
     */
    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }
    /**
     * Getter method for Name of a Request
     * @return String, Name of a Request
     */
    public String getName() {
        return name;
    }
    /**
     * Setter method for the Name of a Request
     * @param name, Name of a Request
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter method for the Location details of a Request
     * @return String, Location details of a Request
     */
    public String getLoc() {
        return loc;
    }
    /**
     * Setter method for the Location Details of a Request
     * @param loc, Location of a Request
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }
    /**
     * Getter method for Blood Group of a Request
     * @return String, Blood Group of a Request
     */
    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getQuantity() {
        return quantity;
    }
    /**
     * Setter method for the number of Blood Bags of a Request
     * @param quantity, quantity of Blood Bags a Request
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    /**
     * Getter method for the Date of a Request
     * @return String, Date of a Request
     */
    public String getDate() {
        return date;
    }

    public Button getDetails_button() {
        return details_button;
    }

    public void setDetails_button(Button details_button) {
        this.details_button = details_button;
    }
    /**
     * Getter method for the Contact of a Request
     * @return String, Contact of a Request
     */
    public String getContact() {
        return contact;
    }
    /**
     * Setter method for the Contact Number of a Request
     * @param contact, Contact of a Request
     */
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
    /**
     * Setter method for the Date of a Request
     * @param date, Date of a Request
     */
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

    /**
     * First Contructor of the Request Class to Initialize
     * @param serial
     * @param req_id
     * @param name
     * @param loc
     * @param bg
     * @param quantity
     * @param date
     * @param d_button
     * @param s_button
     */
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

    /**
     * Second Constructor of the Request Class.
     * @param serial
     * @param req_id
     * @param name
     * @param bg
     * @param loc
     * @param date
     * @param contact
     * @param dt_button
     */
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
