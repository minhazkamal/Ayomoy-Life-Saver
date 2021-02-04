package sample;

import javafx.scene.control.Button;

/**
 * This Class is for the instances of Organization that are registred
 * into the system.
 *
 * @author abdullah239
 */
public class Organization {

    private String username;
    private String serial;
    private String name;
    private String loc;
    private String contact;
    private String cpmobile;
    private String email;
    private Button details_button;

    /**
     * Getter function for accessing the username of the Org.
     * @return String, the user name of the Org.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter function to assign the set Username
     * @param username, String denoting the Username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter function for accessing the serial Number of the Org.
     * @return String, which is the serial Number
     */
    public String getSerial() {
        return serial;
    }
    /**
     * Setter function to assign the Serial Number of Org.
     * @param serial, a String denoting the serial number.
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }
    /**
     * Getter function for accessing the Name of the Org.
     * @return String, the name of the Org.
     */
    public String getName() {
        return name;
    }
    /**
     * Setter function to assign the name of the Org.
     * @param  name, the name of the Org.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter function for accessing the Location of the Org.
     * @return String, the Location details of the Org.
     */
    public String getLoc() {
        return loc;
    }
    /**
     * Setter function to assign the Location details of the Org.
     * @param loc, the location details of the Org.
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }
    /**
     * Getter function for accessing the Contact Number of Org.
     * @return String, the Contact Number of Org.
     */
    public String getContact() {
        return contact;
    }
    /**
     * Setter function to assign the Contact Number or the Org.
     * @param contact, the Contact number of the Org.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * Getter function for accessing the Mobile number of the Contact Person
     * @return String, the Number of the contact Person.
     */
    public String getCpmobile() {
        return cpmobile;
    }
    /**
     * Setter function to assign the Number of the Contact Person.
     * @param cpmobile, the Number of the Contact Person
     */
    public void setCpmobile(String cpmobile) {
        this.cpmobile = cpmobile;
    }
    /**
     * Getter function for accessing the Email
     * @return String, the email of Org.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Setter function to assign the Email
     * @param email, of the Org.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public Button getDetails_button() {
        return details_button;
    }

    public void setDetails_button(Button details_button) {
        this.details_button = details_button;
    }

    /**
     * Constructor of the Class to assign the values.
     *
     * The names of the Parameters are self-explanatory.
     * @param username
     * @param serial
     * @param name
     * @param loc
     * @param contact
     * @param cpmobile
     * @param email
     * @param details_button
     */
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
