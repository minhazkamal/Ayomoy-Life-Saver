package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * This is the controller class for the new request of Blood
 * Donation from a Done.
 *
 * @author abdullah239
 */
public class DoneeNewReqController {

    ObservableList<String> BGList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    ObservableList<String> GenderList = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> LocList = FXCollections.observableArrayList();

    /**
     * Method for feting the Location details of a user
     * from the database using SQL query.
     */
    public void getLoc()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT NAME FROM LOCATIONS ORDER BY NAME";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            Statement pst = con.createStatement();

            ResultSet rs = pst.executeQuery(query);

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                LocList.add(rs.getString(1));
            }
            rs.close();
            con.close();
        } catch (Exception e)
        {
//           System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }
    @FXML
    private TextField txtUsername;
    @FXML
    private ChoiceBox doneeLocation;
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
    private TextField PatientName;
    @FXML
    private TextField emMobile;
    @FXML
    private TextArea locationDetails;
    @FXML
    private ChoiceBox Gender_choice;
////    @FXML
////    private Button submit;
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
        Parent root = FXMLLoader.load(getClass().getResource("DoneePanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
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
        txtUsername.setText(LogINpanelController.getUser());
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String type_query = "SELECT USER_TYPE FROM REGISTRATION WHERE USERNAME=?";
        String query = "";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst_type = con.prepareStatement(type_query);
            pst_type.setString(1,txtUsername.getText());

            ResultSet rs_type = pst_type.executeQuery();

            while(rs_type.next())
            {
                if(rs_type.getString(1).equals("Person")) query = "SELECT NAME, MOBILE FROM PERSONAL_INFO WHERE USERNAME=?";
                else if(rs_type.getString(1).equals("Organization")) query = "SELECT NAME, CONTACT FROM ORG_INFO WHERE USERNAME=?";
            }

            rs_type.close();

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtUsername.getText());
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                name.setText(rs.getString(1));
                mobile.setText(rs.getString(2));
            }
            rs.close();
            con.close();
        } catch (Exception e)
        {
//           System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

        getLoc();
        BG_choice.setItems(BGList);
        Gender_choice.setItems(GenderList);
        doneeLocation.setItems(LocList);
    }

    /**
     * Method for checking whether string is integer or not
     * @param s, string value
     * @return boolean, true if integer
     * and false if otherwise.
     */
    static boolean isInt(String s)
    {
        try
        { int i = Integer.parseInt(s); return true; }

        catch(NumberFormatException er)
        { return false; }
    }
    /**
     * This is for the checking alert while updating the information.
     * @return boolean, returns true value if there is an alert and
     * returns false if otherwise.
     */
    public boolean checkAlert()
    {
        boolean alertCheck = false;

        if(PatientName.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Patient Name Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(emMobile.getText().length()<11||emMobile.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Emergency Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(date.getValue().compareTo(LocalDate.now())<0)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Approximate Date can't be less than current time!!!"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(locationDetails.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Location Details Should be added"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(isInt(quantity.getText())==false)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Quantity will be a integer"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
    }

    /**
     * Method for submitting a new request from the Donee.
     * @param even, object of ActionEvent
     * @throws IOException, checked exception
     */
   public void submitNewReq(ActionEvent even) throws IOException{
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "INSERT INTO REQUEST (USERNAME, BLOOD_GROUP, LOCATION, LOCATION_DETAILS, COMPLICATIONS, QUANTITY, APPROX_DATE, PATIENT, P_GENDER, EM_MOBILE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String query2 = "SELECT * FROM LOCATIONS ORDER BY NAME";

            PreparedStatement pst = null;
            ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);

                pst = con.prepareStatement(query);
                pst.setString(1, txtUsername.getText());
                pst.setString(2, BG_choice.getValue().toString());
                pst.setString(3, doneeLocation.getValue().toString());
                pst.setString(4, locationDetails.getText());
                pst.setString(5, complications.getText());
                pst.setInt(6, Integer.parseInt(quantity.getText()));
                pst.setDate(7, Date.valueOf(date.getValue()));
                pst.setString(8, PatientName.getText());
                pst.setString(9,Gender_choice.getValue().toString());
                pst.setString(10, emMobile.getText());

                pst.executeUpdate();
                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Request Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Request Successfully Received");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("DoneePrevReq.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
                primaryStage.setResizable(false);
                primaryStage.show();

            } catch (Exception e)
            {
//           System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText(String.valueOf(e));
                alert.setResizable(false);
                alert.showAndWait();
            }
        }

    }
}
