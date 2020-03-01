package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import simulator.RockManager;

public class MainUIController implements Initializable {

    // UI部品
    @FXML
    private AnchorPane chartPane;
    @FXML
    private Slider speed;
    @FXML
    private Label speedVal;
    @FXML
    private Button play, skip, skip10;
    @FXML
    private TextField widthF, widthT, heightF, heightT;

    // 描画用
    private ScatterChart chart;
    private double updateSpeed;
    private double widthFVal, widthTVal, heightFVal, heightTVal;
    private RockManager rockManager;
    private Timeline tl;

    /**
     * 初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 初期化
        initChart(0, 1000, 0, 800);
        updateSpeed = 1.0;
        widthFVal = heightFVal = 0;
        widthTVal = 1000;
        heightTVal = 800;
        rockManager = new RockManager();
        setData(rockManager.getChartData());

        // UIイベント
        play.setOnAction(event -> initTimeLine());
        skip.setOnAction(event -> {
            rockManager.move(1);
            setData(rockManager.getChartData());
        });
        skip10.setOnAction(event -> {
            rockManager.move(10);
            setData(rockManager.getChartData());
        });
        speed.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateSpeed = Math.round(oldVal.doubleValue()*10)/10.0;
            speedVal.setText(updateSpeed+"");
        });
        widthF.textProperty().addListener((obs, oldText, newText) -> {
            widthFVal = parseDouble(newText);
            initChart(widthFVal, widthTVal, heightFVal, heightTVal);
        });
        widthT.textProperty().addListener((obs, oldText, newText) -> {
            widthTVal = parseDouble(newText);
            initChart(widthFVal, widthTVal, heightFVal, heightTVal);
        });
        heightF.textProperty().addListener((obs, oldText, newText) -> {
            heightFVal = parseDouble(newText);
            initChart(widthFVal, widthTVal, heightFVal, heightTVal);
        });
        heightT.textProperty().addListener((obs, oldText, newText) -> {
            heightTVal = parseDouble(newText);
            initChart(widthFVal, widthTVal, heightFVal, heightTVal);
        });
    }

    /**
     * ScatterChart初期化
     *
     * @param widthF 描画幅(From)
     * @param widthT 描画幅(To)
     * @param heightF 描画高さ(From)
     * @param heightT 描画高さ(To)
     */
    private void initChart(double widthF, double widthT, double heightF, double heightT) {
        // NumberAxis初期化
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Distance(m)");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(widthF);
        xAxis.setUpperBound(widthT);
        yAxis.setLabel("Height(m)");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(heightF);
        yAxis.setUpperBound(heightT);

        // Chart設定
        chart = new ScatterChart<>(xAxis, yAxis);
        chart.setAnimated(false);

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
        if(tl != null && tl.getStatus().equals(Animation.Status.RUNNING))
            return;
        tl = new Timeline(
                new KeyFrame(
                    Duration.seconds(0.5),
                    event -> {
                        rockManager.move(50);
                        setData(rockManager.getChartData());
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
