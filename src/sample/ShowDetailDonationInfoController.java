package sample;

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

public class ShowDetailDonationInfoController extends LogINpanelController{
    @FXML
    private TextField p_mobile;
    @FXML
    private TextField p_name;
    @FXML
    private TextField complications;
    @FXML
    private DatePicker d_date;
    @FXML
    private TextField showLocation;
    @FXML
    private TextArea p_locationDetails;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField showGender;

    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());

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
                showLocation.setText(rs.getString(2));
                p_name.setText(rs.getString(3));
                showGender.setText(rs.getString(4));
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

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressUpdateDonation(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UpdateDonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
