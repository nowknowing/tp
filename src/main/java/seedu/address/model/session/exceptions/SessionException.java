package seedu.address.model.session.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.session.Session;

/**
 * Error during the creation of a session {@link Session}.
 */
public class SessionException extends IllegalValueException {
    public SessionException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code SessionException} with the specified detail {@code message} and {@code cause}.
     */
    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
