package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.SessionBuilder.DEFAULT_TIME;
import static seedu.address.testutil.TypicalStudents.CARL;

import org.junit.jupiter.api.Test;

class RecurringSessionTest extends SessionTest {
    static final Interval WEEKLY = new Interval("7");
    static final SessionDate SESSION_DATE = new SessionDate("2021-01-01", "10:00");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecurringSession(null,
                null, null, null, null, null));
    }

    @Test
    public void isValidEnd() {
        SessionDate consistent = new SessionDate("2021-01-15", "10:00");
        SessionDate inconsistentDate = new SessionDate("2021-01-14", "10:00");
        SessionDate inconsistentTime = new SessionDate("2021-01-15", "12:00");


        assertTrue(RecurringSession.isValidEnd(SESSION_DATE, consistent, WEEKLY));
        assertFalse(RecurringSession.isValidEnd(SESSION_DATE, inconsistentDate, WEEKLY));
        assertFalse(RecurringSession.isValidEnd(SESSION_DATE, inconsistentTime, WEEKLY));
    }

    @Test
    public void isConsistentDatesAndInterval() {
        SessionDate consistent1 = new SessionDate("2021-01-15", "12:00");
        SessionDate same = new SessionDate("2021-01-01", "10:00");
        SessionDate inconsistentDate = new SessionDate("2021-01-14", "10:00");
        Interval inconsistentInterval = new Interval("3");
        Interval nextDay = new Interval("1");


        assertTrue(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, consistent1, WEEKLY));
        assertFalse(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, same, nextDay));
        assertFalse(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, inconsistentDate, WEEKLY));
        assertFalse(RecurringSession.isConsistentDatesAndInterval(SESSION_DATE, consistent1, inconsistentInterval));
    }

    @Test
    void getInterval() {
        if (CARL.getListOfSessions().get(0) instanceof RecurringSession) {
            assertEquals(new Interval("7"), (
                    (RecurringSession) CARL.getListOfSessions().get(0)).getInterval());
        }
    }

    @Test
    void getLastSessionDate() {
        if (CARL.getListOfSessions().get(0) instanceof RecurringSession) {
            assertEquals(new SessionDate("2021-01-15", DEFAULT_TIME), ((
                    (RecurringSession) CARL.getListOfSessions().get(0)).getLastSessionDate()));
        }
    }

    @Test
    void numOfSessionBetween() {
        SessionDate firstSessionDate = new SessionDate("2021-02-28", "10:00");
        SessionDate lastSessionDate = new SessionDate("2021-04-11", "10:00");
        SessionDate firstOfMarch = new SessionDate("2021-03-01", "00:00");
        SessionDate lastOfMarch = new SessionDate("2021-03-31", "23:59");

        int eyeBalledAns1 = 2;
        Interval interval1 = new Interval("14");
        //first and last must match
        RecurringSession s = new RecurringSession(firstSessionDate, new Duration("100"), new Subject("Math"),
                new Fee("40"), interval1, lastSessionDate);
        int ans1 = s.numOfSessionBetween(firstOfMarch, lastOfMarch);
        assertEquals(ans1, eyeBalledAns1);

        int eyeBalledAns2 = 31;
        Interval interval2 = new Interval("1");
        s = new RecurringSession(firstSessionDate, new Duration("100"), new Subject("Math"),
                new Fee("40"), interval2, lastSessionDate);
        int ans2 = s.numOfSessionBetween(firstOfMarch, lastOfMarch);
        assertEquals(ans2, eyeBalledAns2);
    }

}