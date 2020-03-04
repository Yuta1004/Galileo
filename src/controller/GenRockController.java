package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GenRockController implements Initializable {

    // UI
    @FXML
    private Button done;
    @FXML
    private TextField xTf, yTf, v0Tf, thetaTf, diameterTf;

    // 状態管理
    private boolean inpOk;
    public double x, y, v0, theta, diameter;

    /**
     * 初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        done.setOnAction(event -> {
            validateInput();
            System.out.println(inpOk);
        });
    }

    /**
     * 入力検証
     */
    private void validateInput() {
        inpOk = (x = toDouble(xTf.getText())) != Double.MIN_VALUE
             && (y = toDouble(yTf.getText())) != Double.MIN_VALUE
             && (v0 = toDouble(v0Tf.getText())) != Double.MIN_VALUE
             && (theta = toDouble(thetaTf.getText())) != Double.MIN_VALUE;
    }

    /**
     * String to double
     */
    private double toDouble(String numStr) {
        try {
            return Double.parseDouble(numStr);
        } catch (Exception e) {
            return Double.MIN_VALUE;
        }
    }

}
