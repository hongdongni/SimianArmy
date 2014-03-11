package com.netflix.simianarmy;

import static org.joda.time.DateTimeConstants.MILLIS_PER_DAY;

import org.joda.time.DateTime;
import org.testng.Assert;

public class TestUtils {
    
    /** Verify that the termination date is roughly retentionDays from now
     * By 'roughly' we mean within one day.  There are times (twice per year)
     * when certain tests execute and the Daylight Savings cutover makes it not 
     * a precisely rounded day amount (for example, a termination policy of 4 days
     * will really be about 3.95 days, or 95 hours, because one hour is lost as
     * the clocks "spring ahead").
     * 
     * A more precise, but complicated logic could be written to make sure that "roughly"
     * means not more than an hour before and not more than an hour after the anticipated
     * cutoff, but that makes the test much less readable.
     * 
     * By just making sure that the difference between the actual and proposed dates
     * is less than one day, we get a rough idea of whether the termination time was correct.
     * @param resource The AWS Resource to be checked
     * @param retentionDays number of days it should be kept around
     * @param timeOfCheck The time the check is run
     */
    public static void verifyTerminationTimeRough(Resource resource, int retentionDays, DateTime timeOfCheck) {
        long days = (resource.getExpectedTerminationTime().getTime() - timeOfCheck.getMillis()) / MILLIS_PER_DAY;
        Assert.assertTrue(Math.abs(days - retentionDays) <= 1);
    }

}
