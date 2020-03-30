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
     * LogViewControllerのコンストラクタ
     */
    public LogViewController() {
        logCnt = 0;
        logData = FXCollections.observableArrayList(genLogStr("Start logging..."));
    }

    /**
     * ウィンドウ初期化
     *
     * @param location URL
     * @param resource リソースデータ
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        logList.setItems(logData);
    }

    /**
     * ログ追加
     *
     * @param msg ログデータ
     */
    public void addLogMsg(String msg) {
        logData.add(genLogStr(msg));
        if(logList != null)
            logList.setItems(logData);
    }

    /**
     * ログ表示用にフォーマットされたStringを返す
     *
     * @param msg 表示するメッセージ
     */
    private String genLogStr(String msg) {
        return (logCnt++) + ": " + msg + "\n";
    }

}