package sample;

import javafx.scene.control.Button;

/**
 * This is the class for handling the instances of Donor in the system.
 *
 * @author fairuz240
 */
public class Donor {
    private String serial;
    private String req_id;
    private String name;
    private String loc;

    /**
     * Getter method for the username
     * @return String, the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for the username.
     * @param username, the username of the Donor.
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
     * Getter method for the Serial Number
     * @return String, the Serial Number
     */
    public String getSerial() {
        return serial;
    }
    /**
     * Setter method for the Serial Number.
     * @param serial, the Serial Number.
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * Getter method for the Request ID
     * @return String, the Request ID
     */
    public String getReq_id() {
        return req_id;
    }
    /**
     * Setter method for the Request ID.
     * @param req_id, the Request ID.
     */
    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    /**
     * Getter method for the Name
     * @return String, the Name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter method for the Name.
     * @param name, the Name of the Donor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the Location Details
     * @return String, the Location details
     */
    public String getLoc() {
        return loc;
    }
    /**
     * Setter method for the Location details.
     * @param loc, the Location details of the Donor.
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
     * Getter method for the Contact Number
     * @return String, the Contact Number
     */
    public String getContact() {
        return contact;
    }
    /**
     * Setter method for the Contact Number.
     * @param contact, the Contact Number of the Donor.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Constructor with parameters for the initialization of a donor instance.
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
