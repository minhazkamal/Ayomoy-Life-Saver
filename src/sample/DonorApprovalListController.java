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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * This is the Controller Class for handling the Donor Approval list.
 * The admin will approve the Donor and they will be placed on the approvol list.
 *
 * Extends the LogINpanelController Class.
 * @author minhaz231
 */
public class DonorApprovalListController extends LogINpanelController{

    static String user_img=null;

    public static String getUser_img() {
        return user_img;
    }

    @FXML
    private TextField txtUser;
    @FXML
    private TableView<UpdateByAdmin> DonorApprovalTable;
    @FXML
    private TableColumn<UpdateByAdmin, String> col_serial;
    @FXML
    private TableColumn<UpdateByAdmin, String> col_username;
    @FXML
    private TableColumn<UpdateByAdmin, String> col_date;
    @FXML
    private TableColumn<UpdateByAdmin, ChoiceBox> col_stat;
    @FXML
    private TableColumn<UpdateByAdmin, ChoiceBox> col_approval;
    @FXML
    private TableColumn<UpdateByAdmin, String> col_comment;
    @FXML
    private TableColumn<UpdateByAdmin, Button> col_report;
    @FXML
    private TableColumn<UpdateByAdmin, Button> col_update;

    ObservableList<UpdateByAdmin> oblist = FXCollections.observableArrayList();
    ObservableList<String> Aprroval = FXCollections.observableArrayList(null, "Approved", "Rejected");
    ObservableList<String> Status = FXCollections.observableArrayList(null, "Valid", "Invalid");
    private Button[] report_button;
    private Button[] update_button;
    private ChoiceBox[] approval;
    private ChoiceBox[] status;

    private int count_report;

    /**
     * Method for checing the valid age.
     *
     * @param DOB, date of birth of the user.
     * @return true if above 18 yrs, else return false.
     */
    public Boolean CheckAge(LocalDate DOB)
    {
        LocalDate current = LocalDate.now();

        Period period = Period.between(DOB, current);

        if(period.getYears()>=18) return true;
        else return false;
    }

    /**
     * Method for checing whether all the Flags are set apropiately
     * or not.
     *
     * @param s, status flag
     * @param a, approved flag
     * @param c, eligible flag
     * @return true of alert is there else return false.
     */
    private boolean check_alert(String s, String a, String c)
    {
        boolean alert_check=false;
        if(s.equals("Select One")||a.equals("Select One"))
        {
            alert_check = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Status/Approval should be selected");
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(s.equals("Invalid")&&a.equals("Approved"))
        {
            alert_check = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Status and Approval can't be contraditory");
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(c==null)
        {
            alert_check = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Comment on reports should be added");
            alert.setResizable(false);
            alert.showAndWait();
        }
        return alert_check;
    }

    /**
     * Methof for checking the update of the Approval form the admin.
     *
     * @param s
     * @param a
     * @return
     */
    private boolean updateCheck(String s, String a)
    {
        if(s.equals("Valid") && a.equals("Approved")) return true;
        else return false;
    }

    /**
     * Method for the admin to update the approval into the
     * database. This will be then displayed on the Donors Profile
     * when the user logs in.
     *
     * @param even, object of ActionEvent Class
     * @throws IOException, checked exeption for i/p and o/p.
     */
    private void pressUpdateButton(ActionEvent even) throws IOException {
        String user=null;
        int serial=-1;
        for(int i=0; i<count_report; i++)
        {
            if(even.getSource() == update_button[i])
            {
                //System.out.print(i+1);
                //System.out.println(" update button is pressed");
                user = oblist.get(i).getUsername();
                serial=i;
                break;
            }
        }

//        System.out.println(oblist.get(serial).getComment());
//        System.out.println(oblist.get(serial).getApproval_choice().getValue().toString());
//        System.out.println(oblist.get(serial).getStatus_choice().getValue().toString());
        if(check_alert(oblist.get(serial).getStatus_choice().getValue().toString(), oblist.get(serial).getApproval_choice().getValue().toString(),
                oblist.get(serial).getComment())==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "UPDATE TEST_REPORTS SET R_STATUS=?, APPROVAL=?, CHECKER=?, DOC=?, COMMENTS=? WHERE USERNAME=?";
            String update_query = "UPDATE DONOR_INFO SET ELIGIBILITY='Eligible' WHERE USERNAME=?";
            String dob_query = "SELECT DOB, USERNAME FROM PERSONAL_INFO WHERE USERNAME=?";
//        Statement pst = null;
//        ResultSet rs = null;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, oblist.get(serial).getStatus_choice().getValue().toString());
                pst.setString(2, oblist.get(serial).getApproval_choice().getValue().toString());
                pst.setString(3, LogINpanelController.getUser());
                pst.setDate(4, Date.valueOf(LocalDate.now()));
                pst.setString(5, oblist.get(serial).getComment());
                pst.setString(6, user);

                pst.executeUpdate();

                PreparedStatement pst2 = con.prepareStatement(dob_query);
                pst2.setString(1, user);

                ResultSet rs2 = pst2.executeQuery();

                LocalDate dob=null;

                while(rs2.next())
                {
                    dob = rs2.getDate(1).toLocalDate();
                    //System.out.println(dob);
                }

                pst2.close();
                rs2.close();

                if(updateCheck(oblist.get(serial).getStatus_choice().getValue().toString(), oblist.get(serial).getApproval_choice().getValue().toString()) && CheckAge(dob))
                {
                    //System.out.println("Yahooo");
                    PreparedStatement pst1 = con.prepareStatement(update_query);
                    pst1.setString(1, user);

                    pst1.executeUpdate();
                    //System.out.println("Yahooo");

                    pst1.close();
                }


                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Profile Updated Successfully");
                alert.setResizable(false);
                alert.showAndWait();

                pressRefresh(even);

            } catch (Exception e)
            {
//            System.out.println(1);
                System.out.println(e);
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
     * This button is for the Admin to see the test report of the User.
     * The method uses sample.Image.fxml to show the
     * @param even, object of ActionEvent Class
     * @throws IOException, Checked exception for i/p and o/p.
     */
    private void pressReportButton(ActionEvent even) throws IOException {

        for(int i=0; i<count_report; i++)
        {
            if(even.getSource() == report_button[i])
            {
                //System.out.print(i+1);
                //System.out.println(" report button is pressed");
                user_img = oblist.get(i).getUsername();
                //System.out.println(user_img);
                break;
            }
        }
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("ImageShow.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

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
        DonorApprovalTable.setEditable(true);
        txtUser.setText(LogINpanelController.getUser());
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, TEST_REPORTS.*, DONOR_INFO.ELIGIBILITY FROM TEST_REPORTS, DONOR_INFO " +
                "WHERE TEST_REPORTS.USERNAME=DONOR_INFO.USERNAME AND ELIGIBILITY='Ineligible' AND DOS IS NOT NULL ORDER BY CHECKER DESC, DOS ASC";
        String count_query = "SELECT COUNT(*) FROM TEST_REPORTS";
//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            PreparedStatement pst1 = con.prepareStatement(count_query);

            ResultSet rs1 = pst1.executeQuery();
            while(rs1.next())
            {
                count_report = rs1.getInt(1);
            }

            rs1.close();

            ResultSet rs = pst.executeQuery();

            report_button = new Button[count_report];
            update_button = new Button[count_report];
            approval = new ChoiceBox[count_report];
            status = new ChoiceBox[count_report];

            for(int i=0; i<count_report; i++)
            {
                report_button[i] = new Button();
                update_button[i] = new Button();
               approval[i] = new ChoiceBox();
                status[i] = new ChoiceBox();

                report_button[i].setOnAction(even -> {
                    try {
                        pressReportButton(even);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                update_button[i].setOnAction(even -> {
                    try {
                        pressUpdateButton(even);
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
                oblist.add(new UpdateByAdmin(rs.getString(1), rs.getString(2), rs.getDate(6).toString(), status[j-1], approval[j-1], rs.getString(11), report_button[j-1], update_button[j-1]));

            }
            rs.close();
            con.close();



        } catch (Exception e)
        {
//            System.out.println(1);
           System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

        col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        col_approval.setCellValueFactory(new PropertyValueFactory<>("approval_choice"));

        col_stat.setCellValueFactory(new PropertyValueFactory<>("status_choice"));

        col_date.setCellValueFactory(new PropertyValueFactory<>("DOS"));
        col_report.setCellValueFactory(new PropertyValueFactory<>("report_button"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("update_button"));


        col_comment.setCellFactory(TextFieldTableCell.forTableColumn());
//        col_comment.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UpdateByAdmin, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<UpdateByAdmin, String> event) {
//                System.out.println("dsfsdfsdf");
//                UpdateByAdmin record1 = event.getRowValue();
//                System.out.println(event.getNewValue());
//                record1.setComment(event.getNewValue());
//            }
//        });

        DonorApprovalTable.setItems(oblist);
    }

    /**
     * This is the method for refresh button in the apporval list. Refreshing
     * the list will show the new changes made that were not shown in a
     * particular session.
     *
     * @param even, object of ActionEvent Class
     * @throws IOException, Checked excetption for i/p and o/p.
     */
    public void pressRefresh(ActionEvent even) throws IOException {
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
        Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * Method for the comment button of the admin. The admin will be able
     * to place a comment as remark and pressing this button will show the
     * comment to the user later in the approval list.
     *
     * @param event, object of ActionEvent
     */
    public void pressComment(TableColumn.CellEditEvent<UpdateByAdmin, String> event) {
        //System.out.println("adsafdsfdsga");
        UpdateByAdmin record1 = DonorApprovalTable.getSelectionModel().getSelectedItem();
        //System.out.println(event.getNewValue());
        record1.setComment(event.getNewValue());

    }
}
