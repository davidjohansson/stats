package writers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import model.SpreadSheetException;


public class StatsHelper {

 
    public static <T> boolean isEmpty(T stats) throws SpreadSheetException{
        ValueCheckerVisitor<T> visitor = new StatsHelper().new ValueCheckerVisitor<T>();
        try {
            visitAllFields(stats, visitor);
            return visitor.empty;
        } catch (Exception e) {
            throw new SpreadSheetException(e);
        }
    }

    public static <T extends Object> T getBackOutStats(T stats) throws SpreadSheetException {
        try{
            Class<?> backOutStatsClass =  Class.forName(stats.getClass().getName());
            T backOutStats = (T) backOutStatsClass.newInstance();

            BackOutVisitor<T> backOutVisitor = new StatsHelper().new BackOutVisitor<T>(backOutStats);
            visitAllFields(stats, backOutVisitor);
            return backOutStats;
        } catch(Exception e){
            throw new SpreadSheetException(e);
        }
    }
 
    private static <T> void visitAllFields(T stats, StatsFieldVisitor<T> visitor)
            throws Exception {
        Field[] declaredFields = stats.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Class<?> type = field.getType();
            if(!Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                visitor.visitField(stats, field, type);
            }
        }
    }
    
    interface  StatsFieldVisitor<T>{
        public void visitField(T stats, Field field, Class<?> type) throws Exception;
    }
    
    class ValueCheckerVisitor<T> implements StatsFieldVisitor<T>{

        boolean empty = true;

        @Override
        public void visitField(T stats, Field field, Class<?> type) throws Exception {
            if(type.isAssignableFrom(double.class)){
                double val = field.getDouble(stats);
                if (val != 0.0){
                    empty = false;
                }
            } else if(type.isAssignableFrom(int.class)){
                int val = field.getInt(stats);
                if (val != 0){
                    empty = false;
                }
            }
        }
    }

    class BackOutVisitor<T> implements StatsFieldVisitor<T>{
        T backOutStats;
        public BackOutVisitor(T backOutStats){
            this.backOutStats = backOutStats;
        }

        @Override
        public void visitField(T stats, Field field, Class<?> type) throws Exception{
            if(type.isAssignableFrom(double.class)){
                double val = field.getDouble(stats);
                field.setDouble(backOutStats, val * -1.0);
            } else if(type.isAssignableFrom(int.class)){
                int val = field.getInt(stats);
                field.setInt(backOutStats, val * -1);
            }
        }
    }
}
