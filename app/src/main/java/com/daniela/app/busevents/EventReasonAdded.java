package com.daniela.app.busevents;

/**
 * Created by Daniela on 20-Jun-17.
 */

public class EventReasonAdded {

    private String reason;
    private String program;

    public EventReasonAdded(String reason, String program) {
        this.reason = reason;
        this.program = program;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
