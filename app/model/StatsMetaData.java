package model;

public interface StatsMetaData {
    
    public enum UpdateMode{
        OVERWRITE,
        ACCUMULATE, 
        NONE
    }
    

    public String getDailyWorkSheet();


    public String getWeeklyWorkSheet();


    public String getSpreadSheet();

}
