package com.zkwl.qhzwj.ui.down;

/**
 * @author songkai
 * @date on 2019/8/2
 */
public enum DownTimerEnum {

    defaultStr("不刷新", 0, 0),
    FiveS("5秒", 5, 5 * 1000),
    TenS("10秒", 10, 10 * 1000),
    FifteenS("15秒", 15, 15 * 1000),
    TwentyMinute("20秒", 20, 2060 * 1000),
    TwentyFiveMinute("25秒", 25, 25 * 1000),
    ThirtyMinute("30秒", 30, 30 * 1000),
    OneMinute("1分钟", 60, 60 * 1000);
//    FivMinute("5分钟", 5 * 60, 5 * 60 * 1000),
//    FifteenMinute("15分钟", 15 * 60, 15 * 60 * 1000),
//    Semih("半小时", 30 * 60, 30 * 60 * 1000),
//    OneHour("一小时", 60 * 60, 60 * 60 * 1000);


    DownTimerEnum(String timeStr, int timeInt, int timeTotalInt) {
        this.timeStr = timeStr;
        this.timeInt = timeInt;
        this.timeTotalInt = timeTotalInt;
    }

    // 成员变量
    private String timeStr;
    private int timeInt;
    private int timeTotalInt;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public int getTimeInt() {
        return timeInt;
    }

    public void setTimeInt(int timeInt) {
        this.timeInt = timeInt;
    }

    public int getTimeTotalInt() {
        return timeTotalInt;
    }

    public void setTimeTotalInt(int timeTotalInt) {
        this.timeTotalInt = timeTotalInt;
    }
}
