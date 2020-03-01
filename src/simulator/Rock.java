public class Rock {

    private double x, y;

    /**
     * コンストラクタ
     */
    public Rock() {
        this(0, 0);
    }

    /**
     * コンストラクタ(初期座標指定)
     *
     * @param x x座標の初期値
     * @param y y座標の初期値
     */
    public Rock(double x, double y) {
        this.x = x;
        this.y = y;
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
