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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DonorApprovalListController extends LogINpanelController{

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

    private void pressUpdateButton(ActionEvent even) throws IOException {
        for(int i=0; i<count_report; i++)
        {
            if(even.getSource() == update_button[i])
            {
                System.out.print(i+1);
                System.out.println(" update button is pressed");
                break;
            }
        }
    }

    private void pressReportButton(ActionEvent even) throws IOException {
        for(int i=0; i<count_report; i++)
        {
            if(even.getSource() == report_button[i])
            {
                System.out.print(i+1);
                System.out.println(" report button is pressed");
                break;
            }
        }
    }

    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, TEST_REPORTS.* FROM TEST_REPORTS WHERE DOS IS NOT NULL ORDER BY CHECKER DESC, DOS ASC";
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

        DonorApprovalTable.setItems(oblist);
    }

    public void pressRefresh(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonorApprovalList.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
