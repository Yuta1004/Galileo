public class PlainRock extends Rock {

    // 計算用変数
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
        vxp = v0*Math.cos(theta*PI/180.0);
        vyp = v0*Math.sin(theta*PI/180.0);
    }

    @Override
    public void move() {
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
