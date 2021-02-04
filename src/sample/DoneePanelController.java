package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoneePanelController extends LogINpanelController{

    private static String type;
    public void initialize()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String type_query = "SELECT USER_TYPE FROM REGISTRATION WHERE USERNAME=?";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst_type = con.prepareStatement(type_query);
            pst_type.setString(1,LogINpanelController.getUser());

            ResultSet rs_type = pst_type.executeQuery();

            while(rs_type.next())
            {
                type = rs_type.getString(1);
            }

            rs_type.close();
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

    public void pressBack(ActionEvent even) throws IOException {

        if(type.equals("Person"))
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
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
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    }

    public void pressPrevReq(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("DoneePrevReq.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void pressNewReq(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("DoneeNewReq.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressSearch(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("SearchDonor-Org.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
