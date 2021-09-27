import java.util.BitSet;

public class Cron {

    private BitSet minutes;
    private BitSet hours;
    private BitSet days;
    private BitSet months;
    private BitSet daysOfWeek;
    private String fileName;

    public BitSet getMinutes() {
        return minutes;
    }

    public BitSet getHours() {
        return hours;
    }

    public BitSet getDays() {
        return days;
    }

    public BitSet getMonths() {
        return months;
    }

    public BitSet getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getFileName() {
        return fileName;
    }

    public void setMinutes(BitSet minutes) {
        this.minutes = minutes;
    }

    public void setHours(BitSet hours) {
        this.hours = hours;
    }

    public void setDays(BitSet days) {
        this.days = days;
    }

    public void setMonths(BitSet months) {
        this.months = months;
    }

    public void setDaysOfWeek(BitSet daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String convertToString(BitSet bitSet, boolean isRangeDifferentThanBitSet){
        StringBuilder result = new StringBuilder();
        for (int i=bitSet.nextSetBit(0);i>=0;i=bitSet.nextSetBit(i+1)){
            if(isRangeDifferentThanBitSet) result.append(i+1+" ");
            else result.append(i+" ");
            if(i==Integer.MAX_VALUE) break;
        }
        return new String(result);
    }
}
