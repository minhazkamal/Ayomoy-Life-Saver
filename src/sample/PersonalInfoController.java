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
 * This class is the handler of the Personal Information of the
 * Person type users. Extends the RegisterController Class.
 *
 * @author fairuz240
 */
public class PersonalInfoController extends RegisterController{
    ObservableList<String> BGList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    ObservableList<String> GenderList = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> LocList = FXCollections.observableArrayList();

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
    @FXML
    private TextField txtEmail;
    @FXML
    private Button back_button;

    /**
     * Fetches the Location details from the Database of a Person.
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
        back_button.setVisible(false);
        getLoc();
        txtUsername.setText(getTxtUsername());
        BG_choice.setItems(BGList);
        Gender_Choice.setItems(GenderList);
        AddressLocation.setItems(LocList);
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
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This method is for the submit button of the personal information.
     *
     * @param even object of ActionEvent
     * @throws IOException checked exception for i/p and o/p
     */
    public void pressSubmit(ActionEvent even) throws IOException{
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "INSERT INTO PERSONAL_INFO VALUES(?,?,?,?,?,?,?,?)";
            String query1 = "INSERT INTO DONOR_INFO (USERNAME) VALUES(?)";
            String query2 = "INSERT INTO TEST_REPORTS (USERNAME) VALUES(?)";

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, txtUsername.getText());
                pst.setString(2, name.getText());
                pst.setString(3, MobileNo.getText());
                pst.setString(4, txtEmail.getText());
                pst.setDate(5, Date.valueOf(DOB.getValue()));
                pst.setString(6, AddressLocation.getValue().toString());
                pst.setString(7, Gender_Choice.getValue().toString());
                pst.setString(8, BG_choice.getValue().toString());

                pst.executeUpdate();

                PreparedStatement pst1 = con.prepareStatement(query1);
                pst1.setString(1, txtUsername.getText());
                pst1.executeUpdate();

                PreparedStatement pst2 = con.prepareStatement(query2);
                pst2.setString(1, txtUsername.getText());
                pst2.executeUpdate();

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
            Parent root = FXMLLoader.load(getClass().getResource("PersonalInfo.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
}
