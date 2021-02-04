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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class PendingRequestController extends DonorPanelController {

    public int count_req;
    public Button[]dt_button;
    public static String bg;
    private static String r_id;

    public static String getBg() {
        return bg;
    }

    private String dob;
    public boolean filter = false;

    public static String getR_id() {
        return r_id;
    }

    @FXML
    private ChoiceBox filterBG;
    @FXML
    private ChoiceBox filterLocation;
    @FXML
    private DatePicker startingDate;
    @FXML
    private DatePicker endingDate;



    @FXML
    private TextField txtUser;
    @FXML
    private TableView<Request> RequestTable;
    @FXML
    private TableColumn<Request, String> col_serial;
    @FXML
    private TableColumn<Request, String> col_req;
    @FXML
    private TableColumn<Request, String> col_name;
    @FXML
    private TableColumn<Request, String> col_BG;
    @FXML
    private TableColumn<Request, String> col_location;
    @FXML
    private TableColumn<Request, String> col_date;
    @FXML
    private TableColumn<Request, String> col_contact;
    @FXML
    private TableColumn<Request, Button> col_details;

    ObservableList<Request> oblist = FXCollections.observableArrayList();

    public void countRequestandBG(){
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT BLOOD_GROUP, DOB FROM PERSONAL_INFO WHERE USERNAME=?";
        String count_query = "SELECT COUNT(*) FROM REQUEST WHERE APPROX_DATE>=?";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst1 = con.prepareStatement(count_query);
            pst1.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                count_req = rs1.getInt(1);
                //System.out.println(count_req);
            }
            rs1.close();

            PreparedStatement pst2 = con.prepareStatement(query);
            pst2.setString(1, DonorPanelController.getUser());
            txtUser.setText(DonorPanelController.getUser());
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()) {
                bg = rs2.getString(1);
                dob = rs2.getDate(2).toLocalDate().toString();
                //System.out.println(count_req);
            }
            rs2.close();
            con.close();
        }
        catch(Exception e){
            System.out.println(1);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    private void pressDetailsButton(ActionEvent even) throws IOException
    {
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == dt_button[i])
            {
                r_id = oblist.get(i).getReq_id();
                //System.out.println(r_id);
                break;
            }
        }

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ShowDetailRequestInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressBack(ActionEvent even) throws IOException {
        if(LogINpanelController.getType().equals("Person"))
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("DonorPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else if(LogINpanelController.getType().equals("Organization"))
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("OrganizationDonorPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    public void pressReset(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PendingRequest.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressFilter(ActionEvent event) throws IOException{

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FilterRequest.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

        if(FilterRequestController.flag==true)
        {
            //System.out.println(FilterRequestController.getCountf()+" "+FilterRequestController.getStd()+" "+FilterRequestController.getEnd_d()+" "+FilterRequestController.getLoct()+" "+FilterRequestController.getBg());
            oblist.clear();
            RequestTable.setItems(oblist);

            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "SELECT ROWNUM, ID, PATIENT, BLOOD_GROUP, LOCATION, APPROX_DATE, EM_MOBILE FROM REQUEST WHERE APPROX_DATE>=? AND APPROX_DATE<=? AND BLOOD_GROUP LIKE ? AND LOCATION LIKE ? ORDER BY APPROX_DATE ASC";
//        Statement pst = null;
//        ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setDate(1, Date.valueOf(FilterRequestController.getStd()));
                pst.setDate(2, Date.valueOf(FilterRequestController.getEnd_d()));
                pst.setString(3, FilterRequestController.getBg());
                pst.setString(4, FilterRequestController.getLoct());

                ResultSet rs = pst.executeQuery();

                count_req = FilterRequestController.getCountf();

                dt_button = new Button[FilterRequestController.getCountf()];

                for(int i=0; i<FilterRequestController.getCountf(); i++)
                {
                    dt_button[i] = new Button();
                    dt_button[i].setOnAction(even -> {
                        try {
                            pressDetailsButton(even);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                int k = 1;
                while(rs.next())
                {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                    //int j = rs.getInt(1);
                    int j = k;
                    //System.out.println(j);
                    oblist.add(new Request(Integer.toString(j), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toString(), rs.getString(7), dt_button[j-1]));

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
            col_req.setCellValueFactory(new PropertyValueFactory<>("req_id"));
            col_BG.setCellValueFactory(new PropertyValueFactory<>("bg"));
            col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
            col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));


            RequestTable.setItems(oblist);
        }
    }

    public void initialize()
    {
            countRequestandBG();

            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query;
            if(LogINpanelController.getType().equals("Organization"))
                query = "SELECT ROWNUM, ID, PATIENT, BLOOD_GROUP, LOCATION, APPROX_DATE, EM_MOBILE FROM REQUEST WHERE BLOOD_GROUP LIKE ? AND APPROX_DATE>=? ORDER BY APPROX_DATE ASC";
            else
                query = "SELECT ROWNUM, ID, PATIENT, BLOOD_GROUP, LOCATION, APPROX_DATE, EM_MOBILE FROM REQUEST WHERE BLOOD_GROUP=? AND APPROX_DATE>=? ORDER BY APPROX_DATE ASC";
//        Statement pst = null;
//        ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                if(LogINpanelController.getType().equals("Organization")==false)
                    pst.setString(1, bg);
                else
                    pst.setString(1, "%");

                pst.setDate(2, Date.valueOf(LocalDate.now()));
                ResultSet rs = pst.executeQuery();

                dt_button = new Button[count_req];

                for(int i=0; i<count_req; i++)
                {
                    dt_button[i] = new Button();
                    dt_button[i].setOnAction(even -> {
                        try {
                            pressDetailsButton(even);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                int k = 1;
                while(rs.next())
                {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                    //int j = rs.getInt(1);
                    int j = k;
                    //System.out.println(j);
                    //System.out.println(count_req-j+1);
                    oblist.add(new Request(Integer.toString(j), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toString(), rs.getString(7), dt_button[j-1]));

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
            col_req.setCellValueFactory(new PropertyValueFactory<>("req_id"));
            col_BG.setCellValueFactory(new PropertyValueFactory<>("bg"));
            col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
            col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));

            RequestTable.setItems(oblist);
    }

}
