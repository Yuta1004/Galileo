public class Rock {

    private double x, y;
    private final double v0, theta;
    private static double G = 9.8;
    private static double PI = 3.141592653589793;


    /**
     * コンストラクタ
     *
     * @param x x座標の初期値
     * @param y y座標の初期値
     * @param v0 射出速度
     * @param theta 射出角度
     */
    public Rock(double x, double y, double v0, double theta) {
        this.x = x;
        this.y = y;
        this.v0 = v0;
        this.theta = theta;
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

}
