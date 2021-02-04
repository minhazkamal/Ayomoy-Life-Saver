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

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The Organization Can also perform the same Tasks of the Donor. This class is for
 * facilitating those features of the Organization.
 *
 * @author fairuz240
 */
public class OrganizationDonorPanelController extends LogINpanelController{

//    @FXML
//    private Label eligibility;
//
//    public void initialize()
//    {
//        String username = "als";
//        String password = "iutcse18";
//        String url = "jdbc:oracle:thin:@localhost:1521/XE";
//        String query = "SELECT USERNAME,ELIGIBILITY FROM ORG_LIC_INFO WHERE USERNAME=?";
//
//        try{
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            Connection con = DriverManager.getConnection(url,username,password);
//            PreparedStatement pst = con.prepareStatement(query);
//            pst.setString(1, LogINpanelController.getUser());
//            //System.out.println(LogINpanelController.getUser());
//
//            ResultSet rs = pst.executeQuery();
//
////            eligibility.setText(rs.getString(2));
////            System.out.println(rs.getString(2));
////            activeness.setText(rs.getString(3));
////            System.out.println(rs.getString(3));
//
//            while(rs.next())
//            {
//                eligibility.setText(rs.getString(2));
//                //System.out.println(rs.getString(2));
//            }
//
//            pst.close();
//            rs.close();
//            con.close();
//
//        } catch (Exception e)
//        {
////           System.out.println(e);
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error Dialog");
//            alert.setHeaderText("Look, an Error Dialog");
//            alert.setContentText(String.valueOf(e));
//            System.out.println(e);
//            alert.setResizable(false);
//            alert.showAndWait();
//        }
//
//    }

    /**
     * This method is here for the functionality of the "Back" button. This will redirect the
     * user to the parent node that is the "root" which is sample.UserPanel.fxml. This hierarchical
     * structure is for the simplicity of the system.
     *
     * @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrganizationPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This method is for the functionality of the Org Info button. This loads the sample.OrgLicInfo.fxml
     * file and presents the user with the relevant information and the component.
     *
     * @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void pressOrgInfo(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrgLicInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This method is for the button that enables an Organization to see the pending reqgesusts with
     * loading the sample.PendingRequest.fxml file. This is the same feature that was implemneted
     * into the Donor Class.
     *
     * @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException, which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */

    public void pressPendingRequest(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PendingRequest.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void initialize()
    {

    }
}
