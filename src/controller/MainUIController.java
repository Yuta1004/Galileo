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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import simulator.RockManager;

public class MainUIController implements Initializable {

    // UI部品
    @FXML
    private AnchorPane chartPane;
    @FXML
    private Slider speed;
    @FXML
    private Label speedVal, clock;
    @FXML
    private Button play, skip, skip10, init, reset, genRock;
    @FXML
    private TextField widthF, widthT, heightF, heightT;

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
        // 初期化
        this.resource = resource;
        updateSpeed = 1.0;
        widthFVal = heightFVal = 0;
        widthTVal = 1000;
        heightTVal = 800;
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
            clock.setText(rockManager.getClock());
            rockManager = new RockManager();
        });
        reset.setOnAction(event -> {
            tl.stop();
            initChart(false);
            rockManager.reset();
            play.setText("▷");
            clock.setText(rockManager.getClock());
            setData(rockManager.getChartData());
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
            rockManager.move(10);
            setData(rockManager.getChartData());
            clock.setText(rockManager.getClock());
        });
        skip10.setOnAction(event -> {
            rockManager.move(30);
            setData(rockManager.getChartData());
            clock.setText(rockManager.getClock());
        });
        genRock.setOnAction(event -> {
            addRock();
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
            widthFVal = parseDouble(newText);
            initChart(true);
        });
        widthT.textProperty().addListener((obs, oldText, newText) -> {
            widthTVal = parseDouble(newText);
            initChart(true);
        });
        heightF.textProperty().addListener((obs, oldText, newText) -> {
            heightFVal = parseDouble(newText);
            initChart(true);
        });
        heightT.textProperty().addListener((obs, oldText, newText) -> {
            heightTVal = parseDouble(newText);
            initChart(true);
        });
    }

    /**
     * ScatterChart初期化
     */
    private void initChart(boolean takeover) {
        // NumberAxis初期化
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
        tl.stop();
        tl = new Timeline(
                new KeyFrame(
                    new Duration(500/updateSpeed),
                    event -> {
                        rockManager.move(10);
                        setData(rockManager.getChartData());
                        clock.setText(rockManager.getClock());
                    }
                )
            );
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    /**
     * 散布図描画データ追加
     */
    private void setData(XYChart.Series series) {
        chart.getData().addAll(series);
    }

    /**
     * 噴石追加ウィンドウを表示して処理を行う
     */
    private void addRock() {
        GenRockController controller = new GenRockController();
        genStage("Gen Rock", "/fxml/GenRock.fxml", controller).showAndWait();
        if(controller.inputOK()) {
            double x = controller.x;
            double y = controller.y;
            double v0 = controller.v0;
            double theta = controller.theta;
            double diameter = controller.diameter;
            if(controller.enableAirRes())
                rockManager.addAirResistanceRock(x, y, v0, theta, diameter);
            else
                rockManager.addPlainRock(x, y, v0, theta);
            setData(rockManager.getChartData());
        }
    }

    /**
     * FXMLファイルを元にStageを生成して返す
     *
     * @param title ウィンドウタイトル
     * @param path FXMLファイルのパス
     * @param controller UIController
     */
    private <T> Stage genStage(String title, String path, T controller) {
        // FXML読み込み
        Scene scene = null;
        try {
            FXMLLoader loader  = new FXMLLoader(getClass().getResource(path), resource);
            if(controller != null)
                loader.setController(controller);
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 設定
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle(title);
        return stage;
    }


    /**
     * String to Double
     * 失敗時には0を返す
     */
    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    }

}
