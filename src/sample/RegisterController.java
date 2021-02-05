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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * This is the Controller Class to handheld the register the Person and the
 * Organizations of the system.
 *
 * @author minhaz231
 */
public class RegisterController {
    ObservableList<String> TypeList = FXCollections.observableArrayList("Person", "Organization");
    private static String z;
    @FXML
    private Button back_button;
    @FXML
    private ChoiceBox choiceType;
    @FXML
    private TextField txtUsername;

    /**
     * Getter function of the Username.
     * @return String, the username.
     */
    public static String getTxtUsername() {
        return z;
    }

    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;

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
        choiceType.setItems(TypeList);
    }

    /**
     * Method for checking the validity of the user
     * @return boolean, true if valid user and false otherwise.
     */
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

    /**
     * Method for checking the Reentered password
     * for confirmations.
     * @return boolean, truth value based on the
     * fact whether two strings match or not.
     */
    public boolean checkPassword()
    {
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) return true;
        else return false;
    }

    /**
     * This method is for checking the Alerts of the total process.
     *
     * The Fetched data from the database ond the user i/p is
     * processed here and in case of any anomaly which may crash the
     * program or create inconsistency, the alert value will be true.
     *
     * @return boolean and the value depends on the truth value of
     * the alert.
     */
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

    /**
     * This is for the first page of Registrations. After the user gives the
     * username, password and type, the next button will be pressed. These
     * information will be written into the database and the next form for
     * details is presented by the sample.PersonalInfo.fxml file.
     *
     * @param even object of ActionEvent Class.
     * @throws IOException, Checked Exception for i/p and o/p.
     */
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
                    primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
                    primaryStage.initModality(Modality.APPLICATION_MODAL);
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
                    primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
                    primaryStage.initModality(Modality.APPLICATION_MODAL);
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
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
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
        Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
