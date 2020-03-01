package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {

    // UI部品
    @FXML
    private AnchorPane chartPane;

    // 描画用
    private ScatterChart chart;

    /**
     * 初期化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        initChart(0, 1000, 0, 800);
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
}
