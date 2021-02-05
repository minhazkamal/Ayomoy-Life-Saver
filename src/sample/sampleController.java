package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the Sample Controller for the sample.fxml file.
 * Implements the Initialize Class. The root of the whole hierarchical structure.
 *
 * @author minhaz231
 */
public class sampleController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView imView;

    @FXML
    private Button button;

    @FXML

    /**
     * This is the initializing function of the fxml components.
     *
     * When a the window is launched this method initializes these
     * components. Establishes the connection with the database and
     * upon fetching the data columns works with the property values.
     *
     */
    public void initialize(){
        imView.setImage(
                new Image("images/cover.jpg")
        );

//        imView.setImage(
//                new Image("images/cover.jpg")
//        );
//        root.heightProperty().addListener(
//                h -> imView.setFitHeight(
//                        root.getHeight()
//                )
//        );
//        root.widthProperty().addListener(
//                h -> imView.setFitWidth(
//                        root.getWidth()
//                )
//        );
    }

    @FXML
    /**
     * This is for the button press to continue on the first page.
     * This loads the sample.LogINpanel.fxml file and prompts user
     * to log in or register.
     */
    public void pressToContinue(ActionEvent even) throws IOException
    {
        //System.out.println("Hello World!!!");
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    @Override
    /**
     * The Overridden initialize method which is empty.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This button is the about us button pressing which loads the AboutUs.fxml file
     * and shows the credits of the developers.
     * @param even, object of ActionEvent Class
     * @throws IOException, Checked exception for i/p and o/p.
     */
    public void pressCredit(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AboutUs.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
