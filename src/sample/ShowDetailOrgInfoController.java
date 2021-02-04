package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowDetailOrgInfoController {

    @FXML
    private Label showName;
    @FXML
    private Label showMobileNo;
    @FXML
    private Label showEmail;
    @FXML
    private Label showLocation;
    @FXML
    private Label cp;
    @FXML
    private Label cp_mobile;
    @FXML
    private TextArea description;

    public void initialize()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ORG_INFO.* FROM ORG_INFO WHERE USERNAME=?";
//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, SearchOrgController.getUser());
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                showName.setText(rs.getString(2));
                showMobileNo.setText(rs.getString(3));
                showEmail.setText(rs.getString(4));
                showLocation.setText(rs.getString(5));
                cp.setText(rs.getString(7));
                cp_mobile.setText(rs.getString(8));
                description.setText(rs.getString(9));
            }
            rs.close();
            con.close();

        } catch (Exception e)
        {
//            System.out.println(1);
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
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("SearchOrg.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
