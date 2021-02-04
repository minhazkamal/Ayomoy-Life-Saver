package sample;

import javafx.scene.control.Button;

/**
 * This is the class for the donor instances of the System.
 *
 * @author abdullah239
 */
public class Donor {
    private String serial;
    private String req_id;
    private String name;
    private String loc;

    /**
     * Getter function for the Username
     * @return String, the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter Function of the USurname
     * @param username, the username intended to assign
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private String bg;
    private String paying_condition;
    private Button details_button;
    private String contact;
    private String username;

    /**
     * Getter function for the Serial
     * @return String, the Serial
     */
    public String getSerial() {
        return serial;
    }
    /**
     * Setter Function of the Serial
     * @param serial, the serial intended to assign
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * Getter function for the Request Identification
     * @return String, the Request Identification
     */
    public String getReq_id() {
        return req_id;
    }
    /**
     * Setter Function of the Request Identification
     * @param req_id, the Request Identification intended to assign
     */
    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    /**
     * Getter function for the Name
     * @return String, the Name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter Function of the Name
     * @param name, the Name intended to assign
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter function for the Location
     * @return String, the location
     */
    public String getLoc() {
        return loc;
    }

    /**
     * Setter Function of the Location
     * @param loc, the Location intended to assign
     */
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

    /**
     * Getter function for the Contact
     * @return String, the Request Identification
     */
    public String getContact() {
        return contact;
    }
    /**
     * Setter Function of the Contact Number
     * @param contact, the Contact Number intended to assign
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Constructor of the Class.
     *
     * @param username
     * @param serial
     * @param name
     * @param loc
     * @param bg
     * @param contact
     * @param paying_condition
     * @param details_button
     */
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
