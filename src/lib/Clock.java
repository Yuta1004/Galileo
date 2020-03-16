public class Clock {

    private int second = 0;
    public static final int H = 60*60, M = 60, S = 1;

    public Clock(int h, int m, int s) {
        second = h*H + m*M + s*S;
    }

}
