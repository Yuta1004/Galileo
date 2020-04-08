package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

import java.util.ResourceBundle;
import java.net.URL;

import db.Settings;

public class AdvancedSettingsController implements Initializable {

    @FXML
    private CheckBox viewRatioNormalize;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 表示比正規化
        viewRatioNormalize.setOnAction(event -> {
            Settings.ViewRatioNormalize = viewRatioNormalize.isSelected();
        });
    }

}