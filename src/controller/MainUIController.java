package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import db.Log;
import db.Settings;
import simulator.RockManager;
import util.Util;

public class MainUIController implements Initializable {

    // UI部品
    @FXML
    private AnchorPane chartPane;
    @FXML
    private Slider speed;
    @FXML
    private Label speedVal, clock;
    @FXML
    private Button play, skip, skip10, init, reset, genRock, openSettings;
    @FXML
    private TextField widthF, widthT, heightF, heightT;
    @FXML
    private MenuItem openLog, openCredit;

    // 描画用
    private ScatterChart chart;
    private double updateSpeed;
    private double widthFVal, widthTVal, heightFVal, heightTVal;
    private RockManager rockManager;
    private Timeline tl = new Timeline();

    // その他管理用
    private ResourceBundle resource;

    /**
     * 初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // スプラッシュ表示
        Stage splash = Util.genStage("", "/fxml/Credit.fxml", new SplashController(), resource);
        splash.getScene().setFill(Color.TRANSPARENT);
        splash.initStyle(StageStyle.TRANSPARENT);
        splash.showAndWait();

        // 初期化
        this.resource = resource;
        updateSpeed = 1.0;
        widthFVal = heightFVal = 0;
        widthTVal = 1500;
        heightTVal = 725;
        initUI();
        initChart(false);
        rockManager = new RockManager();
        setData(rockManager.getChartData());
    }

    /**
     * UI初期化
     */
    private void initUI() {
        // UIイベント<ボタン>
        init.setOnAction(event -> {
            tl.stop();
            initChart(false);
            play.setText("▷");
            clock.setText(rockManager.clock+"");
            rockManager = new RockManager();
            Log.add("Initialize simulation => ViewScale: %.1f, ScaleNormalize: %s",
                    Settings.RockMagnification, Settings.ViewRatioNormalize);
        });
        reset.setOnAction(event -> {
            tl.stop();
            initChart(false);
            rockManager.reset();
            play.setText("▷");
            clock.setText(rockManager.clock+"");
            setData(rockManager.getChartData());
            Log.add("Reset simulation");
        });
        play.setOnAction(event -> {
            if(tl.getStatus().equals(Animation.Status.RUNNING)) {
                play.setText("▷");
                tl.stop();
            } else {
                play.setText("□");
                initTimeLine();
            }
        });
        skip.setOnAction(event -> {
            rockManager.move(1);
            setData(rockManager.getChartData());
            clock.setText(rockManager.clock+"");
        });
        skip10.setOnAction(event -> {
            rockManager.move(10);
            setData(rockManager.getChartData());
            clock.setText(rockManager.clock+"");
        });
        genRock.setOnAction(event -> {
            addRock();
        });
        openSettings.setOnAction(event -> {
            tl.stop();
            play.setText("▷");
            SettingsController controller = new SettingsController();
            Stage stage = Util.genStage("Settings", "/fxml/Settings.fxml", controller, resource);
            stage.showAndWait();
            initChart(true);
        });

        // UIイベント<メニュー>
        openLog.setOnAction(event -> {
            Stage stage = Util.genStage("Log", "/fxml/LogView.fxml", new LogViewController(), resource);
            stage.setAlwaysOnTop(true);
            stage.show();
        });
        openCredit.setOnAction(event -> {
            Util.genStage("Credit", "/fxml/Credit.fxml", null, resource).show();
        });

        // UIイベント<スライダー>
        speed.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateSpeed = Math.round(oldVal.doubleValue()*10)/10.0;
            speedVal.setText("x"+updateSpeed);
            if(tl.getStatus().equals(Animation.Status.RUNNING))
                initTimeLine();
        });

        // UIイベント<テキスト入力(1行)>
        widthF.textProperty().addListener((obs, oldText, newText) -> {
            widthFVal = Util.parseDouble(newText, 0.0);
            initChart(true);
        });
        widthT.textProperty().addListener((obs, oldText, newText) -> {
            widthTVal = Util.parseDouble(newText, 0.0);
            initChart(true);
        });
        heightF.textProperty().addListener((obs, oldText, newText) -> {
            heightFVal = Util.parseDouble(newText, 0.0);
            initChart(true);
        });
        heightT.textProperty().addListener((obs, oldText, newText) -> {
            heightTVal = Util.parseDouble(newText, 0.0);
            initChart(true);
        });
    }

    /**
     * ScatterChart初期化
     */
    private void initChart(boolean takeover) {
        // NumberAxis初期化
        axisNormalize();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Distance(m)");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit((widthTVal-widthFVal)/20);
        xAxis.setLowerBound(widthFVal);
        xAxis.setUpperBound(widthTVal);
        yAxis.setLabel("Height(m)");
        yAxis.setAutoRanging(false);
        yAxis.setTickUnit((heightTVal-heightFVal)/20);
        yAxis.setLowerBound(heightFVal);
        yAxis.setUpperBound(heightTVal);

        // 拡大率セット
        double scaleX = 500.0/(widthTVal-widthFVal);
        double scaleY = 500.0/(heightTVal-heightFVal);
        if(chart != null) {
            ObservableList<XYChart.Series<Number, Number>> series = chart.getData();
            for(XYChart.Series s: series)
                setChartScale(s, scaleX, scaleY);
        }

        // データ保存
        ObservableList<XYChart.Data<Number, Number>> tmp = null;
        if(takeover && chart != null)
           tmp = chart.getData();

        // Chart設定
        chart = new ScatterChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        if(takeover)
            chart.setData(tmp);

        // 配置
        AnchorPane.setTopAnchor(chart, 0.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 20.0);
        AnchorPane.setBottomAnchor(chart, 0.0);
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    /**
     * TimeLine初期化
     */
    private void initTimeLine() {
        Duration d = new Duration(500/updateSpeed);
        KeyFrame kf = new KeyFrame(d, event -> {
            rockManager.move(1);
            setData(rockManager.getChartData());
            clock.setText(rockManager.clock.toString());
        });
        tl.stop();
        tl = new Timeline(kf);
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    /**
     * 散布図描画データ追加
     */
    private void setData(XYChart.Series series) {
        double scaleX = 500.0/(widthTVal-widthFVal);
        double scaleY = 500.0/(heightTVal-heightFVal);
        setChartScale(series, scaleX, scaleY);
        chart.getData().addAll(series);
    }

    /**
     * グラフ表示のスケーリングを行う
     *
     * @param series スケーリング対象となるデータ群
     * @param rateX X方向の拡大率
     * @param rateY Y方向の拡大率
     */
    private void setChartScale(XYChart.Series series, double rateX, double rateY) {
        if(chart == null) return;
        if(Settings.ViewRatioNormalize) {    // 表示比正規化
            rateX = (rateX+rateY)/2.0;
            rateY = rateX;
        }
        ObservableList<XYChart.Data<Number, Number>> data = series.getData();
        for(XYChart.Data d: data) {
            d.getNode().setScaleX(rateX*Settings.RockMagnification);
            d.getNode().setScaleY(rateY*Settings.RockMagnification);
        }
    }

    /**
     * 噴石追加ウィンドウを表示して処理を行う
     */
    private void addRock() {
        GenRockController controller = new GenRockController();
        Stage stage = Util.genStage("Gen Rock", "/fxml/GenRock.fxml", controller, resource);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(controller.inputOK()) {
            String id = controller.id;
            String color = controller.color;
            double x = controller.x;
            double y = controller.y;
            double v0 = controller.v0;
            double theta = controller.theta;
            double diameter = controller.diameter;
            if(controller.enableAirRes())
                rockManager.addAirResistanceRock(id, color, x, y, v0, theta, diameter);
            else
                rockManager.addPlainRock(id, color, x, y, v0, theta);
            setData(rockManager.getChartData());
        }
    }

    /**
     * 軸サイズの正規化を行う
     */
    private void axisNormalize() {
        if(chart == null) return;
        if(Settings.AxisNormalize) {
            double waSize = ((Number)chart.getXAxis().getWidth()).doubleValue();
            double haSize = ((Number)chart.getYAxis().getHeight()).doubleValue();
            double aratio = haSize/waSize;
            double baseSize = widthTVal-widthFVal;
            heightTVal = heightFVal+baseSize*aratio;
        }
        heightT.setDisable(Settings.AxisNormalize);
    }
}
