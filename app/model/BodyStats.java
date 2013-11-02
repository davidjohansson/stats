package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyStats {

    public static final double SAMPLE_BROST = 103.5;
    public static final double SAMPLE_MAGE = 82.5;
    public static final double SAMPLE_FETT2 = 12.7;
    public static final double SAMPLE_NACKE = 43.5;
    public static final double SAMPLE_VIKT = 82.0;
    private double vikt;
    private double fett2;
    private double mage;
    private double brost;
    private double nacke;

    public double getVikt() {
        return vikt;
    }

    public void setVikt(double vikt) {
        this.vikt = vikt;
    }

    public double getFett2() {
        return fett2;
    }

    public void setFett2(double fett2) {
        this.fett2 = fett2;
    }

    public double getMage() {
        return mage;
    }

    public void setMage(double mage) {
        this.mage = mage;
    }

    public double getBrost() {
        return brost;
    }

    public void setBrost(double brost) {
        this.brost = brost;
    }

    public double getNacke() {
        return nacke;
    }

    public void setNacke(double nacke) {
        this.nacke = nacke;
    }

    @Override
    public String toString() {
        return "BodyStats [vikt=" + vikt + ", fett2=" + fett2 + ", mage="
                + mage + ", bröst=" + brost + ", nacke=" + nacke + "]";
    }
    
    public static BodyStats getSampleBodyStats(){
        BodyStats stats = new BodyStats();
        stats.setBrost(SAMPLE_BROST);
        stats.setFett2(SAMPLE_FETT2);
        stats.setMage(SAMPLE_MAGE);
        stats.setNacke(SAMPLE_NACKE);
        stats.setVikt(SAMPLE_VIKT);
        return stats;
    }
}
