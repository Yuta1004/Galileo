package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;

public class LogViewController implements Initializable {

    // ログ保持用
    private int logCnt;
    private ObservableList<String> logData;

    // UI部品
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
        // ログ表示初期化
        logCnt = 0;
        logData = FXCollections.observableArrayList();
        logList.setItems(logData);
    }

}