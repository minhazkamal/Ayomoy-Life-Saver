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

public class AddNewDonationController extends LogINpanelController {

    ObservableList<String> GenderList = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> LocList = FXCollections.observableArrayList();
    @FXML
    private TextField p_mobile;
    @FXML
    private TextField p_name;
    @FXML
    private TextField complications;
    @FXML
    private DatePicker d_date;
    @FXML
    private ChoiceBox p_Location;
    @FXML
    private TextArea p_locationDetails;
    @FXML
    private TextField txtUser;
    @FXML
    private ChoiceBox p_Gender_choice;

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

    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());
        getLoc();
        p_Gender_choice.setItems(GenderList);
        p_Location.setItems(LocList);
    }

    public boolean checkDate()
    {
        int i=0;
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT D_DATE FROM DONATION_INFO WHERE USERNAME=? AND D_DATE BETWEEN ADD_MONTHS(?, -3) AND ADD_MONTHS(?, 3)";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, txtUser.getText());
            pst.setDate(2, Date.valueOf(d_date.getValue()));
            pst.setDate(3, Date.valueOf(d_date.getValue()));

            ResultSet rs = pst.executeQuery();


            while(rs.next())
            {
               i++;
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

        if(i==0) return true;
        else return false;
    }

    public boolean checkAlert()
    {
        boolean alertCheck = false;

        if(p_mobile.getText().isBlank()==false && (p_mobile.getText().length()<11||p_mobile.getText().length()>11))
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Patient Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        if(checkDate()==false)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Donation date is invalid. Because you have donation in last/next 3 months."));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(p_locationDetails.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Location Details Should be added"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
    }


    public void pressSubmitDonation(ActionEvent even) throws IOException, NullPointerException{
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "INSERT INTO DONATION_INFO VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = null;

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);

                pst = con.prepareStatement(query);
                pst.setString(1, txtUser.getText());
                pst.setString(2, p_Location.getValue().toString());
                pst.setString(3, p_name.getText());
                if(p_Gender_choice.getValue()==null) pst.setString(4, null);
                else pst.setString(4, p_Gender_choice.getValue().toString());
                pst.setString(5, complications.getText());
                pst.setString(6, p_mobile.getText());
                pst.setDate(7, Date.valueOf(d_date.getValue()));
                pst.setString(8, p_locationDetails.getText());

                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Request Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Information Successfully Received");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
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

    }

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
