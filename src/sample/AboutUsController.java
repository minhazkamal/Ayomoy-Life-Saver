package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


/**
 * This is the Controller Class that handles the sample.AboutUs.fxml
 * Here the Developers information are given with attached link of their contact information.
 * The purpose of this is to learn the how professionally a system acknowledges the developers.
 *
 * @author abdullah239
 */
public class AboutUsController {
    /**
     * This method is here for the functionality of the "Back" button. This will redirect the user to the
     * Parent that is the "root" which is sample.fxml
     *
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     *
     */

    public void pressBack() throws IOException {
        pressBack();
    }

    /**
     * This method is here for the functionality of the "Back" button. This will redirect the user to the
     * Parent that is the "root" which is sample.fxml
     *
     * @param even
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * This is a method for the button labeled as Facebook under the developer named Minhaz Kamal
     * Here the facebook profile link is embedded and upon clicking this button the user will be redirected to the
     * facebook profile page of Minhaz Kamal by using the default browser in the particular machine.
     *
     * @param actionEvent
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */
    public void minhazFB(ActionEvent actionEvent) throws IOException {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "http://www.facebook.com/minhaz.kamal9900";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    /**
     * This is a method for the button labeled as Facebook under the developer named Chowdhury Mohammad Abdullah
     * Here the facebook profile link is embedded and upon clicking this button the user will be redirected to the
     * facebook profile page of Chowdhury Mohammad Abdullah by using the default browser in the particular machine.
     *
     * @param actionEvent
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */

    public void abdullahFB(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://www.facebook.com/profile.php?id=100015252342356";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void minhazGIT(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://github.com/minhazkamal";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void fairuzGIT(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://github.com/fairuz-shaiara";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void abdullahGIT(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://github.com/intricate-potato";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void minhazLin(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://www.linkedin.com/in/minhazkamal";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void fairuzLin(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://www.linkedin.com/in/fairuz-shaiara-1195861b1";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void abdullahLin(ActionEvent actionEvent) {
        try{
//            getHostServices().showDocument("http://www.google.com");
            Runtime rt = Runtime.getRuntime();
            String url = "https://www.linkedin.com/in/chowdhury-mohammad-abdullah-a48473188";
            rt.exec("rundll32 url.dll,FileProtocolHandler "+url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
