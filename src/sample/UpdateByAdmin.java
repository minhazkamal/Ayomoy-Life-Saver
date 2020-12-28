package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;



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



    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getComment() {
        return comment;
    }

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

    public Button getLic_button() {
        return lic_button;
    }

    public void setLic_button(Button lic_button) {
        this.lic_button = lic_button;
    }

    public ChoiceBox getStatus_choice() {
        return status_choice;
    }

    public void setStatus_choice(ChoiceBox status_choice) {
        this.status_choice = status_choice;
    }

    public ChoiceBox getApproval_choice() {
        return approval_choice;
    }

    public void setApproval_choice(ChoiceBox approval_choice) {
        this.approval_choice = approval_choice;
    }


}
