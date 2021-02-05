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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/**
 * This is for the controller class for Searching of the Donor and the Organizations
 * for a person a Person type User. Extends the DonorPanelController Class.
 *
 * @author minhaz231
 */
public class SearchDonorOrgController extends DonorPanelController {
    public int count_req;
    public static String user;
    public boolean back_flag = false;
    public String d_bg;

    /**
     * Getter function for the User name.
     * @return
     */
    public static String getUser() {
        return user;
    }

    @FXML
    private TableView<Donor> DonorTable;
    @FXML
    private TableColumn<Donor, String> col_serial;
    @FXML
    private TableColumn<Donor, String> col_name;
    @FXML
    private TableColumn<Donor, String> col_BG;
    @FXML
    private TableColumn<Donor, String> col_location;
    @FXML
    private TableColumn<Donor, String> col_contact;
    @FXML
    private TableColumn<Donor, String> col_pay;
    @FXML
    private TableColumn<Donor, Button> col_details;

    ObservableList<Donor> oblist = FXCollections.observableArrayList();
    private Button[] dt_button;

    /**
     * The method for the reset button on the donor list page. Rests all the
     * filters and shows the list based on the sorted order.
     * @param even, object of ActionEvent Class
     * @throws IOException, checked Exception for i/p and o/p
     */
    public void pressReset(ActionEvent even) throws IOException{

        ((Node) even.getSource()).getScene().getWindow().hide();

        if(back_flag == true) DoneePrevReqController.setDoneeBg(d_bg);

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
     * This is the method for the button to load the filters by the help of sample.FilterDonation.fxml file.
     * This shows a page where the user can choose the filters and mpose them on the list.
     * @param event object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p.
     */
    public void pressFilter(ActionEvent event) throws IOException{

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FilterDonation.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

        if(FilterDonationController.flag==true)
        {
            //System.out.println(FilterRequestController.getCountf()+" "+FilterRequestController.getStd()+" "+FilterRequestController.getEnd_d()+" "+FilterRequestController.getLoct()+" "+FilterRequestController.getBg());
            oblist.clear();
            DonorTable.setItems(oblist);

            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "SELECT ROWNUM, DONOR_INFO.*, PERSONAL_INFO.* FROM DONOR_INFO, PERSONAL_INFO WHERE DONOR_INFO.USERNAME=PERSONAL_INFO.USERNAME AND ELIGIBILITY='Eligible' AND ACTIVENESS='Active' AND BLOOD_GROUP LIKE ? AND LOCATION LIKE ? AND PAYABLE LIKE ?";
//        Statement pst = null;
//        ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, FilterDonationController.getBg());
                pst.setString(2, FilterDonationController.getLoct());
                pst.setString(3, FilterDonationController.getPay());

                ResultSet rs = pst.executeQuery();

                count_req = FilterDonationController.getCountf();

                dt_button = new Button[FilterDonationController.getCountf()];

                //System.out.println(FilterDonationController.getCountf());

                for(int i=0; i<FilterDonationController.getCountf(); i++)
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

                while(rs.next())
                {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                    int j = rs.getInt(1);
                    //System.out.println(rs.getString(2));
                    oblist.add(new Donor(rs.getString(2), /*Integer.toString(count_req-j+1)*/Integer.toString(j), rs.getString(7), rs.getString(11), rs.getString(13), rs.getString(8), rs.getString(5), dt_button[j-1]));
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
            col_BG.setCellValueFactory(new PropertyValueFactory<>("bg"));
            col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
            col_pay.setCellValueFactory(new PropertyValueFactory<>("paying_condition"));
            col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
            col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));


            DonorTable.setItems(oblist);
        }
    }

    /**
     * The method for searching the Organization. Pressing this button loads the sample.SearchOrg.fxml
     * file and shows the enlisted organizations.
     * @param even, object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p.
     */
    public void PressSearchOrg(ActionEvent even) throws IOException{
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

    /**
     * Method for counting the number of Donor. This also takes into account the activity's and
     * eligibility status of the Donor.
     */
    public void countDonor(){
        if(DoneePrevReqController.getDoneeBg()==null)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String count_query = "SELECT COUNT(*) FROM DONOR_INFO WHERE ELIGIBILITY=? AND ACTIVENESS=?";
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst1 = con.prepareStatement(count_query);
                pst1.setString(1, "Eligible");
                pst1.setString(2, "Active");
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
        else
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String count_query = "SELECT COUNT(*) FROM (SELECT * FROM PERSONAL_INFO, DONOR_INFO WHERE PERSONAL_INFO.USERNAME=DONOR_INFO.USERNAME AND ELIGIBILITY=? AND ACTIVENESS=? AND BLOOD_GROUP=?)";
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst1 = con.prepareStatement(count_query);
                pst1.setString(1, "Eligible");
                pst1.setString(2, "Active");
                pst1.setString(3, DoneePrevReqController.getDoneeBg());
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

    }
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

        if(back_flag == false)
        {
            Stage primaryStage = new Stage();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("DoneePanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else
        {
            Stage primaryStage = new Stage();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("DoneePrevReq.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }

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
        if(DoneePrevReqController.getDoneeBg()==null)
        {
            countDonor();
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "SELECT ROWNUM, DONOR_INFO.*, PERSONAL_INFO.* FROM DONOR_INFO, PERSONAL_INFO WHERE DONOR_INFO.USERNAME=PERSONAL_INFO.USERNAME AND ELIGIBILITY=? AND ACTIVENESS=?";
//        Statement pst = null;
//        ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, "Eligible");
                pst.setString(2, "Active");
                ResultSet rs = pst.executeQuery();

                dt_button = new Button[count_req];

                for(int i=0; i<count_req; i++)
                {
                    dt_button[i] = new Button();
                    dt_button[i].setOnAction(even -> {
                        try {
                            pressDetailsButton(even);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                while(rs.next())
                {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                    int j = rs.getInt(1);
                    //System.out.println(j);
                    oblist.add(new Donor(rs.getString(2), /*Integer.toString(count_req-j+1)*/Integer.toString(j), rs.getString(7), rs.getString(11), rs.getString(13), rs.getString(8), rs.getString(5), dt_button[j-1]));

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

            col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_BG.setCellValueFactory(new PropertyValueFactory<>("bg"));
            col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
            col_pay.setCellValueFactory(new PropertyValueFactory<>("paying_condition"));
            col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
            col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));


            DonorTable.setItems(oblist);
        }
        else
        {
            countDonor();
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "SELECT ROWNUM, DONOR_INFO.*, PERSONAL_INFO.* FROM DONOR_INFO, PERSONAL_INFO WHERE DONOR_INFO.USERNAME=PERSONAL_INFO.USERNAME AND ELIGIBILITY=? AND ACTIVENESS=? AND BLOOD_GROUP=?";
//        Statement pst = null;
//        ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, "Eligible");
                pst.setString(2, "Active");
                pst.setString(3, DoneePrevReqController.getDoneeBg());
                ResultSet rs = pst.executeQuery();

                dt_button = new Button[count_req];

                for(int i=0; i<count_req; i++)
                {
                    dt_button[i] = new Button();
                    dt_button[i].setOnAction(even -> {
                        try {
                            pressDetailsButton(even);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                while(rs.next())
                {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                    int j = rs.getInt(1);
                    //System.out.println(j);
                    oblist.add(new Donor(rs.getString(2), /*Integer.toString(count_req-j+1)*/Integer.toString(j), rs.getString(7), rs.getString(11), rs.getString(13), rs.getString(8), rs.getString(5), dt_button[j-1]));

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

            col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_BG.setCellValueFactory(new PropertyValueFactory<>("bg"));
            col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
            col_pay.setCellValueFactory(new PropertyValueFactory<>("paying_condition"));
            col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
            col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));


            DonorTable.setItems(oblist);

            back_flag = true;
            d_bg = DoneePrevReqController.getDoneeBg();
            DoneePrevReqController.setDoneeBg(null);
        }

    }

    /**
     * method for the details button of the Donor. Pressing this laods the sample.ShowDetailDonorInfo.fxml
     * file and presents the profile info of the Donor.
     *
     * @param even, object of ActionEvent Class.
     * @throws IOException, checked exception for i/p and o/p.
     */
    private void pressDetailsButton(ActionEvent even) throws IOException {
//        System.out.println(count_req);
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == dt_button[i])
            {
                user = oblist.get(i).getUsername();
                //System.out.println(user);
                break;
            }
        }
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ShowDetailDonorInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}



