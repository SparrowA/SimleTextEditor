package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.IfNode;

import java.io.*;
import java.util.*;

public class Main extends Application {

    private static Stage mPrimaryStage;

    private static List<FileChooser.ExtensionFilter> mExteFilter;

    private static File mCurentFile;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Text Editor");
        primaryStage.setScene(new Scene(root, 300, 275));

        mPrimaryStage = primaryStage;

        mExteFilter = new ArrayList<>();
        mExteFilter.add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        mExteFilter.add(new FileChooser.ExtensionFilter("TextFile", "*.txt"));


        primaryStage.show();
    }

    public static String OpenFile(String text){
        if(mCurentFile != null && IsSaveFile()){
            SaveFile(text);
        }

        StringBuilder mText = new StringBuilder();

        File tempFile = getFile("Open file", false);

        if(tempFile != null){
            mCurentFile = tempFile;
            mPrimaryStage.setTitle(mCurentFile.getName());
            try {
                Scanner scanner = new Scanner(mCurentFile);
                while (scanner.hasNextLine()){
                    mText.append(scanner.nextLine());
                    mText.append("<br>");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return mText.toString();
    }

    public static void NewFile(){
        File tempFile = getFile("New file", true);

        if(tempFile != null){
            mCurentFile = tempFile;
            mPrimaryStage.setTitle(mCurentFile.getName());
        }
    }

    public static void SaveFile(String text){
        File tempFile = getFile("Save file", true);

        if(tempFile != null){
            mCurentFile = tempFile;
            mPrimaryStage.setTitle(mCurentFile.getName());
            try {
                (new FileWriter(mCurentFile)).append(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean IsSaveFile(){
        Alert mSaveDialog = new Alert(Alert.AlertType.CONFIRMATION);
        mSaveDialog.setTitle("Save file?");
        mSaveDialog.setHeaderText("Current File has changed.");
        mSaveDialog.setContentText("Save current file?");

        Optional<ButtonType> optional =  mSaveDialog.showAndWait();

        return optional.get() == ButtonType.OK;
    }

    private static File getFile(String title, boolean save){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("New file");
        fileChooser.getExtensionFilters().addAll(mExteFilter);

        return save ? fileChooser.showSaveDialog(mPrimaryStage) : fileChooser.showOpenDialog(mPrimaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
