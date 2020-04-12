package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Hashtable;
import java.util.Collections;

import db.Log;
import db.Settings;
import util.Util;
import util.Fluid;

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
    private ChoiceBox visChoice;

    // 抵抗係数管理
    private Hashtable<String, Fluid> visPreset;
    private Hashtable<Fluid, String> rvisPreset;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 初期化(UI部品)
        initVisPreset(resource);
        stepVal.setText(""+Settings.StepVal);
        rockMagValue.setValue(Settings.RockMagnification);
        rockMagValueV.setText("x "+Settings.RockMagnification);
        axisNormalize.setSelected(Settings.AxisNormalize);
        viewRatioNormalize.setSelected(Settings.ViewRatioNormalize);
        visChoice.getItems().addAll(Collections.list(visPreset.keys()));
        visChoice.setValue(rvisPreset.get(Settings.FluidID));

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
        // 抵抗係数
        visChoice.setOnAction(event -> {
            Settings.FluidID = visPreset.get(visChoice.getValue());
        });
    }

    /**
     * 粘度係数プリセットリストを初期化する
     *
     * @param resource ResourceBundle
     */
    private void initVisPreset(ResourceBundle resource) {
        visPreset = new Hashtable<String, Fluid>();
        visPreset.put(resource.getString("Air"), Fluid.AIR);
        visPreset.put(resource.getString("Water"), Fluid.WATER);
        visPreset.put(resource.getString("Mayo"), Fluid.MAYO);

        rvisPreset = new Hashtable<Fluid, String>();
        rvisPreset.put(Fluid.AIR, resource.getString("Air"));
        rvisPreset.put(Fluid.WATER, resource.getString("Water"));
        rvisPreset.put(Fluid.MAYO, resource.getString("Mayo"));
    }
}