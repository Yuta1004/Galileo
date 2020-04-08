package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;

import java.util.ResourceBundle;
import java.net.URL;

import db.Settings;

public class AdvancedSettingsController implements Initializable {

    // UI部品
    @FXML
    private Label rockMagValueV;
    @FXML
    private Slider rockMagValue;
    @FXML
    private CheckBox viewRatioNormalize;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
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
    }

}