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
import java.time.LocalDate;

/**
 * This controller class is for searching the Organization.
 *
 * @author abdullah239
 */
public class SearchOrgController {

    public static int count_org;
    public static String user;

    /**
     * Getter method of the Username.
     * @return String, the username.
     */
    public static String getUser() {
        return user;
    }

    ObservableList<Organization> oblist = FXCollections.observableArrayList();
    ObservableList<String> LocList = FXCollections.observableArrayList();
    private Button[] dt_button;

    @FXML
    private ChoiceBox loc;
    @FXML
    private TableView<Organization> OrgTable;
    @FXML
    private TableColumn<Organization, String> col_serial;
    @FXML
    private TableColumn<Organization, String> col_name;
    @FXML
    private TableColumn<Organization, String> col_location;
    @FXML
    private TableColumn<Organization, String> col_contact;
    @FXML
    private TableColumn<Organization, String> col_cpmobile;
    @FXML
    private TableColumn<Organization, String> col_email;
    @FXML
    private TableColumn<Organization, String> col_details;

    /**
     * Method to fetch the Location details from the database.
     * Fetched lists are sorted alphabetically.
     */
    public void getLoc()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT NAME FROM LOCATIONS ORDER BY NAME";

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
                LocList.add(rs.getString(1));
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
    }

    /**
     * Method for counting the NUmber of the Organizations that are enlisted.
     * Uses simple sql quiery with count() function.
     */
    public void countOrg()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String count_query = "SELECT COUNT(*) FROM ORG_INFO";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst1 = con.prepareStatement(count_query);
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                count_org = rs1.getInt(1);
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
     * This is the initializing function of the fxml components.
     *
     * When a the window is launched this method initializes these
     * components. Establishes the connection with the database and
     * upon fetching the data columns works with the property values.
     *
     */
    public void initialize()
    {
        getLoc();
        loc.setItems(LocList);

        countOrg();
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, ORG_INFO.*, ELIGIBILITY FROM ORG_INFO, ORG_LIC_INFO WHERE ORG_INFO.USERNAME=ORG_LIC_INFO.USERNAME AND ELIGIBILITY='Eligible'";
//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            dt_button = new Button[count_org];

            for(int i=0; i<count_org; i++)
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
                oblist.add(new Organization(rs.getString(2), /*Integer.toString(count_req-j+1)*/Integer.toString(j), rs.getString(3), rs.getString(6), rs.getString(4), rs.getString(9), rs.getString(5), dt_button[j-1]));

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
        col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
        col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_cpmobile.setCellValueFactory(new PropertyValueFactory<>("cpmobile"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));


       OrgTable.setItems(oblist);
    }

    /**
     * method for the details button of the Org. Pressing this laods the sample.ShowDetailOrgInfo.fxml
     * file and presents the profile info of the Org.
     *
     * @param even, object of ActionEvent Class.
     * @throws IOException, checked exception for i/p and o/p.
     */

    private void pressDetailsButton(ActionEvent even) throws IOException{
        for(int i=0; i<count_org; i++)
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
        Parent root;
        root = FXMLLoader.load(getClass().getResource("ShowDetailOrgInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * Method for sending SQL query to the database and fetch a list of Orgs.
     * Catch block catches the exceptions and shows them into the warning window.
     * @param event, object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p.
     */
    public void pressSearch(ActionEvent event) throws IOException{
        oblist.clear();
        OrgTable.setItems(oblist);

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, ORG_INFO.*, ELIGIBILITY FROM ORG_INFO, ORG_LIC_INFO WHERE ORG_INFO.USERNAME=ORG_LIC_INFO.USERNAME AND ELIGIBILITY='Eligible' AND LOCATION LIKE ?";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            if(loc.getValue()==null) pst.setString(1, "%");
            else pst.setString(1, loc.getValue().toString());

            dt_button = new Button[count_org];

            for(int i=0; i<count_org; i++)
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

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                int j = rs.getInt(1);
                //System.out.println(j);
                oblist.add(new Organization(rs.getString(2), /*Integer.toString(count_req-j+1)*/Integer.toString(j), rs.getString(3), rs.getString(6), rs.getString(4), rs.getString(9), rs.getString(5), dt_button[j-1]));
            }
            rs.close();
            con.close();

        } catch (Exception e)
        {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

        col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
        col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_cpmobile.setCellValueFactory(new PropertyValueFactory<>("cpmobile"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));


        OrgTable.setItems(oblist);

    }
    /**
     * The method for the reset button on the Org. list page. Rests all the
     * filters and shows the list based on the sorted order. uses sample.SearchOrg.fxml
     *
     * @param even, object of ActionEvent Class
     * @throws IOException, checked Exception for i/p and o/p
     */
    public void pressReset(ActionEvent even) throws IOException {
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
        Parent root;
        root = FXMLLoader.load(getClass().getResource("SearchDonor-Org.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
