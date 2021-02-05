package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

/**
 * This is the Controller class for handling the Previous Donation
 * requests of a Done.
 * Extends the LogINpanelController Class.
 *
 * @author abdullah239
 */
public class DoneePrevReqController extends LogINpanelController{

    public int count_req;
    public Button []d_button;
    public Button []s_button;
    public static String bg=null;

    /**
     * Method for getting the Donee Blood Group.
     * @return String, blood group of the Donee.
     */
    public static String getDoneeBg() {
        return bg;
    }

    /**
     * Methid for setting the Blood Group of Donee
     * @param bg, the Blood group of the Donee.
     */
    public static void setDoneeBg(String bg) {
        DoneePrevReqController.bg = bg;
    }

    @FXML
    private TextField txtUser;
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
    private TableColumn<Request, Button> col_delete;
    @FXML
    private TableColumn<Request, Button> col_search;

    ObservableList<Request> oblist = FXCollections.observableArrayList();

    /**
     * This method is here for the functionality of the "Back" button. This will redirect the
     * user to the parent node that is the "root" which is sample.UserPanel.fxml. This hierarchical
     * structure is for the simplicity of the system.
     *
     * @param even, object of ActionEvent generated from click or Enter
     * @throws IOException,  checked exception
     */

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DoneePanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Method for counting the number of Requests in the
     * database for a particular username.
     */
    public void countRequest(){
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String count_query = "SELECT COUNT(*) FROM REQUEST WHERE USERNAME=?";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst1 = con.prepareStatement(count_query);
            pst1.setString(1, txtUser.getText());
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                count_req = rs1.getInt(1);
                //System.out.println(count_req);
            }
            rs1.close();
            con.close();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    /**
     * Method for fetching the Donatio Reqeust ID from the
     * Database.
     *
     * @param r_num, serial number of a Donation Request.
     * @return int, request ID of a Donation Request
     */

    public int getReqID(int r_num)
    {
        int r_id=0;
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT findDeleteReqID(?, ?) FROM DUAL";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtUser.getText());
            pst.setInt(2, r_num);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                r_id = rs.getInt(1);
                //System.out.println(count_req);
            }
            rs.close();
            con.close();

            return r_id;
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
        return r_id;
    }

    /**
     * Methd for deleting a Donation request in case the Donee
     * has already found the blood.
     *
     * @param even, object of ActionEvent Class
     */
    private void pressDeleteButton(ActionEvent even)
    {
        int r_id=0;
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == d_button[i])
            {
                r_id = Integer.parseInt(oblist.get(i).getReq_id());
                break;
            }
        }
        //System.out.println(Integer.toString(r_id) +" delete it");
        //Alert box and delete operation need to be added
        Alert alert_confirm = new Alert(Alert.AlertType.CONFIRMATION);
        alert_confirm.setTitle("Record Delete Confirmation");
        alert_confirm.setHeaderText("Are you sure want to delete the record of Request ID: "+Integer.toString(r_id)+"?");
        alert_confirm.setResizable(false);
        //alert.setContentText("Are you sure want to delete the record of Request ID: "+Integer.toString(r_id)+"?");

        ButtonType buttonTypeYES = new ButtonType("YES");
        ButtonType buttonTypeNO = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert_confirm.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);

        Optional<ButtonType> result = alert_confirm.showAndWait();

        if(result.get()==buttonTypeYES)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "DELETE FROM REQUEST WHERE USERNAME=? AND ID=?";
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, txtUser.getText());
                pst.setInt(2, r_id);

                pst.executeUpdate();

                con.close();

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
            catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText(String.valueOf(e));
                alert.setResizable(false);
                alert.showAndWait();
            }
        }
    }

    /**
     * Method for searching the Donation Requests of a Donee.
     *
     * @param even, object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p.
     */

    private void pressSearchButton(ActionEvent even) throws IOException {
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == s_button[i])
            {
//                System.out.print(i+1);
//                System.out.println(" search button is pressed");
                bg = oblist.get(i).getBg();
//                System.out.println(bg);
//                System.out.println(i);
//                System.out.println(count_req);
                break;
            }
        }

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
    /**
     * This is the initializing function of the fxml components.
     *
     * When a the window is launched this method initializes these
     * components. Establishes the connection with the database and
     * upon fetching the data columns works with the property values.
     *
     */
    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());
        countRequest();
        d_button = new Button[count_req];
        s_button = new Button[count_req];

        for(int i=0; i<count_req; i++)
        {
            d_button[i] = new Button();
            d_button[i].setOnAction(this::pressDeleteButton);

            s_button[i] = new Button();
            s_button[i].setOnAction(even -> {
                try {
                    pressSearchButton(even);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, ID, PATIENT, LOCATION, BLOOD_GROUP, QUANTITY, APPROX_DATE FROM REQUEST WHERE USERNAME=? ORDER BY APPROX_DATE DESC, ID DESC";
//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtUser.getText());
            ResultSet rs = pst.executeQuery();


            int k = 1;
            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                //int j = rs.getInt(1);
                int j = k;
                //System.out.println(rs.getString(2));
                oblist.add(new Request(Integer.toString(j), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7).toString(), d_button[j-1], s_button[j-1]));

                k++;
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
            alert.setResizable(false);
            alert.showAndWait();
        }

        col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_request.setCellValueFactory(new PropertyValueFactory<>("req_id"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_bg.setCellValueFactory(new PropertyValueFactory<>("bg"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_delete.setCellValueFactory(new PropertyValueFactory<>("delete_button"));
        col_search.setCellValueFactory(new PropertyValueFactory<>("search_button"));

        prevReqTable.setItems(oblist);
    }


}
