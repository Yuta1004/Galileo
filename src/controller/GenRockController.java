package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

import db.Log;

public class GenRockController implements Initializable {

    // UI
    @FXML
    private Button done;
    @FXML
    private CheckBox enableAir;
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
        enableAir.setOnAction(event -> {
            diameterTf.setEditable(enableAir.isSelected());
            diameterTf.setDisable(!enableAir.isSelected());
        });
        done.setOnAction(event -> {
            validateInput();
            done.getScene().getWindow().hide();
        });
    }

    /**
     * 正当な入力が行われたか返す
     */
    public boolean inputOK() {
        return inpOk;
    }

    /**
     * 空気抵抗チェックが有効か返す
     */
    public boolean enableAirRes() {
        return enableAir.isSelected();
    }

    /**
     * 入力検証
     */
    private void validateInput() {
        inpOk = (x = toDouble(xTf.getText())) != Double.MIN_VALUE
             && (y = toDouble(yTf.getText())) != Double.MIN_VALUE
             && (v0 = toDouble(v0Tf.getText())) != Double.MIN_VALUE
             && (theta = toDouble(thetaTf.getText())) != Double.MIN_VALUE
             && (!enableAir.isSelected() || (diameter = toDouble(diameterTf.getText())) != Double.MIN_VALUE);
    }

    /**
     * String to double
     */
    private double toDouble(String numStr) {
        try {
            return Double.parseDouble(numStr);
        } catch (Exception e) {
            Log.add("[WARN] Corrupt number (modified to 0.0) => %s", numStr);
            return 0.0;
        }
    }

}
