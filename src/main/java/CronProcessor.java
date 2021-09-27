public class CronProcessor {

    private final static CronParser MINUTES_PARSER = new CronParser(CronField.MINUTE);
    private final static CronParser HOURS_PARSER = new CronParser(CronField.HOUR);
    private final static CronParser DAYS_PARSER = new CronParser(CronField.DAY);
    private final static CronParser MONTHS_PARSER = new CronParser(CronField.MONTH);
    private final static CronParser DAY_OF_WEEK_PARSER = new CronParser(CronField.DAY_OF_WEEK);
    private static final Cron CRON = new Cron();

    public static Cron processCron(String[] tokens) throws InvalidTokenException {
        int index = 0;
        String token;
        token =tokens[index++];
        CRON.setMinutes(CronProcessor.MINUTES_PARSER.parse(token));

        token =tokens[index++];
        CRON.setHours(CronProcessor.HOURS_PARSER.parse(token));

        token = tokens[index++];
        CRON.setDays(CronProcessor.DAYS_PARSER.parse(token));

        token = tokens[index++];
        CRON.setMonths(CronProcessor.MONTHS_PARSER.parse(token));

        token = tokens[index++];
        CRON.setDaysOfWeek(CronProcessor.DAY_OF_WEEK_PARSER.parse(token));

        token = tokens[index++];
        CRON.setFileName(token);

        return CRON;
    }

    public static void main(String[] args) throws InvalidTokenException {
        String[] tokens = args[0].trim().split("\\s+");
        if (tokens.length != 6) {
            System.out.println("cron tab expression should have 6 fields");
            System.exit(-1);
        }
        processCron(tokens);
        System.out.println(convertString());
    }

    public static String convertString() {
        return "MINUTES: "+ CRON.convertToString(CRON.getMinutes(),false)+"\n"
                + "HOUR: "+ CRON.convertToString(CRON.getHours(),false)+"\n"
                + "DAY OF MONTH: "+ CRON.convertToString(CRON.getDays(),true)+"\n"
                + "MONTH: "+ CRON.convertToString(CRON.getMonths(),true)+"\n"
                + "DAY OF WEEK: "+ CRON.convertToString(CRON.getDaysOfWeek(),true)+"\n"
                + "FILENAME: "+ CRON.getFileName();
    }
}
