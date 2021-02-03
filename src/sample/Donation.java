package sample;

import javafx.scene.control.Button;

/**
 * This class is for maintaining the donation records.
 *
 * Here the comprehensive properties are maintained to uniquely and extensively
 * keep track of a record. The class has appropriate constructor with parameters
 * for the default writings of the properties. Also the appropriate accessing function
 * are there.
 *
 * @author fairuz240
 */

public class Donation {
    private String serial;
    private String name;
    private String loc;
    private String date;

    /**
     * Constructor of the Class
     *
     * @param serial
     * which is the serial of the donation in the list
     *
     * @param name
     * name of the Donation, referred from the Donors name
     *
     * @param loc
     * Location of the donation to take place, taken from the Done
     *
     * @param date
     * Date of the Donation to take place
     *
     * @param details_button
     * This shows the details that are stored into the database
     *
     * @param update_button
     * this updates the donation information if needed form the donors end
     */

    public Donation(String serial, String name, String loc,
                    String date, Button details_button, Button update_button) {

        this.serial = serial;
        this.name = name;
        this.loc = loc;
        this.date = date;
        this.details_button = details_button;
        this.update_button = update_button;
        this.details_button.setText("Details");
        this.update_button.setText("Update");
    }

    /**
     * Getter function of the Donation Serial
     *
     * @return String
     * showing the serial number
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Setter Function of the Serial
     *
     * @param serial
     * which takes the spiral number as string and assigns it to the property
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * Getter Function for the Name of the donation
     *
     * @return String
     * Which gives the name of the Donation
     */
    public String getName() {
        return name;
    }

    /**
     * Setter Function of the Name
     *
     * @param name
     * takes this name from the Donor and assigns it to the property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter Function for the Location
     * @return String
     */
    public String getLoc() {
        return loc;
    }

    /**
     * Setter Function for the Location
     *
     * @param loc
     * sets takes the location from the Donor and assigns them to the class property
     * The location are stored in the observable list and fetched from there when
     * the user selects them.
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * Getter Function for the Date
     * @return String
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter Function for the Date
     *
     * @param date
     * sets takes the date from the Donor and assigns them to the class property
     */
    public void setDate(String date) {
        this.date = date;
    }

    // No use
    public Button getDetails_button() {
        return details_button;
    }

    /// No use
    public void setDetails_button(Button details_button) {
        this.details_button = details_button;
    }

    // No use
    public Button getUpdate_button() {
        return update_button;
    }

    // No use
    public void setUpdate_button(Button update_button) {
        this.update_button = update_button;
    }

    private Button details_button;
    private Button update_button;

}
