package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

import java.util.ResourceBundle;
import java.net.URL;

import db.Log;
import db.Settings;
import util.Util;

public class SettingsController implements Initializable {

    // UI部品
    @FXML
    private Label rockMagValueV;
    @FXML
    private Slider rockMagValue;
    @FXML
    private CheckBox viewRatioNormalize, axisNormalize;
    @FXML
    private TextField stepVal;
    @FXML
    private ChoiceBox cdChoice;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 初期化
        rockMagValue.setValue(Settings.RockMagnification);
        rockMagValueV.setText("x "+Settings.RockMagnification);
        viewRatioNormalize.setSelected(Settings.ViewRatioNormalize);
        axisNormalize.setSelected(Settings.AxisNormalize);
        stepVal.setText(""+Settings.StepVal);
        cdChoice.getItems().addAll(
            resource.getString("Air")
        );
        cdChoice.setValue(resource.getString("Air"));

        // 噴石拡大表示
        rockMagValue.valueProperty().addListener((obs, oldVal, newVal) -> {
            double val = Math.round(newVal.doubleValue()*10)/10.0;
            rockMagValueV.setText("x "+val);
            Settings.RockMagnification = val;
        });
        // 表示比正規化
        viewRatioNormalize.setOnAction(event -> {
            if(!Settings.AxisNormalize)
                Settings.ViewRatioNormalize = viewRatioNormalize.isSelected();
            else
                viewRatioNormalize.setSelected(true);
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
            Settings.StepVal = Util.parseDouble(newVal, 0.0);
        });
    }
}