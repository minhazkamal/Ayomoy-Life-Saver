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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

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

            alert.showAndWait();
        }
    }

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

            alert.showAndWait();
        }
        if(name.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Name Field can not be blank"));

            alert.showAndWait();
        }
        if(MobileNo.getText().length()<11||MobileNo.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Mobile Number should be 11 digits"));

            alert.showAndWait();
        }
        if(DOB.getValue().compareTo(LocalDate.now())>=0)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("DOB can't be greater than current time!!!"));

            alert.showAndWait();
        }

        return alertCheck;
    }

    public void initialize()
    {
        getLoc();
        txtUsername.setText(getTxtUsername());
        BG_choice.setItems(BGList);
        Gender_Choice.setItems(GenderList);
        AddressLocation.setItems(LocList);
    }

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public void pressSubmit(ActionEvent even) throws IOException{
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "INSERT INTO PERSONAL_INFO VALUES(?,?,?,?,?,?,?,?)";

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

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Registration Completed Successfully");

                alert.showAndWait();

            } catch (Exception e)
            {
//           System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText(String.valueOf(e));

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
            primaryStage.show();
        }
    }
}
