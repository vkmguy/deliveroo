public enum CronField {
    MINUTE(0,59,60), HOUR(0,23,24), DAY(1,31,31),
    MONTH(1,12,12), DAY_OF_WEEK(1,7,7);

    private final int min;
    private final int max;
    private final int length;

    CronField(int min, int max, int length) {
        this.min = min;
        this.max = max;
        this.length = length;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getLength() {
        return length;
    }
}
