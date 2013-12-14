package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyStats {

    public static final int SAMPLE_MAT = 120;
    public static final int SAMPLE_FASTA = 350;
    public static final int SAMPLE_TRANING = 140;

    private int mat;
    private int fasta;
    private int traning;
    
    public int getMat() {
        return mat;
    }
   
    public void setMat(int mat) {
        this.mat = mat;
    }
    
    public int getFasta() {
        return fasta;
    }
    
    public void setFasta(int fasta) {
        this.fasta = fasta;
    }
    
    public int getTraning() {
        return traning;
    }
    
    public void setTraning(int traning) {
        this.traning = traning;
    }
    
    public static DailyStats getSampleDailyStats() {
        return getSampleDailyStatsAndAdd(0);
    }

    public static DailyStats getSampleDailyStatsAndAdd(int numberToAdd) {
        DailyStats stats = new DailyStats();
        stats.setMat(DailyStats.SAMPLE_MAT + numberToAdd);
        stats.setFasta(DailyStats.SAMPLE_FASTA + numberToAdd);
        stats.setTraning(DailyStats.SAMPLE_TRANING + numberToAdd);
        return stats;
    }
    
    public static DailyStats getEmptyDailyStats() {
        DailyStats stats = new DailyStats();
        stats.setMat(0);
        stats.setFasta(0);
        stats.setTraning(0);
        return stats;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fasta;
        result = prime * result + mat;
        result = prime * result + traning;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DailyStats other = (DailyStats) obj;
        if (fasta != other.fasta)
            return false;
        if (mat != other.mat)
            return false;
        if (traning != other.traning)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DailyStats [mat=" + mat + ", fasta=" + fasta + ", traning="
                + traning + "]";
    }
    
}
