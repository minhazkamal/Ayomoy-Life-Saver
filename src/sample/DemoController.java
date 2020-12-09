package sample;

import javafx.scene.control.TextArea;

public class DemoController{
    public TextArea txt;

    public void initialize()
    {
        txt.setText(Main.text);
    }

}
