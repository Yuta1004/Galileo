package simulator;

public class AirResistanceRock extends Rock {

    // 計算用変数
    private int moveCnt;
    private double dt;
    private double xf, xb;
    private double yf, yb;
    private double vxf, vxp, vxb;
    private double vyf, vyp, vyb;
    private double rho_rock, rho_air;
    private double cd, diameter, mass, area;

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
        // 初期化
        super(x, y, v0, theta);
        this.diameter = diameter;
        dt = 0.01;
        cd = 0.2;
        rho_air = 1.2;
        rho_rock = 2800;
        vxp = v0*Math.cos(theta*PI/180.0);
        vyp = v0*Math.sin(theta*PI/180.0);
        mass = rho_rock*(4.0/3.0)*PI*Math.pow(diameter/2.0, 3);
        area = PI*Math.pow(dm/2.0, 2);
    }

    @Override
    public void move() {}

}

