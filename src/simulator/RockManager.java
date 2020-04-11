package simulator;

import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Dictionary;

import lib.Clock;
import db.Log;
import db.Settings;

public class RockManager {

    // 噴石管理
    public final Clock clock;
    private ArrayList<Rock> rocks;
    private Dictionary<String, String> colorDict;

    /**
     * コンストラクタ
     */
    public RockManager() {
        clock = new Clock(0, 0, 0, 0);
        rocks = new ArrayList<Rock>();
        colorDict = new Dictionary<String, String>();
    }

    /**
     * 空気抵抗なし噴石追加
     *
     * @param id 噴石ID
     * @param color 表示色
     * @param x x座標
     * @param y y座標
     * @param v0 射出速度
     * @param theta 射出角度
     */
    public void addPlainRock
        (String id, String color, double x, double y, double v0, double theta)
    {
        Log.add("New Rock => x: %.1fm, y: %.1fm, v0: %.1fm/s, theta: %.1f°",
                x, y, v0, theta);
        rocks.add(new PlainRock(id, x, y, v0, theta));
        colorDict.put(id, color);
    }

    /**
     * 空気抵抗あり噴石追加
     *
     * @param id 噴石ID
     * @param color 表示色
     * @param x x座標
     * @param y y座標
     * @param v0 射出速度
     * @param theta 射出速度
     * @param diameter 噴石の直径
     */
    public void addAirResistanceRock
        (String id, String color, double x, double y, double v0, double theta, double diameter)
    {
        Log.add("New Rock => x: %.1fm, y: %.1fm, v0: %.1fm/s, theta: %.1f°, diameter: %.3fm",
                x, y, v0, theta, diameter);
        rocks.add(new AirResistanceRock(id, x, y, v0, theta, diameter));
        ColorDict.put(id, color);
    }

    /**
     * 指定ステップ進める
     *
     * @param n ステップ数
     */
    public void move(int n) {
        for(int idx = 0; idx < n; ++ idx) {
            clock.tick((int)(Settings.StepVal*1000));
            for(Rock rock: rocks)
                rock.move();
        }
    }

    /**
     * リセットする
     */
    public void reset() {
        clock.set(0, 0, 0, 0);
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
            // 描画属性決定
            Rock rock = rocks.get(idx);
            double sizeRate = 3.0;
            if(rock instanceof AirResistanceRock)
                sizeRate *= ((AirResistanceRock)rock).diameter;
            // 描画データ追加
            XYChart.Data data = new XYChart.Data(rock.getX(), rock.getY());
            Color color = Color.web(colorDict.get(rock.getID()));
            data.setNode(new Circle(sizeRate, color));
            series.getData().add(data);
        }
        return series;
    }
}
