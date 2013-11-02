package writers;

import model.BodyStats;
import model.SpreadSheetException;

public interface StatsWriter {

    Object writeStats(String any, BodyStats any2) throws SpreadSheetException;

}
