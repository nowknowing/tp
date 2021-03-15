package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.session.SessionDate;

public class JsonAdaptedSessionDate {
    private String dateValue;
    private String timeValue;

    @JsonCreator
    public JsonAdaptedSessionDate(@JsonProperty String dateValue, @JsonProperty String timeValue) {
        this.dateValue = dateValue;
        this.timeValue = timeValue;
    }

    public JsonAdaptedSessionDate(SessionDate source) {
        this.dateValue = source.dateValue;
        this.timeValue = source.timeValue;
    }

    public SessionDate toModelType() throws IllegalValueException {
        return new SessionDate(dateValue, timeValue);
    }
}
