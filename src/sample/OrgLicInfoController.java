package sample;

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
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is for the Licence information of the Organization .
 * Extends the LogINpanelController Class.
 *
 * @author minhaz231
 */
public class OrgLicInfoController extends LogINpanelController{
    File f,fp;
    FileInputStream fin;
    String path;
    List<String> extFile = new ArrayList<>();

    @FXML
    private TextField lic;
    @FXML
    private Label lic_status;
    @FXML
    private Label lic_approval;
    @FXML
    private Label checked_by;
    @FXML
    private TextArea comments;
    @FXML
    private Label checkingDate;

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
        extFile.add("*.jpg");
        extFile.add("*.png");
        extFile.add("*.gif");
        extFile.add("*.tiff");
        extFile.add("*.bmp");
        extFile.add("*.jpeg");

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query1 = "SELECT LIC_NAME, LIC_PATH, DOC, L_STATUS, APPROVAL, CHECKER, COMMENTS FROM ORG_LIC_INFO WHERE USERNAME=?";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);

            PreparedStatement pst1 = con.prepareStatement(query1);

            pst1.setString(1, LogINpanelController.getUser());

            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next())
            {
                lic.setText(rs1.getString(1));
                path = rs1.getString(2);
                if(path!=null) f = new File(path);
                if(rs1.getDate(3)!=null) checkingDate.setText(rs1.getDate(3).toString());
                else checkingDate.setText(null);
                lic_status.setText(rs1.getString(4));
                lic_approval.setText(rs1.getString(5));
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
     * @param even, object of ActionEvent generated from click or Enter
     * @throws IOException,  checked exception
     */
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrganizationDonorPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * This is for the button to browsw the Licene files form the Image file.
     * This is the way they will be able to uplaod the licence file into the
     * system and select it.
     *
     * @param actionEvent object of ActionEvent.
     * @throws Exception
     */
    public void pressBrowse(ActionEvent actionEvent) throws Exception{
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", extFile));
        fp = fc.showOpenDialog(null);
        //fin = new FileInputStream(f.getAbsolutePath());
        //System.out.println(f.getName());

        if(fc!=null && fp!=null)
        {
            lic.setText(fp.getName());
            fin = new FileInputStream(fp.getAbsolutePath());
        }
    }

    /**
     * This method is for the save button which is pressed after the image file is
     * uploaded. This will then be overwritten on the database
     * .
     * @param even
     * object of action event
     * @throws IOException
     * checked exceeption related to i/p and o/p
     */
    private void doSave(ActionEvent even) throws IOException
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query1 = "UPDATE ORG_LIC_INFO SET LIC=?,LIC_NAME=?,DOS=?, ELIGIBILITY='Ineligible' WHERE USERNAME=?";
        String query2 = "CALL UPDATELICREPORTS(?)";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);

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
            Parent root = FXMLLoader.load(getClass().getResource("OrganizationDonorPanel.fxml"));
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
     * This method is for the save button which is pressed after the image file is
     * uploaded. This will then be overwritten on the database. This loads the
     * sample.OrganizationDonorPanel.fxml file and shows the saved licence. A recursive
     * call is there for a buttonTypeYes.
     * .
     * @param even
     * object of action event
     * @throws IOException
     * checked exceeption related to i/p and o/p
     */
    public void pressSave(ActionEvent even) throws IOException {
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
            Parent root = FXMLLoader.load(getClass().getResource("OrganizationDonorPanel.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
}
