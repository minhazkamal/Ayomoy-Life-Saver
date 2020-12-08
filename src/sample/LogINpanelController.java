package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogINpanelController {
    public static String user;
    public String type;

    public static String getUser() {
        return user;
    }

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    public boolean checkCredentials()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT USERNAME,PASSWORD,USER_TYPE FROM REGISTRATION WHERE USERNAME=? AND PASSWORD=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtUsername.getText());
            pst.setString(2, txtPassword.getText());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(txtUsername.getText()) && rs.getString(2).equals(txtPassword.getText())) {
                    //System.out.println(rs.getString(1));
                    user = rs.getString(1);
                    type = rs.getString(3);
                    rs.close();
                    con.close();
                    return true;
                }
            }
            rs.close();
            con.close();
            return false;

        } catch (Exception e)
        {
//           System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            System.out.println(e);

            alert.showAndWait();
        }
        return false;
    }


    public void pressLogIN(ActionEvent even) throws IOException{
        if(checkCredentials()==true)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("LogIN Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Logged IN Successfully");

            alert.showAndWait();

            if(type.equals("Person"))
            {
                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.show();
            }
            else if(type.equals("Organization"))
            {
                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("OrganizationPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.show();
            }
            else if(type.equals("Admin"))
            {
                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.show();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Username/Password is incorrect"));
            //System.out.println(e);

            alert.showAndWait();

            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        }

    }

    public void pressRegister(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

    }

    public void pressBack(ActionEvent even) throws IOException {

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}
