package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyStats {

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

    public static final double SAMPLE_BROST = 103.5;
    public static final double SAMPLE_MAGE = 82.5;
    public static final double SAMPLE_FETT2 = 12.7;
    public static final double SAMPLE_NACKE = 43.5;
    public static final double SAMPLE_VIKT = 82.0;

    public static BodyStats getSampleBodyStats(){
        BodyStats stats = new BodyStats();
        stats.setBrost(SAMPLE_BROST);
        stats.setFett2(SAMPLE_FETT2);
        stats.setMage(SAMPLE_MAGE);
        stats.setNacke(SAMPLE_NACKE);
        stats.setVikt(SAMPLE_VIKT);
        return stats;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(brost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fett2);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mage);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nacke);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vikt);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        BodyStats other = (BodyStats) obj;
        if (Double.doubleToLongBits(brost) != Double
                .doubleToLongBits(other.brost))
            return false;
        if (Double.doubleToLongBits(fett2) != Double
                .doubleToLongBits(other.fett2))
            return false;
        if (Double.doubleToLongBits(mage) != Double
                .doubleToLongBits(other.mage))
            return false;
        if (Double.doubleToLongBits(nacke) != Double
                .doubleToLongBits(other.nacke))
            return false;
        if (Double.doubleToLongBits(vikt) != Double
                .doubleToLongBits(other.vikt))
            return false;
        return true;
    }
}
