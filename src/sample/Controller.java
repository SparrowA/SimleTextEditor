package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;

public class Controller {

    @FXML
    private HTMLEditor mTextArea;

    @FXML
    public void onNewFileClick(){
        Main.NewFile();
    }

    @FXML
    public void onOpenFile(){
        String text = Main.OpenFile(mTextArea.getHtmlText());

        if (text.length() > 0){
            mTextArea.setHtmlText(text);
        }
    }

    @FXML
    public void onSaveFile(){
        Main.SaveFile(mTextArea.getHtmlText());
    }

}
