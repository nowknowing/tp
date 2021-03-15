package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.session.*;

public class JsonAdaptedSession {

    private JsonAdaptedSessionDate sessionDate;
    private String duration;
    private String subject;
    private String fee;

    /**
     * Constructs a {@code JsonAdaptedSession} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionDate") JsonAdaptedSessionDate sessionDate,
                              @JsonProperty("duration") String duration,
                              @JsonProperty("subject") String subject, @JsonProperty("fee") String fee) {
        this.sessionDate = sessionDate;
        this.duration = duration;
        this.subject = subject;
        this.fee = fee;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        sessionDate = new JsonAdaptedSessionDate(source.getSessionDate());
        duration = source.getDuration().getValue();
        subject = source.getSubject().toString();
        fee = source.getFee().getString();
    }

    public Session toModelType() throws IllegalValueException {

        return new Session(sessionDate.toModelType(),
                new Duration(duration),
                new Subject(subject),
                new Fee(fee));
    }

}
