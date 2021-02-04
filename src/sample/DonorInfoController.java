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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is a controller class for sample.DonorInfo.fxml
 *
 * @author minhaz231
 */
public class DonorInfoController extends LogINpanelController{

    File f,fp;
    FileInputStream fin;
    String path;
    List<String> extFile = new ArrayList<>();
    ObservableList<String> Activeness = FXCollections.observableArrayList("Active", "Inactive");
    ObservableList<String> Payable = FXCollections.observableArrayList("Paid", "NON-Paid");
    /**
     * FXML related Variables used to fetch i/p from the UI
     */
    @FXML
    private TextArea comments;
    @FXML
    private Label checkingDate;
    @FXML
    private ChoiceBox paying;
    @FXML
    private ChoiceBox active;
    @FXML
    private TextField test_report;
    @FXML
    private Label report_status;
    @FXML
    private Label report_approval;
    @FXML
    private Label checked_by;

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
        paying.setItems(Payable);
        active.setItems(Activeness);
        extFile.add("*.jpg");
        extFile.add("*.png");
        extFile.add("*.gif");
        extFile.add("*.tiff");
        extFile.add("*.bmp");
        extFile.add("*.jpeg");

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ACTIVENESS, PAYABLE FROM DONOR_INFO WHERE USERNAME=?";
        String query1 = "SELECT REPORT_NAME, REPORT_PATH, DOC, R_STATUS, APPROVAL, CHECKER, COMMENTS FROM TEST_REPORTS WHERE USERNAME=?";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, LogINpanelController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                active.setValue(rs.getString(1));
                paying.setValue(rs.getString(2));
            }

            rs.close();

            PreparedStatement pst1 = con.prepareStatement(query1);

            pst1.setString(1, LogINpanelController.getUser());

            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next())
            {
                test_report.setText(rs1.getString(1));
                path = rs1.getString(2);
                if(path!=null) f = new File(path);
                if(rs1.getDate(3)!=null) checkingDate.setText(rs1.getDate(3).toString());
                else checkingDate.setText(null);
                report_status.setText(rs1.getString(4));
                report_approval.setText(rs1.getString(5));
                checked_by.setText(rs1.getString(6));
                comments.setText(rs1.getString(7));
            }
            con.close();
        } catch (Exception e)
        {
           //System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

    }
    /**
     * This method is here for the functionality of the "Back" button. This will redirect the
     * user to the parent node that is the "root" which is sample.UserPanel.fxml. This hierarchical
     * structure is for the simplicity of the system.
     *
     * @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonorPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This is for the bowsing the profiles of the Donor
     * @param even which is an object of ActionEvent Class
     * @throws FileNotFoundException, when the File path is n
     * not incorrect or file not present.
     */
    public void pressBrowse(ActionEvent even) throws FileNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", extFile));
        fp = fc.showOpenDialog(null);
        //fin = new FileInputStream(f.getAbsolutePath());
        //System.out.println(f.getName());

        if(fc!=null && fp!=null)
        {
            test_report.setText(fp.getName());
            fin = new FileInputStream(fp.getAbsolutePath());
        }
    }

    /**
     * This is for the functionality of the Saving of Donor test reports.
     *
     * The test reports will be provided in form of image files.
     *
     *  @param even which is an object of ActionEvent Class
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    private void doSave(ActionEvent even) throws IOException
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "UPDATE DONOR_INFO SET ACTIVENESS=?, PAYABLE=? WHERE USERNAME=?";
        String query1 = "UPDATE TEST_REPORTS SET REPORT=?,REPORT_NAME=?,DOS=? WHERE USERNAME=?";
        String query2 = "CALL UPDATETESTREPORTS(?)";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, active.getValue().toString());
            pst.setString(2, paying.getValue().toString());
            pst.setString(3, LogINpanelController.getUser());

            pst.executeUpdate();
            pst.close();

            PreparedStatement pst1 = con.prepareStatement(query1);

//            if(fin!=null) pst1.setBinaryStream(1, fin, fin.available());
//            else pst1.setBinaryStream(1, null);
            if(fin!=null)
            {
                pst1.setBinaryStream(1, fin, fin.available());
                if(fp!=null) pst1.setString(2, fp.getName());
                else if(f!=null) pst1.setString(2, f.getName());
                else pst1.setString(2,null);
                pst1.setDate(3, Date.valueOf(LocalDate.now()));
                pst1.setString(4, LogINpanelController.getUser());

                pst1.executeUpdate();
                pst1.close();

                CallableStatement pst2 = con.prepareCall(query2);
                pst2.setString(1, LogINpanelController.getUser());
                pst2.executeUpdate();

                pst2.close();
            }

            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Information Successfully Updated");
            alert.setResizable(false);
            alert.showAndWait();

            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("DonorPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();

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
    }

    /**
     * This is for the confirmation of the submission of the test reports.
     *
     * This confirms that the previous report file has been overwritten in the database.
     * @param even even which is an object of ActionEvent Class
     * @throws IOException which is a checked exception.
     * Used to identify errors in i/p and o/p of a
     */
    public void pressSave(ActionEvent even) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conirmation Submission");
        alert.setHeaderText("If you save this, all of your previous data will be overwritten");
        alert.setContentText("Do you want to continue?");
        alert.setResizable(false);
        //alert.showAndWait();

        ButtonType buttonTypeYES = new ButtonType("YES");
        ButtonType buttonTypeNO = new ButtonType("NO");

        alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()==buttonTypeYES)
        {
            doSave(even);
        }
        else if(result.get()==buttonTypeNO)
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("DonorPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
}
