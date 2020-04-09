package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;

import db.Log;

public class LogViewController implements Initializable {

    // ログ管理用
    private Thread fetchThread;
    private ObservableList<String> logData;

    // UI部品
    @FXML
    private CheckBox showAlwaysTop;
    @FXML
    private ListView<String> logList;

    /**
     * ウィンドウ初期化
     *
     * @param location URL
     * @param resource リソースデータ
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 更新スレッド初期化
        fetchThread = new Thread(() -> {
            while(true) {
                if(Log.checkUpdate()) {
                    Platform.runLater(() -> logData.addAll(Log.fetch()));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        });
        fetchThread.start();

        // UI初期化
        logData = FXCollections.observableArrayList("");
        logList.setItems(logData);
        showAlwaysTop.setOnAction(event -> {
            Stage stage = (Stage)showAlwaysTop.getScene().getWindow();
            stage.setAlwaysOnTop(
                showAlwaysTop.isSelected()
            );
        });

        // 終了イベント
        logList.sceneProperty().addListener((obsS, oldS, newS) ->
            newS.windowProperty().addListener((obsW, oldW, newW) ->
                newW.setOnCloseRequest(event -> fetchThread.stop())
            )
        );
    }
}