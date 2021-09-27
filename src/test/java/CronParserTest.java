

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

public class CronParserTest {

    @Test
    public void testMinutesFieldParser() throws InvalidTokenException {
        CronParser parser = new CronParser(CronField.MINUTE);
        BitSet bitSet = parser.parse("*");
        Assert.assertEquals(60, bitSet.cardinality());
        bitSet = parser.parse("6");
        Assert.assertEquals(1, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(6));
        bitSet = parser.parse("1,5,6");
        Assert.assertEquals(3, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(5));
        Assert.assertTrue(bitSet.get(6));

    }

    @Test(expected = InvalidTokenException.class)
    public void testSecondsFieldParserInvalid() throws InvalidTokenException{
        CronParser parser = new CronParser(CronField.MINUTE);
        parser.parse("123");
    }

    @Test
    public void testDayFieldParser() throws InvalidTokenException {
        CronParser parser = new CronParser(CronField.DAY);
        BitSet bitSet = parser.parse("*");
        Assert.assertEquals(31, bitSet.cardinality());
        bitSet = parser.parse("5-8/3");
        Assert.assertEquals(2, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(4));
        Assert.assertTrue(bitSet.get(7));
        bitSet = parser.parse("2,7,10");
        Assert.assertEquals(3, bitSet.cardinality());
        Assert.assertTrue(bitSet.get(1));
        Assert.assertTrue(bitSet.get(6));
        Assert.assertTrue(bitSet.get(9));
    }

    @Test(expected = InvalidTokenException.class)
    public void testDaysFieldParserInvalidMax() throws InvalidTokenException {
        CronParser parser = new CronParser(CronField.DAY);
        parser.parse("32");
    }

}