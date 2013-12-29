package writers;

import api.SpreadSheetException;
import model.PeriodizedStatsWrapper;

public interface StatsWriter {

    public <T> PeriodizedStatsWrapper<T> writeStats(String colValue, T stats) throws SpreadSheetException;

}
