package lib;

public class Clock {

    private int second = 0;
    private int msecond = 0;
    public static final int H = 60*60, M = 60, S = 1;

    public Clock(int h, int m, int s, int ms) {
        set(h, m, s, ms);
    }

    public void set(int h, int m, int s, int ms) {
        second = h*H + m*M + s*S;
        msecond = ms;
    }

    public void tick() {
        tick(1);
    }

    public void tick(int t) {
        // 加算
        msecond += t;
        if(msecond % 1000 == 0) {
            second += msecond/1000;
            msecond %= 1000;
        }

        // 繰り上げ処理
        if(second >= 86400)
            second %= 86400;
        if(second < 0)
            second = 86400-Math.abs(second)%86400;
    }

    public String toString() {
        return String.format("%02d:%02d:%02d.%03d", second/H, second/M%M, second%M, msecond);
    }
}
