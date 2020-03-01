package simulator;

import java.util.ArrayList;

public class RockManager {

    private ArrayList<Rock> rocks;

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
        rocks.add(new AirRisistanceRock(x, y, v0, theta, diameter));
    }

    /**
     * 1ステップ進める
     */
    public void move() {
        for(Rock rock: rocks)
            rock.move();
    }
}
