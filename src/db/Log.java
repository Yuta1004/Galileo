package db;

import java.util.ArrayList;

public class Log {

    private static boolean updatedFlag = false;
    private static ArrayList<String> logData = new ArrayList<String>();

    /**
     * ログを追加する
     *
     * @param log ログ
     */
    public static void addData(String log) {
        updatedFlag = true;
        logData.add(logData.size()+": "+log);
    }

    /**
     * 更新があったかどうかを返す
     *
     * @return boolean
     */
    public static boolean checkUpdate() {
        return updatedFlag;
    }
}