package simulator;

import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RockManager {

    private ArrayList<Rock> rocks;
    private Color[] colors = {
        Color.FORESTGREEN, Color.CORAL, Color.MAGENTA, Color.CRIMSON, Color.BLUE,
        Color.BROWN, Color.CHARTREUSE, Color.AQUAMARINE, Color.BLACK, Color.DARKORANGE
    };

    /**
     * コンストラクタ
     */
    public RockManager() {
        rocks = new ArrayList<Rock>();
    }

    /**
     * 空気抵抗なし噴石追加
     *
     * @param x x座標
     * @param y y座標
     * @param v0 射出速度
     * @param theta 射出角度
     */
    public void addPlainRock(double x, double y, double v0, double theta) {
        rocks.add(new PlainRock(x, y, v0, theta));
    }

    /**
     * 空気抵抗あり噴石追加
     *
     * @param x x座標
     * @param y y座標
     * @param v0 射出速度
     * @param theta 射出速度
     * @param diameter 噴石の直径
     */
    public void addAirResistanceRock(double x, double y, double v0, double theta, double diameter) {
        rocks.add(new AirResistanceRock(x, y, v0, theta, diameter));
    }

    /**
     * 指定ステップ進める
     *
     * @param n ステップ数
     */
    public void move(int n) {
        for(int idx = 0; idx < n; ++ idx)
            for(Rock rock: rocks)
                rock.move();
    }

    /**
     * リセットする
     */
    public void reset(){
        for(Rock rock: rocks)
            rock.reset();
    }

    /**
     * Chart用データを返す
     *
     * @return XYChart.Series
     */
    public XYChart.Series getChartData() {
        XYChart.Series series = new XYChart.Series();
        for(int idx = 0; idx < rocks.size(); ++ idx) {
            Rock rock = rocks.get(idx);
            XYChart.Data data = new XYChart.Data(rock.getX(), rock.getY());
            data.setNode(new Circle(3, colors[idx%10]));
            series.getData().add(data);
        }
        return series;
    }
}
