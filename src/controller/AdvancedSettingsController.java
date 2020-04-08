package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.util.ResourceBundle;
import java.net.URL;

import db.Settings;

public class AdvancedSettingsController implements Initializable {

    // UI部品
    @FXML
    private Slider rockMagValue;
    @FXML
    private CheckBox viewRatioNormalize;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 噴石拡大表示
        rockMagValue.valueProperty().addListener((obs, oldVal, newVal) -> {
            Settings.RockMagnification = newVal.doubleValue();
            System.out.println(Settings.RockMagnification);
        });
        // 表示比正規化
        viewRatioNormalize.setOnAction(event -> {
            Settings.ViewRatioNormalize = viewRatioNormalize.isSelected();
        });
    }

}