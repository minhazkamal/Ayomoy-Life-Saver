package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This Class is for Updating the profile of user.
 * Extends the UserPanelController Class.
 *
 * @author fairuz240
 */
public class UserProfileUpdateController extends UserPanelController{

    ObservableList<String> BGList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    ObservableList<String> GenderList = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> LocList = FXCollections.observableArrayList();

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField name;
    @FXML
    private TextField MobileNo;
    @FXML
    private ChoiceBox BG_choice;
    @FXML
    private ChoiceBox AddressLocation;
    @FXML
    private DatePicker DOB;
    @FXML
    private TextField txtUsername;
    @FXML
    private ChoiceBox Gender_Choice;

    /**
     * Method to show the already existing information i.e
     * the previous information in the system. Information
     * fetched from the databse using SQL query.
     */
    public void showPrevInfo()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM PERSONAL_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, LogINpanelController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(LogINpanelController.getUser())) {
                    //System.out.println(rs.getString(1));
                    txtUsername.setText(rs.getString(1));
                    name.setText(rs.getString(2));
                    MobileNo.setText(rs.getString(3));
                    txtEmail.setText(rs.getString(4));
                    DOB.setValue(rs.getDate(5).toLocalDate());
                    //showDOB.setText(rs.getDate(5).toLocalDate().format("dd-MM-yyyy").);
                    AddressLocation.setValue(rs.getString(6));
                    Gender_Choice.setValue(rs.getString(7));
                    BG_choice.setValue(rs.getString(8));

                    rs.close();
                    con.close();
                    return;
                }
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
            System.out.println(e);
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

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
    /**
     * This is for the checking alert while updating the information.
     * @return boolean, returns true value if there is an alert and
     * returns false if otherwise.
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
        if(name.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Name Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(MobileNo.getText().length()<11||MobileNo.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(DOB.getValue().compareTo(LocalDate.now())>=0)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("DOB can't be greater than current time!!!"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
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
        getLoc();
        //BG_choice.setItems(BGList);
        Gender_Choice.setItems(GenderList);
        AddressLocation.setItems(LocList);
        showPrevInfo();
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
        Parent root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * The Method for the functionality of the update button. The updated information
     * given here are then sent to the database via DML and overwritten.
     * catch block is there for handling the exceptions for the null entries.
     *
     * @param even, object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p.
     */
    public void pressUpdate(ActionEvent even) throws IOException{
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "UPDATE PERSONAL_INFO SET NAME=?, MOBILE=?, EMAIL=?, DOB=?, LOCATION=?, GENDER=?, BLOOD_GROUP=? WHERE USERNAME=?";

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);
                //pst.setString(1, txtUsername.getText());
                pst.setString(1, name.getText());
                pst.setString(2, MobileNo.getText());
                pst.setString(3, txtEmail.getText());
                pst.setDate(4, Date.valueOf(DOB.getValue()));
                pst.setString(5, AddressLocation.getValue().toString());
                pst.setString(6, Gender_Choice.getValue().toString());
                pst.setString(7, BG_choice.getValue().toString());

                pst.setString(8, txtUsername.getText());

                pst.executeUpdate();

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Profile Updated Successfully");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
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
        else
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("UserProfileUpdate.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
    /**
     * Method for changing the password of Org. Loads the sample.ChangePassword.fxml.
     *
     * @param even, object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p.
     */
    public void pressChangePass(ActionEvent even) throws IOException{

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
    }
}
