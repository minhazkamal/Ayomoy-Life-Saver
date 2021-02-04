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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/**
 * This Class works with the Approval of the Organization by the Admin.
 * The Class extends the LogINpanelController Class.
 * @author fairuz240
 */

public class OrgApprovalListController extends LogINpanelController {
    static String user_img=null;
    public static String user_type=null;

    /** Getter function of the User type, static method.
     *
     * @return String, type of the User.
     */
    public static String getUser_type() {
        return user_type;
    }

    /**
     * Setter Function of the user type. Static Method.
     * @param user_type, the user type of the User.
     */
    public static void setUser_type(String user_type) {
        OrgApprovalListController.user_type = user_type;
    }
    /**
     * Getter Function of the User Image.
     * This gest te User Image from the Property values of the Class.
     *
     * @return String, the User Image related to the respective the Approval
     */
    public static String getUser_img() {
        return user_img;
    }

    @FXML
    private TextField txtUser;
    @FXML
    private TableView<UpdateByAdmin> OrgApprovalTable;
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
     * This is the alert in case user forgets to Select the Status flags
     * from the dropdown boxes. This prompt the User to again go for the selection
     * of flags.
     *
     * @param s, flag 1
     * @param a, flag 2
     * @param c, flag 3
     * @return boolean, if any alert found then true and false otherwise.
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
     * Method for updating the Check window in the Org. Approval List.
     *
     * @param s, Flag of validity
     * @param a, Flag of Approvals
     * @return boolean, True if the flags have positive values and
     * false if otherwise.
     */
    private boolean updateCheck(String s, String a)
    {
        if(s.equals("Valid") && a.equals("Approved")) return true;
        else return false;
    }

    /**
     * Method for the button to update the Approval for the Admin.
     * @param even, object of ActionEvent generated from click or Enter
     * @throws IOException, checked exception
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
            String query = "UPDATE ORG_LIC_INFO SET L_STATUS=?, APPROVAL=?, CHECKER=?, DOC=?, COMMENTS=? WHERE USERNAME=?";
            String update_query = "UPDATE ORG_LIC_INFO SET ELIGIBILITY='Eligible' WHERE USERNAME=?";
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

                if(updateCheck(oblist.get(serial).getStatus_choice().getValue().toString(), oblist.get(serial).getApproval_choice().getValue().toString()))
                {
                    //System.out.println("Yahooo");
                    PreparedStatement pst1 = con.prepareStatement(update_query);
                    pst1.setString(1, user);

                    pst1.executeUpdate();
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
                //System.out.println(e);
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
     * Method for the functionality of the Report Button. 
     *
     * @param even, object of ActionEvent generated from click or Enter.
     * @throws IOException, checked exception.
     */
    private void pressReportButton(ActionEvent even) throws IOException {

        for(int i=0; i<count_report; i++)
        {
            if(even.getSource() == report_button[i])
            {
                //System.out.print(i+1);
                //System.out.println(" report button is pressed");
                user_img = oblist.get(i).getUsername();
                user_type = "Organization";
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

    public void initialize()
    {
        OrgApprovalTable.setEditable(true);
        txtUser.setText(LogINpanelController.getUser());
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, ORG_LIC_INFO.* FROM ORG_LIC_INFO WHERE ELIGIBILITY='Ineligible' AND DOS IS NOT NULL ORDER BY CHECKER DESC, DOS ASC";
        String count_query = "SELECT COUNT(*) FROM ORG_LIC_INFO";
//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con1 = DriverManager.getConnection(url, username, password);
            PreparedStatement pst1 = con1.prepareStatement(count_query);

            ResultSet rs1 = pst1.executeQuery();
            while(rs1.next())
            {
                //System.out.println(2);
                count_report = rs1.getInt(1);
                //System.out.println(count_report);
            }

            rs1.close();
            pst1.close();
            con1.close();

            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            //System.out.println(pst.execute());
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
                //System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                int j = rs.getInt(1);
                //System.out.println(j);
                oblist.add(new UpdateByAdmin(rs.getString(1), rs.getString(2), rs.getDate(7).toString(), status[j-1], approval[j-1], rs.getString(12), report_button[j-1], update_button[j-1]));

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

        OrgApprovalTable.setItems(oblist);
    }

    public void pressRefresh(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrgApprovalList.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

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

    public void pressComment(TableColumn.CellEditEvent<UpdateByAdmin, String> event) {
        UpdateByAdmin record1 = OrgApprovalTable.getSelectionModel().getSelectedItem();
        //System.out.println(event.getNewValue());
        record1.setComment(event.getNewValue());
    }
}
