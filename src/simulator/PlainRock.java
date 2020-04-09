package simulator;

import db.Log;
import db.Settings;

public class PlainRock extends Rock {

    // 計算用変数
    private int moveCnt;
    private double dt;
    private double xf, xb;
    private double yf, yb;
    private double vxf, vxp, vxb;
    private double vyf, vyp, vyb;
    private boolean hitGroundFlag = false;

    /**
     * コンストラクタ
     *
     * @param x x座標の初期値
     * @param y y座標の初期値
     * @param v0 射出速度
     * @param theta 射出角度
     */
    public PlainRock(double x, double y, double v0, double theta) {
        // 初期化
        super(x, y, v0, theta);
        dt = 0.1;
        moveCnt = 0;
        vxp = v0*Math.cos(theta*PI/180.0);
        vyp = v0*Math.sin(theta*PI/180.0);
    }

    @Override
    public void move() {
        // 着地判定
        dt = Settings.StepVal;
        if(!hitGroundFlag && moveCnt > 0 && y <= 0) {
            hitGroundFlag = true;
            calSpeedAndArg();
        }

        // ステップ値計算
        if(moveCnt == 0) {
            xf = x+vxp*dt;
            yf = y+vyp*dt;
            vxf = vxp;
            vyf = vyp-G*dt;
        } else {
            xf = xb+vxp*2*dt;
            yf = yb+vyp*2*dt;
            vxf = vxb;
            vyf = vyb-G*2*dt;
        }
        updateCalcData();
        ++ moveCnt;
    }

   @Override
    public void reset() {
        moveCnt = 0;
        x = X;
        y = Y;
        xb = xf = 0;
        yb = yf = 0;
        vxb = vxf = 0;
        vyb = vyf = 0;
        vxp = v0*Math.cos(theta*PI/180.0);
        vyp = v0*Math.sin(theta*PI/180.0);
    }

    /**
     * 計算用変数を更新する
     */
    private void updateCalcData() {
        xb = x;
        yb = y;
        vxb = vxp;
        vyb = vyp;
        x = xf;
        y = yf;
        vxp = vxf;
        vyp = vyf;
    }

    /**
     * 噴石の速度、角度を調べてログ出力
     * ※弾着時に呼ばれることが前提の実装
     */
    private void calSpeedAndArg() {
        double X = x-xb;
        double Y = yb-y;
        double HypoXY = Math.sqrt(X*X+Y*Y);
        double deg = Math.toDegrees(Math.acos(X/HypoXY));   // °
        double speed = HypoXY*(1/Settings.StepVal);         // m/s
        Log.add("Hit ground => Speed: %.3fm/s, Deg: %.3f°", speed, deg);
    }
}
