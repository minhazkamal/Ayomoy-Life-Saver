package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DonorPanelController extends LogINpanelController{

    @FXML
    private Label elg_st;
    @FXML
    private Label act_st;
    @FXML
    private Label pay_st;
    @FXML
    private Label paying;
    @FXML
    private Label eligibility;
    @FXML
    private Label activeness;

    public void initialize()
    {
        if(LogINpanelController.getType().equals("Organization"))
        {
            pay_st.setVisible(false);
            act_st.setVisible(false);
            elg_st.setVisible(false);
        }
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT USERNAME,ELIGIBILITY,ACTIVENESS,PAYABLE FROM DONOR_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, LogINpanelController.getUser());
            //System.out.println(LogINpanelController.getUser());

            ResultSet rs = pst.executeQuery();

//            eligibility.setText(rs.getString(2));
//            System.out.println(rs.getString(2));
//            activeness.setText(rs.getString(3));
//            System.out.println(rs.getString(3));

            while(rs.next())
            {
                eligibility.setText(rs.getString(2));
                //System.out.println(rs.getString(2));
                activeness.setText(rs.getString(3));
                //System.out.println(rs.getString(3));
                paying.setText(rs.getString(4));
            }

            pst.close();
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

    public void pressDonorInfo(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonorInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressDonationInfo(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressPendingRequest(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PendingRequest.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressBack(ActionEvent even) throws IOException {
        if(LogINpanelController.getType().equals("Organization"))
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("OrganizationPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    }
}
