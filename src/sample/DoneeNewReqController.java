package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DoneeNewReqController {
    ObservableList<String> BGList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    @FXML
    private TextField doneeLocation;
    @FXML
    private ChoiceBox BG_choice;
    @FXML
    private TextField mobile;
    @FXML
    private TextField name;
    @FXML
    private TextField complications;
    @FXML
    private TextField quantity;
    @FXML
    private DatePicker date;
    @FXML
    private TextField emContact;
    @FXML
    private TextField emMobile;
////    @FXML
////    private Button submit;

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DoneePanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public void initialize()
    {
        BG_choice.setItems(BGList);
    }

   public void submitNewReq(ActionEvent even) {
        System.out.println("Name: "+name.getText());
        System.out.println("Mobile: "+mobile.getText());
        System.out.println("Location: "+ doneeLocation.getText());
        System.out.println("Complications: "+complications.getText());
        System.out.println("Blood Group: "+BG_choice.getValue().toString());
        System.out.println("Quantity: "+quantity.getText()+" Bag(s)");
        System.out.println("Approximate Date: "+date.getValue());
        System.out.println("Emergency Contact Name: "+emContact.getText());
        System.out.println("Emergency Mobile: "+emMobile.getText());
    }
}
