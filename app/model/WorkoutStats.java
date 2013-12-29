package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkoutStats {
    
    private double Fivekm;
    private int rackhav;
    private int dips;
    private double marklyft;
    private double styrkefrivandning;
    private double styrkeryck;
    private double splitjerk;
    private double bankpress;
    private double press;

    public double getFivekm() {
        return Fivekm;
    }
    public void setFivekm(double fivekm) {
        Fivekm = fivekm;
    }
    public int getRackhav() {
        return rackhav;
    }
    public void setRackhav(int rackhav) {
        this.rackhav = rackhav;
    }
    public int getDips() {
        return dips;
    }
    public void setDips(int dips) {
        this.dips = dips;
    }
    public double getMarklyft() {
        return marklyft;
    }
    public void setMarklyft(double marklyft) {
        this.marklyft = marklyft;
    }
    public double getStyrkefrivandning() {
        return styrkefrivandning;
    }
    public void setStyrkefrivandning(double styrkefrivandning) {
        this.styrkefrivandning = styrkefrivandning;
    }
    public double getStyrkeryck() {
        return styrkeryck;
    }
    public void setStyrkeryck(double styrkeryck) {
        this.styrkeryck = styrkeryck;
    }
    public double getSplitjerk() {
        return splitjerk;
    }
    public void setSplitjerk(double splitjerk) {
        this.splitjerk = splitjerk;
    }
    public double getBankpress() {
        return bankpress;
    }
    public void setBankpress(double bankpress) {
        this.bankpress = bankpress;
    }
    public double getPress() {
        return press;
    }

    public void setPress(double press) {
        this.press = press;
    }
    public final static double SAMPLE_FIVEKM = 34.07;
    public final static int SAMPLE_RACKHAV = 8;
    public final static int SAMPLE_DIPS = 5;
    public final static double SAMPLE_MARKLYFT = 70.0;
    public final static double SAMPLE_STYRKEFRIVANDNING = 35.0;
    public final static double SAMPLE_STYRKERYCK = 30.0;
    public final static double SAMPLE_SPLITJERK = 35.0;
    public final static double SAMPLE_BANKPRESS = 50.0;
    public final static double SAMPLE_PRESS = 30.0;

    public static WorkoutStats getSampleWorkoutStats() {

        WorkoutStats workoutStat = new WorkoutStats();
        workoutStat.bankpress = SAMPLE_BANKPRESS;
        workoutStat.dips = SAMPLE_DIPS;
        workoutStat.Fivekm = SAMPLE_FIVEKM;
        workoutStat.marklyft = SAMPLE_MARKLYFT;
        workoutStat.press = SAMPLE_PRESS;
        workoutStat.rackhav = SAMPLE_RACKHAV;
        workoutStat.splitjerk = SAMPLE_SPLITJERK;
        workoutStat.styrkefrivandning = SAMPLE_STYRKEFRIVANDNING;
        workoutStat.styrkeryck = SAMPLE_STYRKERYCK;
        
        return workoutStat;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(Fivekm);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bankpress);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + dips;
        temp = Double.doubleToLongBits(marklyft);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(press);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + rackhav;
        temp = Double.doubleToLongBits(splitjerk);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(styrkefrivandning);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(styrkeryck);
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
        WorkoutStats other = (WorkoutStats) obj;
        if (Double.doubleToLongBits(Fivekm) != Double
                .doubleToLongBits(other.Fivekm))
            return false;
        if (Double.doubleToLongBits(bankpress) != Double
                .doubleToLongBits(other.bankpress))
            return false;
        if (dips != other.dips)
            return false;
        if (Double.doubleToLongBits(marklyft) != Double
                .doubleToLongBits(other.marklyft))
            return false;
        if (Double.doubleToLongBits(press) != Double
                .doubleToLongBits(other.press))
            return false;
        if (rackhav != other.rackhav)
            return false;
        if (Double.doubleToLongBits(splitjerk) != Double
                .doubleToLongBits(other.splitjerk))
            return false;
        if (Double.doubleToLongBits(styrkefrivandning) != Double
                .doubleToLongBits(other.styrkefrivandning))
            return false;
        if (Double.doubleToLongBits(styrkeryck) != Double
                .doubleToLongBits(other.styrkeryck))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "WorkoutStats [Fivekm=" + Fivekm + ", rackhav=" + rackhav
                + ", dips=" + dips + ", marklyft=" + marklyft
                + ", styrkefrivandning=" + styrkefrivandning + ", styrkeryck="
                + styrkeryck + ", splitjerk=" + splitjerk + ", bankpress="
                + bankpress + ", press=" + press + "]";
    }

}
