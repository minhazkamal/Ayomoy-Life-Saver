package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/**
 * This controller Class is for the Showing of Detals of a blood donation
 * request from the Donor.
 *
 * @author fairuz240
 */
public class ShowDetailRequestInfoController {

    @FXML
    private TextField req_id;
    @FXML
    private ChoiceBox BG_choice;
    @FXML
    private TextField mobile;
    @FXML
    private TextField name;
    @FXML
    private TextField complications;
    @FXML
    private TextField quantity;
    @FXML
    private DatePicker date;
    @FXML
    private TextField emMobile;
    @FXML
    private TextField PatientName;
    @FXML
    private ChoiceBox doneeLocation;
    @FXML
    private TextArea locationDetails;
    @FXML
    private ChoiceBox Gender_choice;

    /**
     * This method is for finding the type of user for a particular request id.
     * Sends SQL Query for the relevant list. Catch block is there for the exceptions.
     *
     * @return String, type of the user.
     */
    private String find_type()
    {
        String type = null;
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT USERNAME, USER_TYPE FROM REGISTRATION WHERE USERNAME = (SELECT USERNAME FROM REQUEST WHERE REQUEST.ID=?)";


        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst2 = con.prepareStatement(query);
            pst2.setInt(1, Integer.parseInt(PendingRequestController.getR_id()));

            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {
                type = rs2.getString(2);
            }
            rs2.close();
            con.close();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
        return type;
    }

    /**
     * This is the initializing function of the fxml components.
     *
     * When a the window is launched this method initializes these
     * components. Establishes the connection with the database and
     * upon fetching the data columns works with the property values.
     *
     */
    public void initialize()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = null;
        if(find_type().equals("Person"))
            query = "SELECT * FROM REQUEST, PERSONAL_INFO WHERE REQUEST.USERNAME=PERSONAL_INFO.USERNAME AND REQUEST.ID=?";
        else if(find_type().equals("Organization"))
            query = "SELECT * FROM REQUEST, ORG_INFO WHERE REQUEST.USERNAME=ORG_INFO.USERNAME AND REQUEST.ID=?";


        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst2 = con.prepareStatement(query);
            pst2.setInt(1, Integer.parseInt(PendingRequestController.getR_id()));

            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {
                req_id.setText(String.valueOf(rs2.getInt(1)));
                BG_choice.setValue(rs2.getString(3));
                doneeLocation.setValue(rs2.getString(4));
                locationDetails.setText(rs2.getString(5));
                complications.setText(rs2.getString(6));
                quantity.setText(String.valueOf(rs2.getInt(7)));
                date.setValue(rs2.getDate(8).toLocalDate());
                PatientName.setText(rs2.getString(9));
                Gender_choice.setValue(rs2.getString(10));
                emMobile.setText(rs2.getString(11));
                name.setText(rs2.getString(13));
                mobile.setText(rs2.getString(14));
                //System.out.println(count_req);
            }
            rs2.close();
            con.close();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }
    /**
     * This method is here for the functionality of the "Back" button. This will redirect the
     * user to the parent node that is the "root" which is sample.UserPanel.fxml. This hierarchical
     * structure is for the simplicity of the system.
     *
     * @param even, object of ActionEvent generated from click or Enter
     * @throws IOException,  checked exception
     */
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PendingRequest.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
