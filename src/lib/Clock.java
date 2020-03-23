package lib;

public class Clock {

    private int second = 0;
    public static final int H = 60*60, M = 60, S = 1;

    public Clock(int h, int m, int s) {
        set(h, m, s);
    }

    public void set(int h, int m, int s) {
        second = h*H + m*M + s*S;
    }

    public void tick() {
        tick(1);
    }

    public void tick(int t) {
        second += t;
        if(second >= 86400)
            second %= 86400;
        if(second < 0)
            second = 86400-Math.abs(second)%86400;
    }

    public String toString() {
        return String.format("%02d:%02d:%02d", second/H, second/M%M, second%M);
    }
}
