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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This Class Controllers the sample.LogINpanel.fxml file.
 *
 * Handles the Log in of the users through fecthing the credentials.
 *
 * @author fairuz240
 */
public class LogINpanelController {
    ObservableList<String> TypeList = FXCollections.observableArrayList("Admin", "Person", "Organization");
    public static String user;
    public static String type;


    /**
     * Getter function for the type, explicitly mentioned for
     * optimization. Not needed to parse through the whole user table
     * because of this. Static Method.
     * @return String, type of the user.
     */
    public static String getType() {
        return type;
    }

    /**
     * Getter function of the User, static method
     * @return String, which is the user name.
     */
    public static String getUser() {
        return user;
    }

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ChoiceBox user_type;

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
        user_type.setItems(TypeList);
    }

    /**
     * Method for checking the credentials given input.
     *
     * The Credentials are taken and query is generated accordingly. Then the database is accessed
     * to see the existence of a user. No Null entries are possible here in the relations.
     *
     * @return boolean, true of credentials match Catch block used for the catching og
     * of different possible exceptions in runtime or false otherwise.
     */
    public boolean checkCredentials()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT USERNAME,PASSWORD,USER_TYPE FROM REGISTRATION WHERE USERNAME=? AND PASSWORD=? AND USER_TYPE=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtUsername.getText());
            pst.setString(2, txtPassword.getText());
            pst.setString(3, user_type.getValue().toString());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(txtUsername.getText()) && rs.getString(2).equals(txtPassword.getText()) && rs.getString(3).equals(user_type.getValue().toString()))
                {
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
            alert.setResizable(false);
            alert.showAndWait();
        }
        return false;
    }

    /**
     * This method is for the "Log IN" button in the Log in Panel.
     *
     * The upon checking valid credentials this will show a confirmation window
     * or show error.
     *
     * @param even an Object of the ActionEvent Class
     * @throws IOException which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */

    public void pressLogIN(ActionEvent even) throws IOException{
        if(checkCredentials()==true)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("LogIN Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Logged IN Successfully");
            alert.setResizable(false);
            alert.showAndWait();

            if(type.equals("Person"))
            {
                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.setResizable(false);
                primaryStage.show();
            }
            else if(type.equals("Organization"))
            {
                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("OrganizationPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.setResizable(false);
                primaryStage.show();
            }
            else if(type.equals("Admin"))
            {
                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.setResizable(false);
                primaryStage.show();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Username/Password/USER_TYPE is incorrect"));
            //System.out.println(e);
            alert.setResizable(false);
            alert.showAndWait();

            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    }

    /**
     * This is the method for the Register button on the Log In Panel. This laods the
     * sample.Register.fxml and presents the form of registration with appropriate fields.
     * @param even an Object of the ActionEvent Class
     * @throws IOException which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void pressRegister(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

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
    public void pressBack(ActionEvent even) throws IOException {

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
