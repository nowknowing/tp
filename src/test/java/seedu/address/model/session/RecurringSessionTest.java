package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.SessionBuilder.DEFAULT_TIME;
import static seedu.address.testutil.TypicalStudents.CARL;

import org.junit.jupiter.api.Test;

class RecurringSessionTest extends SessionTest {
    static final Interval INTERVAL = new Interval("7");
    static final SessionDate SESSION_DATE = new SessionDate("2021-01-01", "10:00");
    static final Duration DURATION = new Duration("60");
    static final Subject SUBJECT = new Subject("English");
    static final Fee FEE = new Fee("40");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecurringSession(null,
                null, null, null, null, null));
    }

    @Test
    public void isValidEndTest() {
        SessionDate consistent = new SessionDate("2021-01-15", "10:00");
        SessionDate inconsistentDate = new SessionDate("2021-01-14", "10:00");
        SessionDate inconsistentTime = new SessionDate("2021-01-15", "12:00");


        assertTrue(RecurringSession.isValidEnd(SESSION_DATE, consistent, INTERVAL));
        assertFalse(RecurringSession.isValidEnd(SESSION_DATE, inconsistentDate, INTERVAL));
        assertFalse(RecurringSession.isValidEnd(SESSION_DATE, inconsistentTime, INTERVAL));
    }

    @Test
    public void isConsistentDatesAndIntervalTest() {
        SessionDate consistentDateAndInterval = new SessionDate("2021-01-15", "12:00");
        SessionDate same = new SessionDate("2021-01-01", "10:00");
        SessionDate inconsistentDate = new SessionDate("2021-01-14", "10:00");
        Interval inconsistentInterval = new Interval("3");
        Interval nextDay = new Interval("1");


        assertTrue(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, consistentDateAndInterval, INTERVAL));
        assertTrue(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, same, nextDay));
        assertFalse(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, inconsistentDate, INTERVAL));
        assertFalse(RecurringSession.isConsistentDatesAndInterval(
                SESSION_DATE, consistentDateAndInterval, inconsistentInterval));
    }

    @Test
    void getIntervalTest() {
        if (CARL.getListOfSessions().get(0) instanceof RecurringSession) {
            assertEquals(INTERVAL, (
                    (RecurringSession) CARL.getListOfSessions().get(0)).getInterval());
        }
    }

    @Test
    void getLastSessionDateTest() {
        if (CARL.getListOfSessions().get(0) instanceof RecurringSession) {
            assertEquals(new SessionDate("2021-01-15", DEFAULT_TIME), ((
                    (RecurringSession) CARL.getListOfSessions().get(0)).getLastSessionDate()));
        }
    }

    @Test
    void hasSessionOnDateTest() {
        RecurringSession startAfter = new RecurringSession(new SessionDate("2021-03-01", "10:00"),
                DURATION, SUBJECT, FEE, INTERVAL, new SessionDate("2021-03-15", "10:00"));
        RecurringSession endBefore = new RecurringSession(new SessionDate("2020-03-01", "10:00"),
                DURATION, SUBJECT, FEE, INTERVAL, new SessionDate("2020-03-15", "10:00"));
        RecurringSession notOnDate = new RecurringSession(new SessionDate("2020-12-31", "10:00"),
                DURATION, SUBJECT, FEE, INTERVAL, new SessionDate("2021-01-14", "10:00"));
        RecurringSession onDate = new RecurringSession(new SessionDate("2020-12-11", "10:00"),
                DURATION, SUBJECT, FEE, INTERVAL, new SessionDate("2021-01-15", "10:00"));

        assertFalse(startAfter.hasSessionOnDate(SESSION_DATE));
        assertFalse(endBefore.hasSessionOnDate(SESSION_DATE));
        assertFalse(notOnDate.hasSessionOnDate(SESSION_DATE));
        assertTrue(onDate.hasSessionOnDate(SESSION_DATE));
    }

    @Test
    void isRecurringOverlappingRecurring() {
        // EndDate in relation to when the first session begins

        // Days Between: 8, Interval1: 3, Interval2: 7, EndDate1: 15, EndDate2: 15
        RecurringSession recurringSessionTest1 = new RecurringSession(new SessionDate("2021-01-01", "10:00"),
                DURATION, SUBJECT, FEE, new Interval("3"), new SessionDate("2021-01-16", "10:00"));
        RecurringSession recurringSession2Test1 = new RecurringSession(new SessionDate("2021-01-09", "10:00"),
                DURATION, SUBJECT, FEE, new Interval("7"), new SessionDate("2021-01-16", "10:00"));
        assertTrue(recurringSessionTest1.isOverlapping(recurringSession2Test1));

        // Days Between: 10, Interval1: 14, Interval2: 3, EndDate1: 28, EndDate2: 31
        RecurringSession recurringSession1Test2 = new RecurringSession(new SessionDate("2021-01-01", "10:00"),
                DURATION, SUBJECT, FEE, new Interval("14"), new SessionDate("2021-01-29", "10:00"));
        RecurringSession recurringSession2Test2 = new RecurringSession(new SessionDate("2021-01-11", "10:00"),
                DURATION, SUBJECT, FEE, new Interval("3"), new SessionDate("2021-02-01", "10:00"));
        assertTrue(recurringSession1Test2.isOverlapping(recurringSession2Test2));

        // Days Between: 10, Interval1: 14, Interval2: 3, EndDate: 28, EndDate2: 25
        RecurringSession recurringSession1Test3 = new RecurringSession(new SessionDate("2021-01-01", "12:00"),
                DURATION, SUBJECT, FEE, new Interval("14"), new SessionDate("2021-01-29", "12:00"));
        RecurringSession recurringSession2Test3 = new RecurringSession(new SessionDate("2021-01-11", "12:00"),
                DURATION, SUBJECT, FEE, new Interval("3"), new SessionDate("2021-01-26", "12:00"));
        assertFalse(recurringSession1Test3.isOverlapping(recurringSession2Test3));

        // Days Between: 1, Interval1: 2, Interval2: 1, EndDate1: 14, EndDate2: 15
        RecurringSession recurringSession1Test4 = new RecurringSession(new SessionDate("2021-01-01", "12:00"),
                DURATION, SUBJECT, FEE, new Interval("2"), new SessionDate("2021-01-15", "12:00"));
        RecurringSession recurringSession2Test4 = new RecurringSession(new SessionDate("2021-01-02", "12:00"),
                DURATION, SUBJECT, FEE, new Interval("1"), new SessionDate("2021-01-14", "12:00"));
        assertTrue(recurringSession1Test4.isOverlapping(recurringSession2Test4));

    }

    @Test
    void lastSessionOnOrBeforeTest() {
        RecurringSession recurringSession = new RecurringSession(new SessionDate("2020-12-11", "10:00"),
                DURATION, SUBJECT, FEE, INTERVAL, new SessionDate("2021-01-15", "10:00"));
        Session session = new Session(new SessionDate("2021-01-01", "10:00"), DURATION, SUBJECT, FEE);
        assertEquals(session, recurringSession.lastSessionOnOrBefore(SESSION_DATE));

        RecurringSession startAfter = new RecurringSession(new SessionDate("2021-12-11", "10:00"),
                DURATION, SUBJECT, FEE, INTERVAL, new SessionDate("2022-01-15", "10:00"));
        assertThrows(IllegalArgumentException.class, () -> startAfter.lastSessionOnOrBefore(SESSION_DATE));
    }


    @Test
    void numOfSessionBetweenTest() {
        SessionDate firstSessionDate = new SessionDate("2021-02-28", "10:00");
        SessionDate lastSessionDate = new SessionDate("2021-04-11", "10:00");
        SessionDate firstOfMarch = new SessionDate("2021-03-01", "00:00");
        SessionDate lastOfMarch = new SessionDate("2021-03-31", "23:59");


        Interval twoWeekInterval = new Interval("14");
        RecurringSession recurringSession1 = new RecurringSession(
                firstSessionDate, new Duration("100"), new Subject("Math"),
                new Fee("40"), twoWeekInterval, lastSessionDate);
        int ans1 = recurringSession1.numOfSessionBetween(firstOfMarch, lastOfMarch);
        assertEquals(2, ans1);

        Interval oneDayInterval = new Interval("1");
        RecurringSession recurringSession2 = new RecurringSession(
                firstSessionDate, new Duration("100"), new Subject("Math"),
                new Fee("40"), oneDayInterval, lastSessionDate);
        int ans2 = recurringSession2.numOfSessionBetween(firstOfMarch, lastOfMarch);
        assertEquals(31, ans2);
    }

}