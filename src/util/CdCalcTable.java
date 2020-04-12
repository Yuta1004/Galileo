package util;

import java.util.Hashtable;

/**
 * CD計算用の値をもつ
 */
public class CdCalcTable {

    // 対応表
    private static final Hashtable<Fluid, Double> viscosity, density;

    static {
        // 粘性係数
        viscosity = new Hashtable<Fluid, Double>();
        viscosity.put(Fluid.AIR, 0.000018);
        viscosity.put(Fluid.WATER, 0.000089);
        viscosity.put(Fluid.MAYO, 8.0);

        // 流体密度
        density = new Hashtable<Fluid, Double>();
        density.put(Fluid.AIR, 1.2);
        density.put(Fluid.WATER, 998.233);   // 20度
        density.put(Fluid.MAYO, 1456.0);
    }

    /**
     * 直径、密度から抵抗係数cdを計算する
     * 近似式: Morisonの式
     *
     * @param fluid 流体
     * @param r 直径(m)
     * @param v 流速(m/s)
     */
    public static double calc(Fluid fluid, double r, double v) {
        double u = viscosity.get(fluid);
        double p = density.get(fluid);
        double re = p*v*r/u;
        double cd = (24.0/re) +
                    (2.6*(re/5.0)) / (1+Math.pow(re/5.0,1.52)) +
                    (0.411*Math.pow(re/263000.0,-7.94)) / (1+Math.pow(re/263000.0,-8.0)) +
                    (0.25*(re/1000000.0)) / (1+(re/1000000.0));
        return cd;
    }
}