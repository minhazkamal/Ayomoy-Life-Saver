package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DoneePrevReqController {
    @FXML
    private TableView<Request> prevReqTable;
    @FXML
    private TableColumn<Request, String> col_serial;
    @FXML
    private TableColumn<Request, String> col_name;
    @FXML
    private TableColumn<Request, String> col_request;
    @FXML
    private TableColumn<Request, String> col_location;
    @FXML
    private TableColumn<Request, String> col_bg;
    @FXML
    public TableColumn<Request, String> col_quantity;
    @FXML
    private TableColumn<Request, String> col_date;
    @FXML
    private TableColumn<Request, Button> col_update;
    @FXML
    private TableColumn<Request, Button> col_search;

    ObservableList<Request> oblist = FXCollections.observableArrayList();

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
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, ID, NAME, LOCATION, BLOOD_GROUP, QUANTITY, APPROX_DATE FROM NEW_DONEE";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            Statement pst = con.createStatement();

            ResultSet rs = pst.executeQuery(query);

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                oblist.add(new Request(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7).toString()));
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

            alert.showAndWait();
        }
        col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_request.setCellValueFactory(new PropertyValueFactory<>("req_id"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_bg.setCellValueFactory(new PropertyValueFactory<>("bg"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("up_button"));
        col_search.setCellValueFactory(new PropertyValueFactory<>("search_button"));

        prevReqTable.setItems(oblist);
    }


}
