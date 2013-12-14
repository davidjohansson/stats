package model;

public class SpreadSheetException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SpreadSheetException(Throwable t){
        super(t);
    }
    
    public SpreadSheetException(String s){
        super(s);
    }
}
