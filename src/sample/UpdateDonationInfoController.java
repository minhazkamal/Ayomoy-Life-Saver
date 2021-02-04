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

public class UpdateDonationInfoController extends DonationInfoController{

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

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM DONATION_INFO WHERE USERNAME=? AND D_DATE=?";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, LogINpanelController.getUser());
            pst.setDate(2, Date.valueOf(DonationInfoController.getUpdate_date()));

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                p_Location.setValue(rs.getString(2));
                p_name.setText(rs.getString(3));
                p_Gender_choice.setValue(rs.getString(4));
                complications.setText(rs.getString(5));
                p_mobile.setText(rs.getString(6));
                d_date.setValue(rs.getDate(7).toLocalDate());
                p_locationDetails.setText(rs.getString(8));
            }
            rs.close();
            con.close();
        } catch (Exception e)
        {
           System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }


    public boolean checkAlert()
    {
        boolean alertCheck = false;

        if(p_mobile.getText()!=null && (p_mobile.getText().length()<11||p_mobile.getText().length()>11))
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Patient Mobile Number should be 11 digits"));
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


    public void pressUpdateDonation(ActionEvent even) throws IOException {

        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "UPDATE DONATION_INFO SET LOCATION=?, P_NAME=?, P_GENDER=?, COMPLICATIONS=?, P_CONTACT=?, LOCATION_DETAILS=? WHERE USERNAME=? AND D_DATE=?";

            PreparedStatement pst = null;

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);

                pst = con.prepareStatement(query);

                pst.setString(7, txtUser.getText());
                pst.setString(1, p_Location.getValue().toString());
                pst.setString(2, p_name.getText());
                if(p_Gender_choice.getValue()==null) pst.setString(3, null);
                else pst.setString(3, p_Gender_choice.getValue().toString());
                pst.setString(4, complications.getText());
                pst.setString(5, p_mobile.getText());
                pst.setDate(8, Date.valueOf(d_date.getValue()));
                pst.setString(6, p_locationDetails.getText());

                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Request Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Information Successfully Updated");
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
