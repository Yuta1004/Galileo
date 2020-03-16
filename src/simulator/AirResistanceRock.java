package simulator;

public class AirResistanceRock extends Rock {

    // 計算用変数
    private int moveCnt;
    private double dt;
    private double xf, xb;
    private double yf, yb;
    private double vxf, vxp, vxb;
    private double vyf, vyp, vyb;
    private final double rho_rock, rho_air;
    private final double cd, diameter, mass, area;

    /**
     * コンストラクタ
     *
     * @param x x座標の初期値
     * @param y y座標の初期値
     * @param v0 射出速度
     * @param theta 射出角度
     * @param diameter 噴石の直径(m)
     */
    public AirResistanceRock(double x, double y, double v0, double theta, double diameter) {
        super(x, y, v0, theta);
        this.diameter = diameter;

        // 定数初期化
        dt = 0.01;
        cd = diameter == 0.01 ? 0.4 : 0.2;
        rho_air = 1.2;
        rho_rock = 2800;
        mass = rho_rock*(4.0/3.0)*PI*Math.pow(diameter/2.0, 3);
        area = PI*Math.pow(diameter/2.0, 2);

        // 計算用変数初期化
        vxp = v0*Math.cos(theta*PI/180.0);
        vyp = v0*Math.sin(theta*PI/180.0);
    }

    @Override
    public void move() {
        double tmp = 0.5*cd*rho_air*area*Math.sqrt(Math.pow(vxp, 2)+Math.pow(vyp, 2));
        if(moveCnt == 0) {
            xf = x+vxp*dt;
            yf = y+vyp*dt;
            vxf = vxp-(tmp*vxp/mass*dt);
            vyf = vyp-(tmp*vyp/mass+G)*dt;
        } else {
            xf = xb+vxp*2*dt;
            yf = yb+vyp*2*dt;
            vxf = vxb-(tmp*vxp/mass*2*dt);
            vyf = vyb-(tmp*vyp/mass+G)*2*dt;
        }
        updateCalcData();
        ++ moveCnt;
    }

    @Override
    public void reset() {
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

