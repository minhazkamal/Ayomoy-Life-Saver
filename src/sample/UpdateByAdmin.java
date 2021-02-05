package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * This class is for the features of Updating by the Admin Profile.
 *
 * @author minhaz231
 */

public class UpdateByAdmin {

    private String serial;
    private String username;
    private String DOS;
//    private String status;
//    private String approval;
    private Button update_button;
    private Button lic_button;
    private ChoiceBox status_choice;
    private ChoiceBox approval_choice;
    private String comment;
    private Button report_button;

    /**
     * Constructor of the UpdateByAdmin Class
     * @param serial
     * @param username
     * @param DOS
     * @param status_choice
     * @param approval_choice
     * @param comment
     * @param report_button
     * @param update_button
     */
    public UpdateByAdmin(String serial, String username, String DOS, ChoiceBox status_choice, ChoiceBox approval_choice, String comment, Button report_button, Button update_button) {
        ObservableList<String> Aprroval = FXCollections.observableArrayList("Select One", "Approved", "Rejected");
        ObservableList<String> Status = FXCollections.observableArrayList("Select One", "Valid", "Invalid");
        this.serial = serial;
        this.username = username;
        this.DOS = DOS;
        this.comment = comment;
        this.report_button = report_button;
        this.report_button.setText("View");
        this.report_button.setPrefWidth(72.0);
        this.update_button = update_button;
        this.update_button.setText("Update");
        this.update_button.setPrefWidth(90.39990234375);
        this.status_choice = status_choice;
        this.status_choice.setValue("Select One");
        this.status_choice.setItems(Status);
        this.approval_choice = approval_choice;
        this.approval_choice.setValue("Select One");
        this.approval_choice.setItems(Aprroval);
    }


    /**
     * Getter method for the serail Number of the users.
     * @return String, serial number of the users.
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Setter function for the Serial number of a review.
     * @param serial, the serial number of review requests.
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }
    /**
     * Getter method for the Username.
     * @return String, the username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Setter function for the username in a review.
     * @param username, the username of the user which needs to be review.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter method for the DOS of the user.
     * @return String, the DOS of the USer.
     */
    public String getDOS() {
        return DOS;
    }

    public void setDOS(String DOS) {
        this.DOS = DOS;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getApproval() {
//        return approval;
//    }
//
//    public void setApproval(String approval) {
//        this.approval = approval;
//    }
    /**
     * Getter method for the Comment
     * @return String, the comment by the admin.
     */
    public String getComment() {
        return comment;
    }
    /**
     * Setter function for the Admin Comment after review.
     * @param comment, te comment by the Admin
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Button getReport_button() {
        return report_button;
    }

    public void setReport_button(Button report_button) {
        this.report_button = report_button;
    }

    public Button getUpdate_button() {
        return update_button;
    }

    public void setUpdate_button(Button update_button) {
        this.update_button = update_button;
    }
    /**
     * Getter method for the  Licence of the Org.
     * @return String, the string value from the license button.
     */
    public Button getLic_button() {
        return lic_button;
    }

    public void setLic_button(Button lic_button) {
        this.lic_button = lic_button;
    }
    /**
     * Getter method for the  Status of the Persons.
     * @return String, the Status Flag of the Person.
     */
    public ChoiceBox getStatus_choice() {
        return status_choice;
    }

    public void setStatus_choice(ChoiceBox status_choice) {
        this.status_choice = status_choice;
    }
    /**
     * Getter method for the approval choice of the Organization and Person.
     * @return ChoiceBox, The approval choices of the Organization and Person
     */
    public ChoiceBox getApproval_choice() {
        return approval_choice;
    }

    public void setApproval_choice(ChoiceBox approval_choice) {
        this.approval_choice = approval_choice;
    }


}
