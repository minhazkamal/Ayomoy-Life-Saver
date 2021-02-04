package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class OrganizationPanelController extends LogINpanelController {

    @FXML
    private Label showUsername;
    @FXML
    private Label showName;
    @FXML
    private Label showMobileNo;
    @FXML
    private Label showEmail;
    @FXML
    private Label showLic;
    @FXML
    private Label showCP;
    @FXML
    private Label showLocation;
    @FXML
    private Label showCPmobile;
    @FXML
    private TextArea description;
    @FXML
    private Label eligibility;

    public void initialize()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM ORG_INFO WHERE USERNAME=?";
        String query1 = "SELECT USERNAME,ELIGIBILITY FROM ORG_LIC_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, LogINpanelController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(LogINpanelController.getUser())) {
                    //System.out.println(rs.getString(1));
                    showUsername.setText(rs.getString(1));
                    showName.setText(rs.getString(2));
                    showMobileNo.setText(rs.getString(3));
                    showEmail.setText(rs.getString(4));
                    showLocation.setText(rs.getString(5));
                    showLic.setText(rs.getString(6));
                    showCP.setText(rs.getString(7));
                    showCPmobile.setText(rs.getString(8));
                    description.setText(rs.getString(9));

                    PreparedStatement pst1 = con.prepareStatement(query1);
                    pst1.setString(1, LogINpanelController.getUser());
                    //System.out.println(LogINpanelController.getUser());

                    ResultSet rs1 = pst1.executeQuery();

//            eligibility.setText(rs.getString(2));
//            System.out.println(rs.getString(2));
//            activeness.setText(rs.getString(3));
//            System.out.println(rs.getString(3));

                    while(rs1.next())
                    {
                        eligibility.setText(rs1.getString(2));
                        //System.out.println(rs.getString(2));
                    }

                    pst1.close();
                    rs1.close();
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
    }
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

    public void PressUpdateProfile(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UpdateOrg.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void PressAsDonor(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrganizationDonorPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void PressAsDonee(ActionEvent even) throws IOException {
        //System.out.println(LogINpanelController.getUser());
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DoneePanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
