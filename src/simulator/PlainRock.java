package simulator;

import db.Settings;

public class PlainRock extends Rock {

    // 計算用変数
    private int moveCnt;
    private double dt;
    private double xf, xb;
    private double yf, yb;
    private double vxf, vxp, vxb;
    private double vyf, vyp, vyb;

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
        // ステップ値計算
        if(moveCnt > 0 && y <= 0) return;
        dt = Settings.StepVal;
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

}
