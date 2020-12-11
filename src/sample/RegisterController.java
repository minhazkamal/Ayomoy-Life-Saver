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

public class RegisterController {
    ObservableList<String> TypeList = FXCollections.observableArrayList("Person", "Organization");
    private static String z;
    @FXML
    private Button back_button;
    @FXML
    private ChoiceBox choiceType;
    @FXML
    private TextField txtUsername;

    public static String getTxtUsername() {
        return z;
    }

    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;

    public void initialize()
    {
        choiceType.setItems(TypeList);
    }

    public boolean checkUserValid()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT USERNAME FROM REGISTRATION WHERE USERNAME=?";

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
                    rs.close();
                    con.close();
                    return false;
                }
            }
                rs.close();
                con.close();
                return true;

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
        return false;
    }

    public boolean checkPassword()
    {
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) return true;
        else return false;
    }

    public boolean checkAlert()
    {
        boolean alertCheck = false;
        if(txtUsername.getText().length()>15||txtUsername.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Username length should not be empty or greater than 15 characters"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(checkUserValid()==false)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Username exists already. Try different one."));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(txtPassword.getText().length()>15||txtPassword.getText().isBlank())
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
            alert.setContentText(String.valueOf("Password didn't match!!!"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(choiceType.getValue().toString().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Choice type shouldn't be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        return alertCheck;
    }

    public void pressNext(ActionEvent even) throws IOException {
        if(checkAlert()==false)
        {
            String a = txtUsername.getText();
            z = a;
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "INSERT INTO REGISTRATION VALUES(?,?,?)";

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, txtUsername.getText());
                pst.setString(2, txtPassword.getText());
                pst.setString(3, choiceType.getValue().toString());

                pst.executeUpdate();

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("To Complete the registration fill up the next form.");
                alert.setResizable(false);
                alert.showAndWait();

                if(choiceType.getValue().toString().equals("Person"))
                {
                    ((Node) even.getSource()).getScene().getWindow().hide();

                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("PersonalInfo.fxml"));
                    primaryStage.setTitle("Ayomoy Life Saver");
                    primaryStage.setScene(new Scene(root, 1000, 600));
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
                else if(choiceType.getValue().toString().equals("Organization"))
                {
                    ((Node) even.getSource()).getScene().getWindow().hide();

                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("OrganizationInfo.fxml"));
                    primaryStage.setTitle("Ayomoy Life Saver");
                    primaryStage.setScene(new Scene(root, 1000, 600));
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }


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
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    public void pressBack(ActionEvent even) throws IOException {

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
