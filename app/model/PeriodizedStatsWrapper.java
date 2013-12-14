package model;

public class PeriodizedStatsWrapper<T> {
    
    T weekly;
    T daily;
    
    
    public PeriodizedStatsWrapper(T daily, T weekly){
        this.daily = daily;
        this.weekly = weekly;
    }
    
    public T getWeekly(){
        return weekly;
    }

    public T getDaily() {
        return daily;
    }
}
