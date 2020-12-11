package sample;

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

public class ChangePasswordController extends LogINpanelController{
    @FXML
    private Label title_label;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField currPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField confirmPass;

    public boolean checkCurrPassValid()
    {
        String temp = new String();
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT USERNAME, PASSWORD FROM REGISTRATION WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtUsername.getText());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(txtUsername.getText())) {
                    //System.out.println(rs.getString(1));
                    temp = rs.getString(2);
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
        if(temp.equals(currPass.getText())) return true;
        else return false;
    }

    public boolean checkPassword()
    {
        if(newPass.getText().equals(confirmPass.getText())) return true;
        else return false;
    }

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
        if(newPass.getText().length()>15||newPass.getText().isBlank())
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

    public void initialize()
    {
        txtUsername.setText(LogINpanelController.getUser());
    }

    public void pressChange(ActionEvent even) {
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "UPDATE REGISTRATION SET PASSWORD=? WHERE USERNAME=?";

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, newPass.getText());
                pst.setString(2, txtUsername.getText());

                pst.executeUpdate();

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Password Changed Successfully!!!");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

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
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Password didn't change. Please try again!!!"));
            //System.out.println(e);
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    public void pressCancel(ActionEvent even) {
        ((Node) even.getSource()).getScene().getWindow().hide();
    }
}
