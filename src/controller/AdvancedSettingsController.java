package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;
import java.net.URL;

import db.Log;
import db.Settings;

public class AdvancedSettingsController implements Initializable {

    // UI部品
    @FXML
    private Label rockMagValueV;
    @FXML
    private Slider rockMagValue;
    @FXML
    private CheckBox viewRatioNormalize, axisNormalize;
    @FXML
    private TextField stepVal;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 初期化
        rockMagValue.setValue(Settings.RockMagnification);
        rockMagValueV.setText("x "+Settings.RockMagnification);
        viewRatioNormalize.setSelected(Settings.ViewRatioNormalize);
        axisNormalize.setSelected(Settings.AxisNormalize);
        stepVal.setText(""+Settings.StepVal);

        // 噴石拡大表示
        rockMagValue.valueProperty().addListener((obs, oldVal, newVal) -> {
            double val = Math.round(newVal.doubleValue()*10)/10.0;
            rockMagValueV.setText("x "+val);
            Settings.RockMagnification = val;
        });
        // 表示比正規化
        viewRatioNormalize.setOnAction(event -> {
            Settings.ViewRatioNormalize = viewRatioNormalize.isSelected();
        });
        // 軸目盛り間隔正規化
        axisNormalize.setOnAction(event -> {
            boolean state = axisNormalize.isSelected();
            Settings.AxisNormalize = state;
            Settings.ViewRatioNormalize = state;
            viewRatioNormalize.setSelected(state);
        });
        // ステップ値
        stepVal.textProperty().addListener((obs, oldVal, newVal) -> {
            Settings.StepVal = parseDouble(newVal);
        });
    }

    /**
     * String 2 Double
     * ただし、パースに失敗した場合は0.0を返す
     *
     * @param strVal パースする文字列
     * @return Double
     */
    private double parseDouble(String strVal) {
        try {
            return Double.parseDouble(strVal);
        } catch (Exception e) {
            Log.add("[WARN] Corrupt number (modified to 0.0) => %s", strVal);
            return 0.0;
        }
    }

}