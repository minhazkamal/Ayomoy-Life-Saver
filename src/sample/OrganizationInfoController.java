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
 * This Class is for Handeing the Organization info During Registration.
 *
 * The class extends the RegisterController.
 * @author minhaz231
 */
public class OrganizationInfoController extends RegisterController {

    ObservableList<String> LocList = FXCollections.observableArrayList();


    @FXML
    private TextField OrgName;
    @FXML
    private TextField ContactNo;
    @FXML
    private ChoiceBox AddressLocation;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField LicNo;
    @FXML
    private TextField ContactPerson;
    @FXML
    private TextField CPMobile;
    @FXML
    private TextArea description;
    @FXML
    private Button back_button;

    /**
     * This Methods fetches the Location details from the database.
     * handles the Exceptions with the help of Catch Block and displays
     * error on the window title as waging.
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
        back_button.setVisible(false);
        getLoc();
        txtUsername.setText(getTxtUsername());
        AddressLocation.setItems(LocList);
    }

    /**
     *
     * This functions is for checking the errors in the add new information and Image files.
     *
     * The function is here to show alert on three scenarios.
     * First of all it checks the text field of the mobile number.
     * If the mobile number is not given or blank than it will show error.
     * But for a given mobile number it wil show alert because of the digits less
     * than the number of 11. ALso it will check for the valid characters inserted.
     *
     * Then, using the previous checkDate method it will check the validity
     * of the date and alert accordingly.
     *
     * The location details supports no granularity check rather it sees
     * whether any details is given or not and alerts the user Otherwise.
     *
     * @return boolean which depends on the value of alert check.
     *
     */
    public boolean checkAlert()
    {
        boolean alertCheck = false;
        if(txtEmail.getText().isBlank()||txtEmail.getText().indexOf('@')==-1)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Email Address is not correct"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(OrgName.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Organization Name Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(ContactNo.getText().length()<11||ContactNo.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(LicNo.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("License Number Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(ContactPerson.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Contact Person Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(CPMobile.getText().length()<11||CPMobile.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Emergency Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(description.getText().length()>250)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Description should be in 250 characters"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
    }

    /**
     * This method is here for the functionality of the "Back" button. This will redirect the
     * user to the parent node that is the "root" which is sample.UserPanel.fxml. This hierarchical
     * structure is for the simplicity of the system.
     *
     * @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void pressBack(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This method is here for the functionality of Submit button in the Ogr mode.
     *
     * There are information of Orgs that are Needed to updated frequently (Legal Payers)
     * and some information are there which might change over time. This button submits
     * the changed information into the Database using the DML.
     *
     * @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void pressSubmit(ActionEvent even) throws IOException {
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "INSERT INTO ORG_INFO VALUES(?,?,?,?,?,?,?,?,?)";
            String query1 = "INSERT INTO ORG_LIC_INFO (USERNAME) VALUES (?)";

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);
                PreparedStatement pst1 = con.prepareStatement(query1);

                pst.setString(1, txtUsername.getText());
                pst.setString(2, OrgName.getText());
                pst.setString(3, ContactNo.getText());
                pst.setString(4, txtEmail.getText());
                pst.setString(5, AddressLocation.getValue().toString());
                pst.setString(6, LicNo.getText());
                pst.setString(7, ContactPerson.getText());
                pst.setString(8, CPMobile.getText());
                pst.setString(9, description.getText());

                pst.executeUpdate();

                pst1.setString(1, txtUsername.getText());

                pst1.executeUpdate();

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Registration Completed Successfully");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
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
        else
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("OrganizationInfo.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
}
