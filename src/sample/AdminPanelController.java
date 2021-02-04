package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This Controller Class is regarding the Admin Panel that controls the sample.AdminPanel.fxml
 *
 * @author minhaz231
 */

public class AdminPanelController extends LogINpanelController {

    /**
     * This methods is the functionality of the Back button that changes the stage to
     * the parent node.
     *
     * @param even
     * that is the ActionEvent Object
     *
     * @throws IOException
     * Related to the i/p and o/p of the flow
     */
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogINPanel.fxml"));
        primaryStage.setTitle("Ayomoy LifeSaver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This is the Button for changing the password of the Admin.
     *
     * The ChangePassword fxml here is the root and upon changing successfully,
     * it goes to the Parent root automatically.
     *
     * @param even
     * which is the ActionEvent Object
     *
     * @throws IOException
     * Related to the i/p and o/p of the flow
     */
    public void pressChangePass(ActionEvent even) throws IOException {

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
    }

    /**
     * This is the functionality of the button which shows the Admin the donors list.
     *
     * Here the parent node is the sample.DonorApprovalList.fxml from where the admin
     * can look at all the donors details and eligibility.
     *
     * @param even
     * which is the ActionEvent Object
     *
     * @throws IOException
     * Related to the i/p and o/p of the flow
     */
    public void pressDonorList(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonorApprovalList.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This is the functionality for the Organization lis button. Pressing this button the
     * Admin will be able to see the registered organization list.
     *
     * The parent node here is the sample.OrgApprovalList.fxml. From here the Admin looks
     * at all the lists.
     *
     * @param even
     * which is the ActionEvent Object
     *
     * @throws IOException
     * Related to the i/p and o/p of the flow
     */
    public void pressOrgList(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrgApprovalList.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
