package db;

import java.util.ArrayList;

public class Log {

    private static int fetchedNum = 0;
    private static boolean updatedFlag = false;
    private static ArrayList<String> logData = new ArrayList<String>();

    /**
     * ログを追加する
     *
     * @param log ログ
     */
    public static void add(String log) {
        updatedFlag = true;
        logData.add(logData.size()+": "+log);
    }

    /**
     * ログを追加する(フォーマット付き)
     */
    public static void add(String fmt, Object ... vals) {
        add(String.format(fmt, vals));
    }

    /**
     * 更新があったかどうかを返す
     *
     * @return boolean
     */
    public static boolean checkUpdate() {
        return updatedFlag;
    }

    /**
     * 新規データ取得
     */
    public static ArrayList<String> fetch() {
        int oldFetchedNum = fetchedNum;
        fetchedNum = logData.size();
        return new ArrayList<String>(logData.subList(oldFetchedNum, logData.size()));
    }

    /**
     * 全データ取得
     */
    public static ArrayList<String> getAll() {
        fetchedNum = logData.size();
        return new ArrayList<String>(logData.subList(0, logData.size()));
    }
}