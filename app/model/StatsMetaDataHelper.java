package model;

import java.util.HashMap;

public class StatsMetaDataHelper {

    private static HashMap<Class<?>, StatsMetaData> metaDataStorage = new HashMap<Class<?>, StatsMetaData>();

    static{
        metaDataStorage.put(BodyStats.class, new BodyStatsMetaData());
        metaDataStorage.put(DailyStats.class, new DailyStatsMetaData());
    }

    public static <T> StatsMetaData getMetaData(Class<T> clazz){
        StatsMetaData metaData = metaDataStorage.get(clazz);
        if(metaData == null){
            throw new IllegalArgumentException("No metadata for class " + clazz.getClass().getName());
        }
        return metaData;
    }
}
