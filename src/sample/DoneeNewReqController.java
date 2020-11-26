package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

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
//        System.out.println("Name: "+name.getText());
//        System.out.println("Mobile: "+mobile.getText());
//        System.out.println("Location: "+ doneeLocation.getText());
//        System.out.println("Complications: "+complications.getText());
//        System.out.println("Blood Group: "+BG_choice.getValue().toString());
//        System.out.println("Quantity: "+quantity.getText()+" Bag(s)");
//        System.out.println("Approximate Date: "+date.getValue());
//        System.out.println("Emergency Contact Name: "+emContact.getText());
//        System.out.println("Emergency Mobile: "+emMobile.getText());
       String username = "als";
       String password = "iutcse18";
       String url = "jdbc:oracle:thin:@localhost:1521/XE";
       String query = "INSERT INTO NEW_DONEE (NAME, MOBILE, BLOOD_GROUP, LOCATION, COMPLICATIONS, QUANTITY, APPROX_DATE, EM_PERSON, EM_MOBILE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

       PreparedStatement pst = null;
       ResultSet rs = null;
       try{
           Class.forName("oracle.jdbc.driver.OracleDriver");
           Connection con = DriverManager.getConnection(url,username,password);

           pst = con.prepareStatement(query);
           pst.setString(1, name.getText());
           pst.setString(2, mobile.getText());
           pst.setString(3, BG_choice.getValue().toString());
           pst.setString(4, doneeLocation.getText());
           pst.setString(5, complications.getText());
           pst.setInt(6, Integer.parseInt(quantity.getText()));
           pst.setDate(7, Date.valueOf(date.getValue()));
           pst.setString(8, emContact.getText());
           pst.setString(9, emMobile.getText());

           pst.executeUpdate();

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Request Confirmation");
           alert.setHeaderText(null);
           alert.setContentText("Request Successfully Received");

           alert.showAndWait();

       } catch (Exception e)
       {
//           System.out.println(e);
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error Dialog");
           alert.setHeaderText("Look, an Error Dialog");
           alert.setContentText(String.valueOf(e));

           alert.showAndWait();
       }

    }
}
