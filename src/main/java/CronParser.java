import java.util.BitSet;
import java.util.Locale;

public class CronParser {

    private String name;
    private int min;
    private int max;
    private int length;

    public CronParser(CronField cronField) {
        switch (cronField){
            case MINUTE:
                this.name = cronField.toString().toLowerCase(Locale.ROOT);
                this.min = 0;
                this.max = 59;
                this.length = 60;
                break;
            case HOUR:
                this.name = cronField.toString().toLowerCase(Locale.ROOT);
                this.min = 0;
                this.max = 23;
                this.length = 24;
                break;
            case DAY:
                this.name = cronField.toString().toLowerCase(Locale.ROOT);
                this.min = 1;
                this.max = 31;
                this.length = 31;
                break;
            case MONTH:
                this.name = cronField.toString().toLowerCase(Locale.ROOT);
                this.min = 1;
                this.max = 12;
                this.length = 12;
                break;
            case DAY_OF_WEEK:
                this.name = cronField.toString().toLowerCase(Locale.ROOT);
                this.min = 1;
                this.max = 7;
                this.length = 7;
                break;
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private int parseValue(String token) throws InvalidTokenException {
        int value;
        if (this.isInteger(token)) {
            value = Integer.parseInt(token);
        } else {
            throw new InvalidTokenException(String.format("invalid %s field: \"%s\"", this.name, token));
        }
        return value;
    }

    public BitSet parse(String token) throws InvalidTokenException {
        if (token.contains(",")) {
            BitSet bitSet = new BitSet(this.length);
            String[] items = token.split(",");
            for (String item : items) {
                bitSet.or(this.parse(item));
            }
            return bitSet;
        }

        if (token.contains("/"))
            return this.parseStep(token);

        if (token.contains("-"))
            return this.parseRange(token);

        if (token.equalsIgnoreCase("*"))
            return this.parseAsterisk();

        return this.parseLiteral(token);
    }

    private BitSet parseStep(String token) throws InvalidTokenException {
        try {
            String[] tokenParts = token.split("/");
            if (tokenParts.length != 2) {
                throw new InvalidTokenException(String.format("invalid %s field: \"%s\"", this.name, token));
            }
            String stepSizePart = tokenParts[1];
            int stepSize = this.parseValue(stepSizePart);
            if (stepSize < 1) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\". minimum allowed step (every) value is \"1\"",
                                this.name, token));
            }
            String numSetPart = tokenParts[0];
            if (!numSetPart.contains("-") && !numSetPart.equals("*") && isInteger(numSetPart)) {
                numSetPart = String.format("%s-%d", numSetPart, this.max);
            }
            BitSet numSet = this.parse(numSetPart);
            BitSet stepsSet = new BitSet(this.length);
            for (int i = numSet.nextSetBit(0); i < this.length; i += stepSize) {
                stepsSet.set(i);
            }
            stepsSet.and(numSet);
            return stepsSet;
        } catch (NumberFormatException ex) {
            throw new InvalidTokenException(String.format("invalid %s field: \"%s\"", this.name, token), ex);
        }
    }

    private BitSet parseRange(String token) throws InvalidTokenException {
        String[] rangeParts = token.split("-");
        if (rangeParts.length != 2) {
            throw new InvalidTokenException(String.format("invalid %s field: \"%s\"", this.name, token));
        }
        try {
            int from;
            int to;

            from = this.parseValue(rangeParts[0]);
            if (from < 0) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\".", this.name, this.min));
            }
            if (from < this.min) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\". minimum allowed value for %s field is \"%d\"",
                                this.name, token, this.name, this.min));
            }

            to = this.parseValue(rangeParts[1]);
            if (to < 0) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\".", this.name, this.min));
            }
            if (to > this.max) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\". maximum allowed value for %s field is \"%d\"",
                                this.name, token, this.name, this.max));
            }
            if (to < from) {
                throw new InvalidTokenException(String.format(
                        "invalid %s field: \"%s\". the start of range value must be less than or equal the end value",
                        this.name, token));
            }

            BitSet bitSet = new BitSet(this.length);
            int fromIndex = from - this.min;
            int toIndex = to - this.min;
            for (int i = fromIndex; i <= toIndex; i++) {
                bitSet.set(i);
            }
            return bitSet;
        } catch (NumberFormatException ex) {
            throw new InvalidTokenException(String.format("invalid %s field: \"%s\"", this.name, token), ex);
        }
    }

    private BitSet parseAsterisk() {
        BitSet bitSet = new BitSet(this.length);
        bitSet.set(0, this.length);
        return bitSet;
    }

    private BitSet parseLiteral(String token) throws InvalidTokenException {
        BitSet bitSet = new BitSet(this.length);
        try {
            int number = this.parseValue(token);
            if (number < 0) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\".", this.name, this.min));
            }
            if (number < this.min) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\". minimum allowed value for %s field is \"%d\"",
                                this.name, token, this.name, this.min));
            }
            if (number > this.max) {
                throw new InvalidTokenException(
                        String.format("invalid %s field: \"%s\". maximum allowed value for %s field is \"%d\"",
                                this.name, token, this.name, this.max));
            }
            bitSet.set(number - this.min);
        } catch (NumberFormatException ex) {
            throw new InvalidTokenException(String.format("invalid %s field: \"%s\"", this.name, token), ex);
        }
        return bitSet;
    }
}
