package sample ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * This class is used to handle the changing password features of the
 * users and also Admin will be able to take certain advantages of this
 * class also.
 *
 * @author minhaz231
 */
public class ChangePasswordController extends LogINpanelController{

    /**
     * These are fxml related variables used for capturing the
     * relevant information in the fields taken input from the
     * user. Made Private for privacy issues.
     */

    @FXML
    private TextField txtUsername ;
    @FXML
    private PasswordField currPass ;
    @FXML
    private PasswordField newPass ;
    @FXML
    private PasswordField confirmPass ;

    /**
     * This function is for checking the validity of the given current password.
     * This is because a user can leave open his/ her machine for a time and an
     * intruder may change the password meanwhile. Crosschecking it will definitely
     * make the system more secure. The catch block is there to catch the exceptions
     * and add them to the title according to the string value.
     *
     * @return boolean
     * true if we get the same password from the i/p and false if otherwise.
     *
     */
    public boolean checkCurrPassValid()
    {
        String temp ;
        temp = new String() ;
        String username ;
        username = "als" ;
        String password ;
        password = "iutcse18" ;
        String url ;
        url = "jdbc:oracle:thin:@localhost:1521/XE" ;
        String query ;
        query = "SELECT USERNAME, PASSWORD FROM REGISTRATION WHERE USERNAME=?" ;

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver") ;
            Connection con = DriverManager.getConnection(url,username,password) ;
            PreparedStatement pst = con.prepareStatement(query) ;
            pst.setString(1, txtUsername.getText()) ;

            ResultSet rs = pst.executeQuery() ;

            while(rs.next())
            {
                if(rs.getString(1).equals(txtUsername.getText())) {
                    temp = rs.getString(2);
                }
            }

            rs.close();
            con.close();

        } catch (Exception e)
        {

            Alert alert ;
            alert = new Alert(Alert.AlertType.ERROR) ;
            alert.setTitle("Error Dialog") ;
            alert.setHeaderText("Look, an Error Dialog") ;
            alert.setContentText(String.valueOf(e)) ;
            System.out.println(e) ;
            alert.setResizable(false) ;
            alert.showAndWait() ;
        }

        if(temp.equals(currPass.getText())) return true;
        else return false;
    }

    /**
     * This checks the new password taken form the user and confirms
     * the re-input form the user.
     *
     * @return boolean
     * true if matches and false if otherwise.
     */
    public boolean checkPassword()
    {
        if(newPass.getText().equals(confirmPass.getText()))  return true;
        else return  false;
    }

    /**
     * This is for the checking alert and show the scenarios to the user when the
     * password is not able to be changed. The scenarios covered for the alert in this
     * functions are: not match in the current password, Password length greater than
     * 15 characters and new password not matching upon re-entering.
     *
     * @return boolean
     * which is the value of alerted variable
     */
    public boolean checkAlert()
    {
        boolean alertCheck = false;

        if(checkCurrPassValid()==false)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Current Password didn't match!!!"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        if(newPass.getText().length()>15 || newPass.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Password length should not be empty or greater than 15 characters"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(checkPassword()==false)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("New Password didn't match!!!"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
    }

    /**
     * This method is here to initialize the whole process of updating.
     */
    public void initialize()
    {
        txtUsername.setText(LogINpanelController.getUser());
    }

    public void pressChange(ActionEvent even) {
        if(checkAlert()==false)
        {
            String username;
            username = "als";
            String password;
            password = "iutcse18";
            String url;
            url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query;
            query = "UPDATE REGISTRATION SET PASSWORD=? WHERE USERNAME=?";

            try{

                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, newPass.getText());
                pst.setString(2, txtUsername.getText());

                pst.executeUpdate();

                con.close();

                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Password Changed Successfully!!!");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

            } catch (Exception e)
            {

                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText(String.valueOf(e));
                alert.setResizable(false);
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Password didn't change. Please try again!!!"));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    /**
     * This the functionality of the cancel button in the change password window.
     * this just closes the whole window.
     *
     * @param even
     * which is an ActiveEvent Object
     */
    public void pressCancel(ActionEvent even) {
        ((Node) even.getSource()).getScene().getWindow().hide();
    }
}
