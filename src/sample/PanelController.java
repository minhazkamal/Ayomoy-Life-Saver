package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PanelController {
    @FXML
    private Label title_label;

    @FXML
    public void pressBack(ActionEvent even) throws IOException {

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage(); 
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public void pressDonor(ActionEvent even) throws IOException {
    }

    public void pressDonee(ActionEvent even) throws IOException {
    }

    public void pressOrganization(ActionEvent even) throws IOException {
    }
}
