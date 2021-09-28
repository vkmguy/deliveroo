public enum DayOfWeek {

    MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6), SUN(7);

    private final int dayNumber;

    DayOfWeek(int dayNumber){
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }
}
