package sample;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutUsController {
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

//    public static void openWebpage(String url) {
//        try {
//            new ProcessBuilder("x-www-browser", url).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

    public void abdullahFB(ActionEvent actionEvent) {
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
