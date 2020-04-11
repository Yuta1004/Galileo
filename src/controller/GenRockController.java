package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import db.Log;
import util.Util;

public class GenRockController implements Initializable {

    // UI
    @FXML
    private Button done;
    @FXML
    private CheckBox enableAir;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField xTf, yTf, v0Tf, thetaTf, diameterTf, idTf;

    // 状態管理
    private boolean inpOk;
    public String id, color;
    public double x, y, v0, theta, diameter;

    // その他
    private Color colors[] = { Color.CORNFLOWERBLUE, Color.BROWN }; // 空気抵抗 0: 無, 1: 有

    /**
     * 初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        colorPicker.setValue(Color.BROWN);
        enableAir.setOnAction(event -> {
            boolean state = enableAir.isSelected();
            diameterTf.setEditable(state);
            diameterTf.setDisable(state);
            colorPicker.setValue(colors[state?1:0]);
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
        // id, 色
        id = idTf.getText().equals("") ? "Rock"+(int)(Math.random()*100) : idTf.getText();
        color = colorPicker.getValue().toString();

        // その他属性
        inpOk = (x = Util.parseDouble(xTf.getText(), Double.MIN_VALUE)) != Double.MIN_VALUE
             && (y = Util.parseDouble(yTf.getText(), Double.MIN_VALUE)) != Double.MIN_VALUE
             && (v0 = Util.parseDouble(v0Tf.getText(), Double.MIN_VALUE)) != Double.MIN_VALUE
             && (theta = Util.parseDouble(thetaTf.getText(), Double.MIN_VALUE)) != Double.MIN_VALUE
             &&
                (!enableAir.isSelected() ||
                (diameter = Util.parseDouble(diameterTf.getText(), Double.MIN_VALUE)) != Double.MIN_VALUE);
    }
}
