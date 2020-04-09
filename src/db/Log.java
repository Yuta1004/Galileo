package db;

import java.util.ArrayList;

public class Log {

    private static int num;
    private static boolean updatedFlag = false;
    private static ArrayList<String> logData = new ArrayList<String>();

    public static void addData(String log) {
        updatedFlag = true;
        logData.add(num+": "+log);
    }
}