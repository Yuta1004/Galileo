package simulator;

import db.Log;
import db.Settings;

public abstract class Rock {

    protected double x, y;
    protected final String id;
    protected final double v0, theta;
    protected final double X, Y;
    public static double G = 9.8;
    public static double PI = 3.141592653589793;


    /**
     * コンストラクタ
     *
     * @param id 噴石ID
     * @param x x座標の初期値
     * @param y y座標の初期値
     * @param v0 射出速度
     * @param theta 射出角度
     */
    public Rock(String id, double x, double y, double v0, double theta) {
        X = x;
        Y = y;
        this.id = id;
        this.x = x;
        this.y = y;
        this.v0 = v0;
        this.theta = theta;
    }

    /**
     * idを返す
     *
     * @return String id
     */
    public String getID() {
        return id;
    }

    /**
     * xを返す
     *
     * @return double x
     */
    public double getX() {
        return x;
    }

    /**
     * yを返す
     *
     * @return double x
     */
    public double getY(){
        return y;
    }

    /**
     * シミュレート
     */
    public abstract void move();

    /**
     * リセット
     */
    public abstract void reset();

    /**
     * 噴石の速度、角度を調べてログ出力
     * ※弾着時に呼ばれることが前提の実装
     * ※空気抵抗の有無にかかわらず共通の処理
     */
    protected void calSpeedAndArg(double xb, double yb, double x, double y) {
        double X = x-xb;
        double Y = yb-y;
        double HypoXY = Math.sqrt(X*X+Y*Y);
        double deg = Math.toDegrees(Math.acos(X/HypoXY));   // °
        double speed = HypoXY*(1/Settings.StepVal);         // m/s
        Log.add("Hit ground => ID: %s, Speed: %.3fm/s, Deg: %.3f°", id, speed, deg);
    }
}
