package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    // UI部品
    @FXML
    private Label titleLabel;

    /**
     * 初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 3秒待機 -> ウィンドウを閉じる
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            Platform.runLater(() -> {
                Stage stage = (Stage)titleLabel.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            });
        }).start();
    }

}