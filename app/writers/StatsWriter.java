package writers;

import model.PeriodizedStatsWrapper;
import model.SpreadSheetException;

public interface StatsWriter {

    public <T> PeriodizedStatsWrapper<T> writeStats(String colValue, T stats) throws SpreadSheetException;

}
