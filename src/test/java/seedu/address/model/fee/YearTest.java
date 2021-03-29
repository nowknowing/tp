package seedu.address.model.fee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class YearTest {
    @Test
    public void constructor_invalidYear_throwsIllegalArgumentException() {
        int invalidYear = 2038;
        assertThrows(java.lang.IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void equalsYear() {
        Year year2021 = new Year(2021);
        assertEquals(year2021, new Year(2021));
        assertEquals(year2021.toString(), String.valueOf(year2021.getYear()));
    }

    @Test
    public void isValidYear() {
        // Invalid Year: int below 1970 or above 2037 should fail
        assertFalse(Year.isValidYear(-999));
        assertFalse(Year.isValidYear(1969));
        assertFalse(Year.isValidYear(2038));
        assertFalse(Year.isValidYear(9999));

        // Valid Year: Any int between 1970 inclusive and 2037 inclusive
        assertTrue(Year.isValidYear(1970));
        assertTrue(Year.isValidYear(2000));
        assertTrue(Year.isValidYear(2037));
    }
}
